package play.model;

import java.util.Date;

public class Comment {

	private int id;
	private String text;
	private Date createdAt;
	private User user;
	private int videoId;
	
	public Comment(int id, String text, Date createdAt, User user, int videoId) {
		this.id = id;
		this.text = text;
		this.createdAt = createdAt;
		this.user = user;
		this.videoId = videoId;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getVideoId() {
		return videoId;
	}
	public void setVideoId(int videoId) {
		this.videoId = videoId;
	}
}
