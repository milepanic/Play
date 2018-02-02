package play.model;

import java.util.Date;

public class Vote {

	//private int id;
	private int userId;
	private int voteableId;
	private String voteableType;
	private boolean vote;
	private Date createdAt;
	
	@Override
	public String toString() {
		return "Vote [userId=" + userId + ", voteableId =" + voteableId + ", vote=" + vote + "]";
	}
	
	public Vote(int userId, int voteableId, String voteableType, boolean vote, Date createdAt) {
		//this.id = id;
		this.userId = userId;
		this.voteableId = voteableId;
		this.voteableType = voteableType;
		this.vote = vote;
		this.createdAt = createdAt;
	}
	
	public Vote() {
		userId = 0;
		voteableId = 0;
		voteableType = "";
		vote = true;
		createdAt = new Date();
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getVoteableId() {
		return voteableId;
	}

	public void setVoteableId(int voteableId) {
		this.voteableId = voteableId;
	}

	public String getVoteableType() {
		return voteableType;
	}

	public void setVoteableType(String voteableType) {
		this.voteableType = voteableType;
	}

	public boolean isVote() {
		return vote;
	}

	public void setVote(boolean vote) {
		this.vote = vote;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	
}
