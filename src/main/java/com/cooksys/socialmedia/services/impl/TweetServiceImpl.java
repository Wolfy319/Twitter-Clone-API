package com.cooksys.socialmedia.services.impl;

import com.cooksys.socialmedia.dtos.CredentialsDto;
import com.cooksys.socialmedia.dtos.HashtagDto;
import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.dtos.UserResponseDto;
import com.cooksys.socialmedia.embeddable.Credentials;
import com.cooksys.socialmedia.entities.Tweet;
import com.cooksys.socialmedia.entities.User;
import com.cooksys.socialmedia.exceptions.NotFoundException;
import com.cooksys.socialmedia.mappers.HashtagMapper;
import com.cooksys.socialmedia.mappers.TweetMapper;
import com.cooksys.socialmedia.mappers.UserMapper;
import com.cooksys.socialmedia.repositories.TweetRepository;
import com.cooksys.socialmedia.repositories.UserRepository;
import com.cooksys.socialmedia.services.TweetService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TweetServiceImpl implements TweetService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TweetMapper tweetMapper;
    @Autowired
    private TweetRepository tweetRepository;
    @Autowired
    private HashtagMapper hashtagMapper;


	public Tweet copyTweet(Tweet original) {
		Tweet newTweet = new Tweet();
		newTweet.setAuthor(original.getAuthor());
		newTweet.setContent(original.getContent());
		newTweet.setHashtags(original.getHashtags());
		newTweet.setInReplyTo(original.getInReplyTo());
		newTweet.setLikedByUsers(original.getLikedByUsers());
		newTweet.setMentionedUsers(original.getMentionedUsers());
		newTweet.setReplies(original.getReplies());
		newTweet.setRepostOf(original.getRepostOf());
		newTweet.setReposts(original.getReposts());

		return newTweet;
	}

    @Override
    public List<TweetResponseDto> getAllTweets() {

        List<Tweet> allTweets = tweetRepository.findAll();

        List<TweetResponseDto> tweetResponseDtos = allTweets.stream()
                .map(tweetMapper::entityToDto)
                .collect(Collectors.toList());

        return tweetResponseDtos;
    }

    @Override
    public List<HashtagDto> getTags(Long id) {

        Tweet tweet = tweetRepository.findById(id).orElseThrow(() -> new NotFoundException("Tweet not found"));

		if (tweet == null || tweet.isDeleted()) {
			throw new NotFoundException("Tweet not found");
		}

        return tweet.getHashtags().stream()
                .map(hashtagMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TweetResponseDto> getReposts(Long id) {

        Tweet tweet = tweetRepository.findById(id).orElseThrow(() -> new NotFoundException("Tweet not found"));

		if (tweet == null || tweet.isDeleted()) {
			throw new NotFoundException("Tweet not found");
		}

        return tweet.getReposts().stream()
                .map(tweetMapper::entityToDto)
                .collect(Collectors.toList());
    }

	@Override
    public List<UserResponseDto> getMentions(Long id) {

        Tweet tweet = tweetRepository.findById(id).orElseThrow(() -> new NotFoundException("Tweet not found"));

        return tweet.getMentionedUsers().stream()
                .map(userMapper::entityToDto)
                .collect(Collectors.toList());
    }

	@Override
	public void likeTweet(Long id, Credentials credentials) {

		Tweet tweet = tweetRepository.findById(id).orElseThrow(() -> new NotFoundException("Tweet not found"));

		if (tweet.isDeleted()) {
			throw new NotFoundException("Tweet has been deleted");
		}

		User user = userRepository.findByCredentialsUsername(credentials.getUsername());

		if (user == null || user.isDeleted()) {
			throw new NotFoundException("User not found");
		}

		tweet.getLikedByUsers().add(user);
		user.getLikedTweets().add(tweet);

		tweetRepository.save(tweet);
		userRepository.save(user);
	}

	@Override
	public TweetResponseDto deleteTweet(Long id, CredentialsDto credentials) {

		Tweet tweet = tweetRepository.findById(id).orElseThrow(() -> new NotFoundException("Tweet not found"));

		tweet.setDeleted(true);

		tweetRepository.saveAndFlush(tweet);

		return tweetMapper.entityToDto(tweet);
	}

	@Override
	public List<UserResponseDto> getLikes(Long id) {
		Tweet tweet = tweetRepository.getReferenceById(id);
		if(tweet == null || tweet.isDeleted()) {
			throw new NotFoundException("Tweet does not exist");
		}

		List<User> userLikes = new ArrayList<>();
		for(User likedUser : tweet.getLikedByUsers()) {
			if(!likedUser.isDeleted()) {
				userLikes.add(likedUser);
			}
		}
		return userMapper.entitiesToDtos(userLikes);
	}

    @Override
	public List<TweetResponseDto> getReplies(Long id) {
		Tweet tweet = tweetRepository.getReferenceById(id);
		if(tweet.isDeleted()) {
			throw new NotFoundException("Tweet does not exist");
		}

		List<Tweet> replies = new ArrayList<>();
		for(Tweet reply : tweet.getReplies()) {
			if(!reply.isDeleted()) {
				replies.add(reply);
			}
		}
		return tweetMapper.entitiesToDtos(replies);
	}

	@Override
	public TweetResponseDto repostTweet(Long id, Credentials credentials) {
		User repostingUser = userRepository.findByCredentialsUsername(credentials.getUsername());
		Tweet tweetToRepost = tweetRepository.getReferenceById(id);
		if(repostingUser == null || repostingUser.getCredentials().getPassword() != credentials.getPassword()) {
			throw new NotFoundException("User credentials do not match any existing users");
		} else if(tweetToRepost.isDeleted()) {
			throw new NotFoundException("Tweet was deleted or doesn't exist");
		}

		Tweet repost = copyTweet(tweetToRepost);
		repost.setRepostOf(tweetToRepost);
		repost.setContent(null);
		repost.setInReplyTo(null);
		repost.setAuthor(null);

		return tweetMapper.entityToDto(tweetRepository.saveAndFlush(repost));
	}
}
