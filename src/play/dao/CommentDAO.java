package play.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import play.model.Comment;
import play.model.Video;


public class CommentDAO {
	
	public static List<Comment> getAll(int id) {
		
		List<Comment> comments = new ArrayList<>();
	
		Connection conn = ConnectionManager.getConnection();
	
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT * FROM comments WHERE video_id = ?";
	
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
				//int userId = rset.getInt(index++);
				//int videoId = rset.getInt(index++);
				
				//Query za trazenje usera
				//String query2 = "SELECT * FROM users WHERE id = ?";
				
	
				Comment comment = new Comment(commentId, text, createdAt);
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
	
	public static boolean add(Comment comment) {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		try {
			String query = "INSERT INTO comments (text, user_id, video_id) "
					+ "VALUES (?, 1, 1)";

			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setString(index++, comment.getText());
			//pstmt.setDouble(index++, comment.getUser().get);
			//pstmt.setInt(index++, comment.getVideo().getId());
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
