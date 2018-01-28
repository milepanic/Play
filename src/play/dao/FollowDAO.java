package play.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import play.model.User;
import play.model.Video;

public class FollowDAO {

	public static boolean create(int followerId, int userId) {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		try {
			String query = "INSERT INTO follow (follower_id, user_id) "
					+ "VALUES (?, ?)";

			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setInt(index++, followerId);
			pstmt.setInt(index++, userId);
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
	
	public static boolean get(int followerId, int userId) {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT * "
					+ "FROM follow WHERE follower_id = ? AND user_id = ?";

			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setInt(index++, followerId);
			pstmt.setInt(index++, userId);
			System.out.println(pstmt);

			rset = pstmt.executeQuery();
			if (rset.next()) {				
				return true;
			}
		} catch (SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}

		return false;
	}
	
	// posalje ulogovanog korisnika, uzme sve user_id iz tabele follow
	// gdje je follower_id = prosledjeni korisnik
	// zatim u tabeli video uzme sve rezultate gdje je taj user_id
	public static List<Video> getVideos(int followerId) {
		List<Video> videos = new ArrayList<>();
		
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT user_id FROM follow WHERE follower_id = ?";

			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setInt(index++, followerId);
			System.out.println(pstmt);

			rset = pstmt.executeQuery();
			int i = 0;
			while (rset.next()) {
				index = 1;
				int userId = rset.getInt(index++);
				
				List<Video> userVideos = VideoDAO.getWhereUser(userId);
				videos.addAll(i++, userVideos);
			}
			
			return videos;
		} catch (SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}

		return null;
	}
	
	public static boolean delete(int followerId, int userId) {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		try {
			String query = "DELETE FROM follow WHERE follower_id = ? AND user_id = ?";
			pstmt = conn.prepareStatement(query);
			
			int index = 1;
			pstmt.setInt(index++, followerId);
			pstmt.setInt(index++, userId);
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
