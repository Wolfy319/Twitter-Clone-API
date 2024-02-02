package com.cooksys.socialmedia.utils;

import com.cooksys.socialmedia.entities.Tweet;
import java.util.Comparator;

public class TweetTimestampComparator implements Comparator<Tweet>{

	@Override
	public int compare(Tweet o1, Tweet o2) {
		return o1.getPosted().compareTo(o2.getPosted());
	}

}
