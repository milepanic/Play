package play.model;

import java.util.Date;

public class User {

	public enum Role {USER, ADMIN};
	
	private int id;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private String description;
	private String registeredAt;
	private boolean banned;
	private Role role;
	private boolean deleted;
	
	public User(int id, String username, String password, String firstName, String lastName, 
			String email, String description, String registeredAt, boolean banned, Role role, boolean deleted) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.description = description;
		this.registeredAt = registeredAt;
		this.banned = banned;
		this.role = role;
		this.deleted = deleted;
	}
	
	public User() {
		id = 0;
		username = "";
		password = "";
		firstName = "";
		lastName = "";
		email = "";
		description = "";
		registeredAt = "";
		banned = false;
		role = Role.USER;
		deleted = false;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRegisteredAt() {
		return registeredAt;
	}

	public void setRegisteredAt(String registeredAt) {
		this.registeredAt = registeredAt;
	}


	public boolean isBanned() {
		return banned;
	}

	public void setBanned(boolean banned) {
		this.banned = banned;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
}
