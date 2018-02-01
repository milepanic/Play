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
			String query = "INSERT INTO votes (user_id, video_id, vote) VALUES (?, ?, ?)";

			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setInt(index++, vote.getUserId());
			pstmt.setInt(index++, vote.getVideoId());
			pstmt.setBoolean(index++, vote.isLike());
			
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
					+ "FROM votes WHERE user_id = ? AND video_id = ?";

			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setInt(index++, vote.getUserId());
			pstmt.setInt(index++, vote.getVideoId());
			System.out.println(pstmt);

			rset = pstmt.executeQuery();
			if (rset.next()) {				
				index = 1;
				int id = rset.getInt(index++);
				int userId = rset.getInt(index++);
				int videoId = rset.getInt(index++);
				boolean like = rset.getBoolean(index++);
				Date createdAt = rset.getDate(index++);
				
				return new Vote(userId, videoId, like, createdAt);
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
	
	public static int getVotes(int videoId, boolean like) {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT count(*) FROM votes WHERE video_id = ? AND vote = ?";

			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setInt(index++, videoId);
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
			String query = "UPDATE votes SET vote = ? WHERE user_id = ? AND video_id = ?";

			pstmt = conn.prepareStatement(query);
			int index = 1;
			
			pstmt.setBoolean(index++, vote.isLike());
			pstmt.setInt(index++, vote.getUserId());
			pstmt.setInt(index++, vote.getVideoId());
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
			String query = "DELETE FROM votes WHERE video_id = ? AND user_id = ?";
			pstmt = conn.prepareStatement(query);
			
			int index = 1;
			pstmt.setInt(index++, vote.getVideoId());
			pstmt.setInt(index++, vote.getUserId());
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
