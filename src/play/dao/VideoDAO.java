package play.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import play.dao.ConnectionManager;
import play.model.User;
import play.model.Video;
import play.model.Video.Visibility;

public class VideoDAO {
	
	public static boolean create(Video video) {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		try {
			String query = "INSERT INTO videos (name, url, thumbnail, description, "
					+ "visibility, commentable, voteable, user_id) VALUES "
					+ "(?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setString(index++, video.getName());
			pstmt.setString(index++, video.getUrl());
			pstmt.setString(index++, video.getThumbnail());
			pstmt.setString(index++, video.getDescription());
			pstmt.setString(index++, video.getVisibility().toString());
			pstmt.setBoolean(index++, video.isCommentable());
			pstmt.setBoolean(index++, video.isVoteable());
			pstmt.setInt(index++, 1);
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
	
	public static List<Video> getAll() {
		List<Video> videos = new ArrayList<>();

		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT id, name, url, thumbnail, created_at, user_id "
					+ "FROM videos WHERE visibility = 'PUBLIC' AND blocked = false";

			pstmt = conn.prepareStatement(query);
			int index = 1;
			System.out.println(pstmt);

			rset = pstmt.executeQuery();
			while (rset.next()) {
				index = 1;
				int id = rset.getInt(index++);
				String name = rset.getString(index++);
				String url = rset.getString(index++);
				String thumbnail = rset.getString(index++);
				Date createdAt = rset.getDate(index++);
				int user_id = rset.getInt(index++);
				
				//Query za trazenje usera
				//String query2 = "SELECT * FROM users WHERE id = ?";
				

				Video video = new Video(id, name, url, thumbnail, createdAt);
				videos.add(video);
			}
		} catch (SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		
		return videos;
	}
	
	public static Video get(int id) {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT * "
					+ "FROM videos WHERE id = ?";

			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setInt(index++, id);
			System.out.println(pstmt);

			rset = pstmt.executeQuery();
			if (rset.next()) {
				index = 1;
				int videoId = rset.getInt(index++);
				String name = rset.getString(index++);
				String url = rset.getString(index++);
				String thumbnail = rset.getString(index++);
				String description = rset.getString(index++);
				Visibility visibility = Visibility.valueOf(rset.getString(index++));
				boolean commentable = rset.getBoolean(index++);
				boolean voteable = rset.getBoolean(index++);
				boolean blocked = rset.getBoolean(index++);
				int views = rset.getInt(index++);
				Date createdAt = rset.getDate(index++);
				int userId = rset.getInt(index++);

				return new Video(videoId, name, url, thumbnail,
						description, visibility, commentable, 
						voteable, blocked, views, createdAt);
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
}
