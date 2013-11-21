package edu.sjsu.cmpe.c272;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.User;

public class TwitterStatusListenerImpl implements StatusListener {
	
	private String tweet;
	private static Log log = LogFactory.getLog(TwitterStatusListenerImpl.class);
	private DB2JsonStore db2JsonStore;
	private boolean storeData;
	private String collectionName;
	
	public TwitterStatusListenerImpl(DB2JsonStore db2JsonStore, String collectionName, boolean storeData) {
		this.db2JsonStore = db2JsonStore;
		this.collectionName = collectionName;
		this.storeData = storeData;
	}
	
	public String getTweet() {
		return tweet;
	}

	public void setTweet(String tweet) {
		this.tweet = tweet;
	}

	@Override
	public void onException(Exception arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDeletionNotice(StatusDeletionNotice arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onScrubGeo(long arg0, long arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStallWarning(StallWarning arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatus(Status status) {
		log.debug("OnStatus Method --");
		User user = status.getUser();
		TwitterDTO tweet = new TwitterDTO();
		ObjectMapper mapper = new ObjectMapper();

		String username = status.getUser().getScreenName();
		tweet.setUsername(username);

		String profileLocation = user.getLocation();
		tweet.setProfileLocation(profileLocation);
		
		long tweetId = status.getId();
		tweet.setTweetId(tweetId);
		
		String content = status.getText();
		tweet.setContent(content);

		try {
			String jsonTweet = mapper.writeValueAsString(tweet);
			if (storeData) {
				db2JsonStore.storeData(jsonTweet, collectionName);
			}
			setTweet(jsonTweet);
			log.info("Tweet Data: " + jsonTweet);
		} catch (JsonGenerationException e) {
			log.error("Error occured while generating JSON object for the tweet");
			e.printStackTrace();
		} catch (JsonMappingException e) {
			log.error("Error occured while mapping JSON object");
			e.printStackTrace();
		} catch (IOException e) {
			log.error("IO Error occured while obtaining a tweet");
			e.printStackTrace();
		}
	}

	@Override
	public void onTrackLimitationNotice(int arg0) {
		// TODO Auto-generated method stub
		
	}
	
}