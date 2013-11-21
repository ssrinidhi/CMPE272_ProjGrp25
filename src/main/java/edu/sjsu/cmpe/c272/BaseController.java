package edu.sjsu.cmpe.c272;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ibm.nosql.json.api.BasicDBObject;
import com.ibm.nosql.json.api.DB;
import com.ibm.nosql.json.api.DBCollection;
import com.ibm.nosql.json.api.DBCursor;
import com.ibm.nosql.json.api.DBObject;

import twitter4j.FilterQuery;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

@Controller
@RequestMapping("/")
public class BaseController {
	
	private DB2JsonStore db2JsonStore = new DB2JsonStore();
	private List<TwitterDTO> tweets = null;
	private static Log log = LogFactory.getLog(BaseController.class);
	
	@RequestMapping(value="/", method = RequestMethod.GET)
	public String welcome(ModelMap model) { 
		model.addAttribute("message", "JSON DB2 Connector");
		return "portfolio";
	}
	
	@RequestMapping(value="/index", method = RequestMethod.GET)
	public String getIndexPage() {
		return "index";
	}
	
	@RequestMapping(value = "/streamingTweets/{keywords}", method = RequestMethod.GET)
	public String getStreamingTweets(@PathVariable String keywords, @RequestParam(required = false) boolean storeData, 
			@RequestParam(required = false) String collection, ModelMap model) throws TwitterException {
		log.debug("Getting Streaming tweets for keyword " + keywords);
		String collectionName = getCollectionName(keywords, collection);
		TwitterStatusListenerImpl twitterStatusListener = new TwitterStatusListenerImpl(db2JsonStore, collectionName, storeData);
		TwitterStream twitterStream = getTwitterStream();
		
		FilterQuery filterQuery = new FilterQuery();
		filterQuery.track(getTrackedKeywords(keywords));
		
		twitterStream.addListener(twitterStatusListener);
		twitterStream.filter(filterQuery);
		
		return "index";
	}
	
	@RequestMapping(value = "/timelineTweets/{screenName}", method = RequestMethod.GET)
	public @ResponseBody List<TwitterDTO> getTimelineTweets(@PathVariable String screenName, @RequestParam(required = false) boolean storeData, 
			@RequestParam(required = false) String collection) throws TwitterException {
		log.debug("Getting Streaming tweets for Screen Name " + screenName);
		//ModelAndView model = new ModelAndView("index");
		String collectionName = getCollectionName(screenName, collection);
		Twitter twitter = getTwitter();
		ResponseList<Status> tweetsStatus = twitter.getUserTimeline(screenName);//, new Paging(1, 2));
		
		tweets = new ArrayList<TwitterDTO>();
	
		for (Status status : tweetsStatus) {
			TwitterDTO tweet = new TwitterDTO();
			tweet.setContent(status.getText());
			tweet.setProfileLocation(status.getPlace() == null ? "" : status.getPlace().getFullName());
			tweet.setTweetId(status.getId());
			tweet.setUsername(status.getUser().getName());
			tweets.add(tweet);
		}

		for (TwitterDTO tweet : tweets) {
			try {
				String jsonTweet = new ObjectMapper().writeValueAsString(tweet);
				if (storeData) {
					db2JsonStore.storeData(jsonTweet, collectionName);
				}
				log.info("Tweet:- " + jsonTweet);
			} catch (JsonGenerationException e) {
				log.error("An error occurred while generating the Twitter JSON Object");
				e.printStackTrace();
			} catch (JsonMappingException e) {
				log.error("An error occured while mapping the JSON Object");
				e.printStackTrace();
			} catch (IOException e) {
				log.error("An IO error occured while mapping the tweets");
				e.printStackTrace();
			}
		}
		
		//model.addObject("tweets", tweets);
		
		return tweets;
	}
	
	@RequestMapping(value = "/dbStore", method = RequestMethod.POST)
	public String setDBStore(@RequestParam String propertiesFileName) throws Exception {
		db2JsonStore.setDbStore(new File(propertiesFileName));
		if (db2JsonStore.getDbStore() != null) {
			log.info("DB2 JSON Store Set!");
		} else {
			log.info("Oops!! Something went wrong while setting the database");
		}
		return "index";
	}
	
	@RequestMapping(value = "/retrieveTweets", method = RequestMethod.GET)
	public String getDBTweetsBasedOnKeyword(@RequestParam(required = true) String query, @RequestParam(required = false) String collection) throws JsonParseException, JsonMappingException, IOException {
		DB dbHandle = db2JsonStore.getDbStore();
		//List<TwitterDTO> tweets = new ArrayList<TwitterDTO>();
		List<String> tweets = new ArrayList<String>();
		DBCursor dbCursorForJsonQuery = null;
		DBCursor dbCursorForEntireCollection = null;
		DBCursor dbCursorForKeyword = null;
		if (dbHandle != null) {
			DBCollection dbCollection = dbHandle.getCollection(getCollectionName(query, collection));
			
			try {
				dbCursorForEntireCollection = dbCollection.find();
			} catch (Exception e) {
				log.info("No data in the entire collection");
			}
			
			try {
				BasicDBObject dataBasedOnKeyword = new BasicDBObject ("content", Pattern.compile(".*" + query + ".*"));
				dbCursorForKeyword = dbCollection.find(dataBasedOnKeyword);
			} catch (Exception e) {
				log.warn("Couldn't complete the db collection find for the keyword");
			}
			
			try {
				dbCursorForJsonQuery = dbCollection.find(query);
			} catch (Exception e) {
				log.warn("Couldn't complete the db collection find for the Json Query");
			}
			
			if (dbCursorForJsonQuery != null) {
				while (dbCursorForJsonQuery.hasNext()) {
					DBObject object = dbCursorForJsonQuery.next();
					log.info("Tweet: " + object);
					//System.out.println(object);
					tweets.add(object.toString());
					//TwitterDTO tweet = new ObjectMapper().readValue(object.toBSON(), TwitterDTO.class);
					//tweets.add(tweet);
				}
				dbCursorForJsonQuery.close();
			}
			
			if (dbCursorForKeyword != null) {
				while (dbCursorForKeyword.hasNext()) {
					DBObject object = dbCursorForKeyword.next();
					log.info("Tweet: " + object);
					//System.out.println(object);
					tweets.add(object.toString());
					//TwitterDTO tweet = new ObjectMapper().readValue(object.toBSON(), TwitterDTO.class);
					//tweets.add(tweet);
				}
				dbCursorForKeyword.close();
			}
			
			if (dbCursorForEntireCollection != null) {
				while (dbCursorForEntireCollection.hasNext()) {
					DBObject object = dbCursorForEntireCollection.next();
					log.info("Tweet: " + object);
					//System.out.println(object);
					tweets.add(object.toString());
					//TwitterDTO tweet = new ObjectMapper().readValue(object.toBSON(), TwitterDTO.class);
					//tweets.add(tweet);
				}
				dbCursorForEntireCollection.close();
			}
		} else {
			log.error("DB Store not Set");
			System.out.println("DB Store not Set");
		}
		return "index";
	}
	
	private String getCollectionName(String query, String collection) {
		String collectionName = query+"Collection";
		if (collection != null && !collection.trim().isEmpty()) {
			collectionName = collection;
		}
		return collectionName;
	}
	
	private String[] getTrackedKeywords(String query) {
		String[] arrayOfTrackedKeywords = query.split(",");
		return arrayOfTrackedKeywords;
	}
	
	private TwitterStream getTwitterStream() {
		TwitterStream twitterStream = TwitterStreamFactory.getSingleton();
		return twitterStream;
	}
	
	private Twitter getTwitter() {
		Twitter twitter = TwitterFactory.getSingleton();
		return twitter;
	}
	
}