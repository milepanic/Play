package play.model;

public class Follow {

	private User followerId;
	private User userId;
	
	public Follow(User followerId, User userId) {
		this.followerId = followerId;
		this.userId = userId;
	}

	public User getFollowerId() {
		return followerId;
	}

	public void setFollowerId(User followerId) {
		this.followerId = followerId;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}	
}
