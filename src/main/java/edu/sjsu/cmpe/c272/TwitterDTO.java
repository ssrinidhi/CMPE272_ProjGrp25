package edu.sjsu.cmpe.c272;

public class TwitterDTO {
	private String _id;
	private String username;
	private long tweetId;
	private String content;
	private String profileLocation;
	
	public String get_Id() {
		return _id;
	}
	
	public void set_Id(String _id) {
		this._id = _id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public long getTweetId() {
		return tweetId;
	}
	
	public void setTweetId(long tweetId) {
		this.tweetId = tweetId;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getProfileLocation() {
		return profileLocation;
	}
	
	public void setProfileLocation(String profileLocation) {
		this.profileLocation = profileLocation;
	}
	
}
