package com.cooksys.socialmedia.services.impl;

import com.cooksys.socialmedia.dtos.ContextDto;
import com.cooksys.socialmedia.dtos.CredentialsDto;
import com.cooksys.socialmedia.dtos.HashtagDto;
import com.cooksys.socialmedia.dtos.TweetRequestDto;
import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.dtos.UserResponseDto;
import com.cooksys.socialmedia.embeddable.Credentials;
import com.cooksys.socialmedia.entities.Hashtag;
import com.cooksys.socialmedia.entities.Tweet;
import com.cooksys.socialmedia.entities.User;
import com.cooksys.socialmedia.exceptions.BadRequestException;
import com.cooksys.socialmedia.exceptions.NotFoundException;
import com.cooksys.socialmedia.mappers.HashtagMapper;
import com.cooksys.socialmedia.mappers.TweetMapper;
import com.cooksys.socialmedia.mappers.UserMapper;
import com.cooksys.socialmedia.repositories.HashtagRepository;
import com.cooksys.socialmedia.repositories.TweetRepository;
import com.cooksys.socialmedia.repositories.UserRepository;
import com.cooksys.socialmedia.services.TweetService;
import com.cooksys.socialmedia.services.ValidateService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class TweetServiceImpl implements TweetService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final TweetMapper tweetMapper;
    private final TweetRepository tweetRepository;
    private final HashtagRepository hashtagRepository;
    private final HashtagMapper hashtagMapper;

    private ValidateService validateService;


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
        List<Tweet> allTweets = tweetRepository.findAllByDeletedFalseOrderByPostedDesc();

        return allTweets.stream()
                .map(tweetMapper::entityToDto)
                .collect(toList());
    }

    @Override
    public List<HashtagDto> getTags(Long id) {

        Tweet tweet = tweetRepository.findById(id).orElseThrow(() -> new NotFoundException("Tweet not found"));

        if (tweet == null || tweet.isDeleted()) {
            throw new NotFoundException("Tweet not found");
        }

        return tweet.getHashtags().stream()
                .map(hashtagMapper::entityToDto)
                .collect(toList());
    }

    @Override
    public List<TweetResponseDto> getReposts(Long id) {

        Tweet tweet = tweetRepository.findById(id).orElseThrow(() -> new NotFoundException("Tweet not found"));

        if (tweet == null || tweet.isDeleted()) {
            throw new NotFoundException("Tweet not found");
        }

        return tweet.getReposts().stream()
                .map(tweetMapper::entityToDto)
                .collect(toList());
    }

    @Override
    public List<UserResponseDto> getMentions(Long id) {

        Tweet tweet = tweetRepository.findById(id).orElseThrow(() -> new NotFoundException("Tweet not found"));

        return tweet.getMentionedUsers().stream()
                .map(userMapper::entityToDto)
                .collect(toList());
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
        if (tweet == null || tweet.isDeleted()) {
            throw new NotFoundException("Tweet does not exist");
        }

        List<User> userLikes = new ArrayList<>();
        for (User likedUser : tweet.getLikedByUsers()) {
            if (!likedUser.isDeleted()) {
                userLikes.add(likedUser);
            }
        }
        return userMapper.entitiesToDtos(userLikes);
    }

    @Override
    public List<TweetResponseDto> getReplies(Long id) {
        Tweet tweet = tweetRepository.getReferenceById(id);
        if (tweet.isDeleted()) {
            throw new NotFoundException("Tweet does not exist");
        }

        List<Tweet> replies = new ArrayList<>();
        for (Tweet reply : tweet.getReplies()) {
            if (!reply.isDeleted()) {
                replies.add(reply);
            }
        }
        return tweetMapper.entitiesToDtos(replies);
    }

    @Override
    public TweetResponseDto repostTweet(Long id, Credentials credentials) {
        User repostingUser = userRepository.findByCredentialsUsername(credentials.getUsername());
        Tweet tweetToRepost = tweetRepository.getReferenceById(id);
        if (repostingUser == null || repostingUser.getCredentials().getPassword() != credentials.getPassword()) {
            throw new NotFoundException("User credentials do not match any existing users");
        } else if (tweetToRepost.isDeleted()) {
            throw new NotFoundException("Tweet was deleted or doesn't exist");
        }

        Tweet repost = copyTweet(tweetToRepost);
        repost.setRepostOf(tweetToRepost);
        repost.setContent(null);
        repost.setInReplyTo(null);
        repost.setAuthor(null);

		return tweetMapper.entityToDto(tweetRepository.saveAndFlush(repost));
	}

	@Override
	public ContextDto getContext(Long id) {
		Tweet target = tweetRepository.getReferenceById(id);
		if(target == null || target.isDeleted()) {
			throw new NotFoundException("ID provided does not match any stored tweets");
		}
		
		List<Tweet> before = new ArrayList<>();
		Tweet previousTweet = target.getInReplyTo();
		while(previousTweet != null) {
			before.add(previousTweet);
			previousTweet = previousTweet.getInReplyTo();
		}
		
		List<Tweet> after = new ArrayList<>();
		List<Tweet> replyTree = new ArrayList<>(target.getReplies());
		for(Tweet reply : replyTree) {
			if(!reply.isDeleted()) {
				after.add(reply);
				replyTree.addAll(reply.getReplies());
			}
		}
		return new ContextDto(tweetMapper.entityToDto(target), tweetMapper.entitiesToDtos(before), tweetMapper.entitiesToDtos(after));
	}
	
    @Override
    public Optional<TweetResponseDto> getTweetById(Long id) {
        return tweetRepository.findById(id)
                .map(tweetMapper::entityToDto);
    }

    // In TweetService
    public TweetResponseDto createTweet(TweetRequestDto tweetRequestDto) {
        User author = validateService.validateUser(tweetRequestDto.getCredentials());
        Tweet tweet = new Tweet();
        tweet.setAuthor(author);
        tweet.setContent(tweetRequestDto.getContent());

        return saveAndProcessTweet(tweet);
    }

    private TweetResponseDto saveAndProcessTweet(Tweet tweet) {
        // Process mentions
        List<User> mentionedUsers = extractMentions(tweet.getContent());
        tweet.setMentionedUsers(mentionedUsers);

        // Process hashtags
        List<Hashtag> hashtags = extractHashtags(tweet.getContent());
        tweet.setHashtags(hashtags);

        // Save the tweet
        Tweet savedTweet = tweetRepository.save(tweet);


        return tweetMapper.entityToDto(savedTweet);
    }

    private List<User> extractMentions(String content) {
        Pattern mentionPattern = Pattern.compile("@(\\w+)");
        Matcher matcher = mentionPattern.matcher(content);
        List<User> mentionedUsers = new ArrayList<>();

        while (matcher.find()) {
            String username = matcher.group(1);
            User user = userRepository.findByCredentialsUsername(username);
            if (user == null) {
                throw new NotFoundException("User not found with username: " + username);
            }
            mentionedUsers.add(user);
        }

        return mentionedUsers;
    }

    private List<Hashtag> extractHashtags(String content) {
        Pattern hashtagPattern = Pattern.compile("#(\\w+)");
        return patternMatcher(content, hashtagPattern)
                .map(label -> hashtagRepository.findByLabel(label)
                        .orElseGet(() -> {
                            Hashtag newHashtag = new Hashtag();
                            newHashtag.setLabel(label);
                            return hashtagRepository.save(newHashtag);
                        }))
                .collect(Collectors.toList());
    }

    private Stream<String> patternMatcher(String content, Pattern pattern) {
        Matcher matcher = pattern.matcher(content);
        List<String> matches = new ArrayList<>();
        while (matcher.find()) {
            matches.add(matcher.group(1));
        }
        return matches.stream();
    }

    public TweetResponseDto replyToTweet(Long parentTweetId, TweetRequestDto tweetRequestDto) {
        User author = validateService.validateUser(tweetRequestDto.getCredentials());
        Tweet parentTweet = tweetRepository.findById(parentTweetId)
                .orElseThrow(() -> new BadRequestException("Parent tweet not found or is deleted"));

        // Check if the original tweet is not deleted
        if (parentTweet.isDeleted()) {
            throw new BadRequestException("Cannot reply to a deleted tweet");
        }

        // Create a new tweet entity for the reply
        Tweet replyTweet = new Tweet();
        replyTweet.setAuthor(author);
        replyTweet.setContent(tweetRequestDto.getContent());
        replyTweet.setInReplyTo(parentTweet);

        // Save and process the reply tweet (handle mentions, hashtags, etc.)
        return saveAndProcessTweet(replyTweet);
    }
}