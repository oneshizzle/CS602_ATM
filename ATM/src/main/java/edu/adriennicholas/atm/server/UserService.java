package edu.adriennicholas.atm.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.adriennicholas.atm.shared.model.TransactionObject;
import edu.adriennicholas.atm.shared.model.User;
import edu.adriennicholas.atm.shared.model.User.UserRole;

public class UserService {

	private SocketUtil socketUtil = new SocketUtil();

	public User login(String username, String password) {
		TransactionObject request = new TransactionObject();
		request.setName(username);
		request.setNum(password);
		request.setId("LOGIN");

		TransactionObject response = socketUtil.sendTransaction(request);

		User user = null;
		if (response != null && response.getName() != null) {
			UserRole role = null;
			if (response.getId().contains("ADMIN")) {
				role = UserRole.ADMIN;
			} else {
				role = UserRole.USER;
			}

			String status = response.getType();
			boolean authorized = true;
			if (status == null || status.trim().length() < 1) {
				authorized = false;
			}
			user = new User(role, response.getName(), authorized);
		}

		return user;
	}

	public List<String> findAccountUsers() {
		TransactionObject request = new TransactionObject();
		request.setId("FETCH_USERS");

		TransactionObject response = socketUtil.sendTransaction(request);
		List<String> users = new ArrayList<String>();

		if (response != null && response.getMessage() != null && response.getMessage().length() > 0) {
			String temp = response.getMessage();
			temp = temp.substring(1, temp.length()) + ",";
			users = Arrays.asList(temp.split(","));
		}

		return users;
	}
}
