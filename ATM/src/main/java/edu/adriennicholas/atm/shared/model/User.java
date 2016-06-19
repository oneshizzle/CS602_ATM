package edu.adriennicholas.atm.shared.model;

public class User {

	public enum UserRole {
		ADMIN, USER
	}

	private UserRole userRole;
	private String userName;
	private String password;
	private Boolean authorized = false;

	public User(UserRole userRole, String userName, Boolean authorized) {
		setUserName(userName);
		setUserRole(userRole);
		setAuthorized(authorized);
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Boolean isAuthorized() {
		return authorized;
	}

	public Boolean isAdmin() {
		return (userRole != null && userRole == UserRole.ADMIN);
	}

	private void setAuthorized(Boolean authorized) {
		this.authorized = authorized;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String toString() {
		return userName + " " + userRole + " " + authorized;
	}

}
