package play.model;

import java.util.Date;

public class Vote {

	//private int id;
	private int userId;
	private int videoId;
	private boolean like;
	//dodati tip - video ili komentar
	private Date createdAt;
	
	@Override
	public String toString() {
		return "Vote [userId=" + userId + ", videoId=" + videoId + ", like=" + like + "]";
	}
	
	public Vote(int userId, int videoId, boolean like, Date createdAt) {
		//this.id = id;
		this.userId = userId;
		this.videoId = videoId;
		this.like = like;
		this.createdAt = createdAt;
	}
	
	public Vote() {
		userId = 0;
		videoId = 0;
		like = true;
		createdAt = new Date();
	}
	
	/*public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}*/
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getVideoId() {
		return videoId;
	}
	public void setVideoId(int videoId) {
		this.videoId = videoId;
	}
	public boolean isLike() {
		return like;
	}
	public void setLike(boolean like) {
		this.like = like;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
}
