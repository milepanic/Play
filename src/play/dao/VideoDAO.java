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
import play.model.User.Role;
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
			pstmt.setInt(index++, video.getUser().getId());
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
			String query = "SELECT id, name, url, thumbnail, views, created_at, user_id "
					+ "FROM videos WHERE visibility = 'PUBLIC' AND blocked = false AND deleted = false "
					+ "AND user_id NOT IN (SELECT id FROM users WHERE banned = true) "
					+ "ORDER BY created_at DESC";

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
				int views = rset.getInt(index++);
				Date createdAt = rset.getDate(index++);
				int user_id = rset.getInt(index++);
				
				User user = UserDAO.get(user_id);

				Video video = new Video();
				
				video.setId(id);
				video.SetName(name);
				video.setUrl(url);
				video.setThumbnail(thumbnail);
				video.setViews(views);
				video.setCreatedAt(createdAt);
				video.setUser(user);
				
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
	
	public static List<Video> getWhereUser(int id) {
		List<Video> videos = new ArrayList<>();
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT * "
					+ "FROM videos WHERE visibility = 'PUBLIC' AND blocked = false AND deleted = false AND "
					+ "user_id = ? ORDER BY created_at DESC";

			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setInt(index++, id);
			System.out.println(pstmt);

			rset = pstmt.executeQuery();
			while (rset.next()) {
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
				
				User user = UserDAO.get(userId);

				Video video = new Video(videoId, name, url, thumbnail,
						description, visibility, commentable, 
						voteable, blocked, views, createdAt, user);
				
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
	
	public static List<Video> getWhereUserWithParams(int id, String param, String rank) {
		List<Video> videos = new ArrayList<>();
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT * "
					+ "FROM videos WHERE visibility = 'PUBLIC' AND blocked = false AND deleted = false AND user_id = ?";
			
			if(param.contentEquals("created_at"))
				query += " ORDER BY created_at";
			else if(param.contentEquals("views"))
				query += " ORDER BY views";
			
			if(rank.contentEquals("DESC"))
				query += " DESC";

			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setInt(index++, id);
			System.out.println(pstmt);

			rset = pstmt.executeQuery();
			while (rset.next()) {
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
				
				User user = UserDAO.get(userId);

				Video video = new Video(videoId, name, url, thumbnail,
						description, visibility, commentable, 
						voteable, blocked, views, createdAt, user);
				
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
			String query = "SELECT * FROM videos WHERE id = ?";

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
				
				User user = UserDAO.get(userId);

				return new Video(videoId, name, url, thumbnail,
						description, visibility, commentable, 
						voteable, blocked, views, createdAt, user);
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
	
	public static List<Video> random(int id) {
		List<Video> videos = new ArrayList<>();
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT * FROM videos WHERE visibility = 'PUBLIC' "
					+ "AND blocked = false AND deleted = false "
					+ "AND user_id NOT IN (SELECT id FROM users WHERE banned = true) "
					+ "ORDER BY RAND() LIMIT ?";

			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setInt(index++, id);
			System.out.println(pstmt);

			rset = pstmt.executeQuery();
			while (rset.next()) {
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
				
				User user = UserDAO.get(userId);

				Video video = new Video(videoId, name, url, thumbnail,
						description, visibility, commentable, 
						voteable, blocked, views, createdAt, user);
				
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
	
	public static int last() {
		Connection conn = ConnectionManager.getConnection();
	
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT id FROM videos ORDER BY ID DESC LIMIT 1";
	
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
	
	public static int countViews(int userId) {
		Connection conn = ConnectionManager.getConnection();
	
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT sum(views) FROM videos WHERE user_id = ?";
			
			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setInt(index++, userId);
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
	
	public static int countVideos(int userId) {
		Connection conn = ConnectionManager.getConnection();
	
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT count(*) FROM videos WHERE user_id = ?";
			
			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setInt(index++, userId);
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
	
	public static boolean update(Video video) {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		try {
			String query = "UPDATE videos SET description = ?, visibility = ?, commentable = ?, "
					+ "voteable = ?, blocked = ?, views = ? WHERE id = ?";

			pstmt = conn.prepareStatement(query);
			int index = 1;
			
			pstmt.setString(index++, video.getDescription());
			pstmt.setString(index++, video.getVisibility().toString());
			pstmt.setBoolean(index++, video.isCommentable());
			pstmt.setBoolean(index++, video.isVoteable());
			pstmt.setBoolean(index++, video.isBlocked());
			pstmt.setInt(index++, video.getViews());
			pstmt.setInt(index++, video.getId());
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
	
	public static boolean delete(int id, Role role) {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		try {
			String query = "UPDATE videos SET deleted = true WHERE id = ?";
			
			//if(role.equals(Role.ADMIN))
				//query = "DELETE FROM videos WHERE id = ?";

			pstmt = conn.prepareStatement(query);
			int index = 1;
			
			pstmt.setInt(index++, id);
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
