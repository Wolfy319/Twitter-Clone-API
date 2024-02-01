package com.cooksys.socialmedia.utils;

import java.util.Comparator;

import com.cooksys.socialmedia.entities.Tweet;

public class TweetTimestampComparator implements Comparator<Tweet>{

	@Override
	public int compare(Tweet o1, Tweet o2) {
		return o1.getPosted().compareTo(o2.getPosted());
	}

}
