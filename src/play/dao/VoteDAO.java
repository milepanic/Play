package play.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import play.model.Vote;

public class VoteDAO {

	public static boolean create(Vote vote) {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		try {
			String query = "INSERT INTO votes (user_id, voteable_id, voteable_type, vote) VALUES (?, ?, ?, ?)";

			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setInt(index++, vote.getUserId());
			pstmt.setInt(index++, vote.getVoteableId());
			pstmt.setString(index++, vote.getVoteableType());
			pstmt.setBoolean(index++, vote.isVote());
			
			System.out.println(pstmt);

			return pstmt.executeUpdate() == 1;
		} catch (SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}

		return false;
	}
	
	public static Vote get(Vote vote) {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT * "
					+ "FROM votes WHERE user_id = ? AND voteable_id = ? AND voteable_type = ?";

			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setInt(index++, vote.getUserId());
			pstmt.setInt(index++, vote.getVoteableId());
			pstmt.setString(index++, vote.getVoteableType());
			System.out.println(pstmt);

			rset = pstmt.executeQuery();
			if (rset.next()) {				
				index = 1;
				int id = rset.getInt(index++);
				int userId = rset.getInt(index++);
				int voteableId = rset.getInt(index++);
				String voteableType = rset.getString(index++);
				boolean isVote = rset.getBoolean(index++);
				Date createdAt = rset.getDate(index++);
				
				return new Vote(userId, voteableId, voteableType, isVote, createdAt);
			}
		} catch (SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}

		return null;
	}
	
	public static int getVotes(int videoId, String type, boolean like) {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT count(*) FROM votes WHERE voteable_id = ? AND voteable_type = ? AND vote = ?";

			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setInt(index++, videoId);
			pstmt.setString(index++, type);
			pstmt.setBoolean(index++, like);
			System.out.println(pstmt);

			rset = pstmt.executeQuery();
			if (rset.next()) {				
				index = 1;
				int count = rset.getInt(index++);
				
				return count;
			}
		} catch (SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}

		return -1;
	}
	
	public static boolean update(Vote vote) {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		try {
			String query = "UPDATE votes SET vote = ? WHERE user_id = ? AND voteable_id = ? AND voteable_type = ?";

			pstmt = conn.prepareStatement(query);
			int index = 1;
			
			pstmt.setBoolean(index++, vote.isVote());
			pstmt.setInt(index++, vote.getUserId());
			pstmt.setInt(index++, vote.getVoteableId());
			pstmt.setString(index++, vote.getVoteableType());
			System.out.println(pstmt);

			return pstmt.executeUpdate() == 1;
		} catch (SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}

		return false;
	}
	
	public static boolean delete(Vote vote) {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		try {
			String query = "DELETE FROM votes WHERE user_id = ? AND voteable_id = ? AND voteable_type = ?";
			pstmt = conn.prepareStatement(query);
			
			int index = 1;
			pstmt.setInt(index++, vote.getUserId());
			pstmt.setInt(index++, vote.getVoteableId());
			pstmt.setString(index++, vote.getVoteableType());
			System.out.println(pstmt);
			
			return pstmt.executeUpdate() == 1;
		} catch (SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		
		return false;
	}
}
