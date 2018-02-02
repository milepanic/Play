package play.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import play.model.Comment;
import play.model.User;
import play.model.Video;


public class CommentDAO {
	
	public static boolean add(Comment comment) {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		try {
			String query = "INSERT INTO comments (text, user_id, video_id) "
					+ "VALUES (?, ?, ?)";

			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setString(index++, comment.getText());
			pstmt.setInt(index++, comment.getUser().getId());
			pstmt.setInt(index++, comment.getVideoId());
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
	
	public static List<Comment> getAll(int id, String param, String rank) {
		
		List<Comment> comments = new ArrayList<>();
	
		Connection conn = ConnectionManager.getConnection();
	
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT * FROM comments WHERE video_id = ? ORDER BY created_at";
			if(rank.contains("DESC"))
				query = query + " DESC";
			
			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setInt(index++, id);
			System.out.println(pstmt);
	
			rset = pstmt.executeQuery();
			while (rset.next()) {
				index = 1;
				int commentId = rset.getInt(index++);
				String text = rset.getString(index++);
				Date createdAt = rset.getDate(index++);
				int userId = rset.getInt(index++);
				int videoId = rset.getInt(index++);
				
				User user = UserDAO.get(userId);
	
				Comment comment = new Comment(commentId, text, createdAt, user, videoId);
				comments.add(comment);
			}
		} catch (SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		
		return comments;
	}
	
	public static int count(int videoId) {
		Connection conn = ConnectionManager.getConnection();
	
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT count(*) FROM comments WHERE video_id = ?";
			
			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setInt(index++, videoId);
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
	
	public static int last() {
		Connection conn = ConnectionManager.getConnection();
	
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT id FROM comments ORDER BY ID DESC LIMIT 1";
	
			pstmt = conn.prepareStatement(query);
	
			rset = pstmt.executeQuery();
			if (rset.next()) {
				int index = 1;
				int id = rset.getInt(index++);
	
				return id;
			}
		} catch (SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		
		return 0;
	}
	
}
