package play.model;

import java.util.Date;

public class Video {

	public enum Visibility {PUBLIC, UNLISTED, PRIVATE};
	
	private int id;
	private String name;
	private String url;
	private String thumbnail;
	private String description;
	private Visibility visibility;
	private boolean commentable;
	private boolean voteable;
	private boolean blocked;
	private int views;
	private Date createdAt;
	private User user;
	private boolean deleted;

	public Video(int id, String name, String url, String thumbnail, String description, 
			Visibility visibility, boolean commentable,	 boolean voteable, 
			boolean blocked, int views, Date createdAt, User user) {
		this.id = id;
		this.name = name;
		this.url = url;
		this.thumbnail = thumbnail;
		this.description = description;
		this.visibility = visibility;
		this.commentable = commentable;
		this.voteable = voteable;
		this.blocked = blocked;
		this.views = views;
		this.createdAt = createdAt;
		this.user = user;
	}
	
	
	@Override
	public String toString() {
		return "Video [id=" + id + ", name=" + name + ", url=" + url + ", createdAt=" + createdAt + ", user=" + user
				+ "]";
	}

	public Video() {
		id = 0;
		name = "";
		url = "";
		thumbnail = "";
		description = "";
		visibility = Visibility.PUBLIC;
		commentable = true;
		voteable = true;
		blocked = false;
		views = 0;
		createdAt = new Date();
		user = null;
	}

	public int getId() {
		return id;
	}	

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public void SetName(String name) {
		this.name = name;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Visibility getVisibility() {
		return visibility;
	}

	public void setVisibility(Visibility visibility) {
		this.visibility = visibility;
	}

	public boolean isCommentable() {
		return commentable;
	}

	public void setCommentable(boolean commentable) {
		this.commentable = commentable;
	}

	public boolean isVoteable() {
		return voteable;
	}

	public void setVoteable(boolean voteable) {
		this.voteable = voteable;
	}

	public boolean isBlocked() {
		return blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
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
	
	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	
}
