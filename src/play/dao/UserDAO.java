package play.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import play.model.User;
import play.model.User.Role;

public class UserDAO {
	
	public static boolean create(User user) {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		try {
			String query = "INSERT INTO users (username, password, firstname, lastname, email, description, role) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?)";

			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setString(index++, user.getUsername());
			pstmt.setString(index++, user.getPassword());
			pstmt.setString(index++, user.getFirstName());
			pstmt.setString(index++, user.getLastName());
			pstmt.setString(index++, user.getEmail());
			pstmt.setString(index++, user.getPassword());
			pstmt.setString(index++, user.getRole().toString());
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
	
	public static List<User> getAll() {
		List<User> users = new ArrayList<>();

		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT id, username, firstname, lastname, email, role "
					+ "FROM users ORDER BY registered_at DESC";

			pstmt = conn.prepareStatement(query);
			int index = 1;
			System.out.println(pstmt);

			rset = pstmt.executeQuery();
			while (rset.next()) {
				index = 1;
				int id = rset.getInt(index++);
				String username = rset.getString(index++);
				String firstName = rset.getString(index++);
				String lastName = rset.getString(index++);
				String email = rset.getString(index++);
				Role role = Role.valueOf(rset.getString(index++));

				User user = new User();
				
				user.setUsername(username);
				user.setFirstName(firstName);
				user.setLastName(lastName);
				user.setEmail(email);
				user.setRole(role);
				
				users.add(user);
			}
		} catch (SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		
		return users;
	}
	
	public static User get(int user_id) {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT * FROM users WHERE id = ?";

			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setInt(index++, user_id);
			System.out.println(pstmt);

			rset = pstmt.executeQuery();
			if (rset.next()) {
				index = 1;
				int id = rset.getInt(index++);
				String username = rset.getString(index++);
				String password = rset.getString(index++);
				String firstName = rset.getString(index++);
				String lastName = rset.getString(index++);
				String email = rset.getString(index++);
				String description = rset.getString(index++);
				Date registeredAt = rset.getDate(index++);
				boolean banned = rset.getBoolean(index++);
				Role role = Role.valueOf(rset.getString(index++));
				
				return new User(id, username, password, firstName, lastName, email,
						description, registeredAt, banned, role);
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
	
	public static User get(String user_name) {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT * FROM users WHERE username = ?";

			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setString(index++, user_name);
			System.out.println(pstmt);

			rset = pstmt.executeQuery();
			if (rset.next()) {
				index = 1;
				int id = rset.getInt(index++);
				String username = rset.getString(index++);
				String password = rset.getString(index++);
				String firstName = rset.getString(index++);
				String lastName = rset.getString(index++);
				String email = rset.getString(index++);
				String description = rset.getString(index++);
				Date registeredAt = rset.getDate(index++);
				boolean banned = rset.getBoolean(index++);
				Role role = Role.valueOf(rset.getString(index++));
				
				return new User(id, username, password, firstName, lastName, email,
						description, registeredAt, banned, role);
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
	
	public static boolean update(User user) {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		try {
			String query = "UPDATE users SET firstname = ?, lastname = ?, email = ?, description = ? WHERE username = ?";

			pstmt = conn.prepareStatement(query);
			int index = 1;
			
			pstmt.setString(index++, user.getFirstName());
			pstmt.setString(index++, user.getLastName());
			pstmt.setString(index++, user.getEmail());
			pstmt.setString(index++, user.getDescription());
			pstmt.setString(index++, user.getUsername());
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
