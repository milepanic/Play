package play.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import play.model.User;

public class UserDAO {
	
	public static boolean create(User user) {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		try {
			String query = "INSERT INTO users (username, password, firstname, lastname, email, description) "
					+ "VALUES (?, ?, ?, ?, ?, ?)";

			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setString(index++, user.getUsername());
			pstmt.setString(index++, user.getPassword());
			pstmt.setString(index++, user.getFirstName());
			pstmt.setString(index++, user.getLastName());
			pstmt.setString(index++, user.getEmail());
			pstmt.setString(index++, user.getPassword());
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
	
	public static User get(int id) {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT * "
					+ "FROM users WHERE id = ?";

			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setInt(index++, id);
			System.out.println(pstmt);

			rset = pstmt.executeQuery();
			if (rset.next()) {
				index = 1;
				int userId = rset.getInt(index++);
				String username = rset.getString(index++);
				String password = rset.getString(index++);
				String firstName = rset.getString(index++);
				String lastName = rset.getString(index++);
				String email = rset.getString(index++);
				String description = rset.getString(index++);
				Date registeredAt = rset.getDate(index++);
				//Role role = Role.valueOf(rset.getString(index++));
				boolean banned = rset.getBoolean(index++);
				
				return new User(username, password, firstName, lastName, email,
						description, registeredAt, banned);
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
