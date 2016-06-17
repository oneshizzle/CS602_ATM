package edu.adriennicholas.atm.util;

import edu.adriennicholas.atm.shared.model.User;

public class UserSession {

	private User currentUser;

	private static UserSession userSession;

	private UserSession() {
	}

	public static UserSession getInstance() {
		if (userSession == null) {
			userSession = new UserSession();
		}
		return userSession;
	}

	public User getCurrentUser() {
		return userSession.currentUser;
	}

	public void setCurrentUser(User currentUser) {
		userSession.currentUser = currentUser;
	}

}
