package edu.adriennicholas.atm.server;

import java.util.ArrayList;
import java.util.List;

import edu.adriennicholas.atm.shared.model.TransactionObject;
import edu.adriennicholas.atm.shared.model.User;
import edu.adriennicholas.atm.shared.model.User.UserRole;

public class UserService {

	private SocketUtil socketUtil = new SocketUtil();

	public User login(String username, String password) {
		TransactionObject transactionObject  = new TransactionObject();
		transactionObject.setName(username);
		transactionObject.setNum(password);
		transactionObject.setId("LOGIN");
		
		transactionObject = socketUtil.sendTransaction(transactionObject);
		
		User user = null;
		if (username.equals("BAD")) {

		} else if (username.equals("ADRIEN")) {
			user = new User(UserRole.ADMIN, "ADRIEN", true);
		} else {
			user = new User(UserRole.USER, username, true);
		}
		return user;
	}

	public void createUser(User user) {
		TransactionObject to = new TransactionObject();
		to.setName(user.getUserName());
	}

	public List<User> findAccountUsers() {
		List<User> users = new ArrayList<User>();
		users.add(new User(UserRole.ADMIN, "ADRIEN", true));
		users.add(new User(UserRole.ADMIN, "MARSHA", true));
		users.add(new User(UserRole.ADMIN, "LILIAH", true));
		users.add(new User(UserRole.ADMIN, "RANEKA", true));
		return users;
	}
}
