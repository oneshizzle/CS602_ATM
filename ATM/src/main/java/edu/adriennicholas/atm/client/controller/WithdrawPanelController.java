package edu.adriennicholas.atm.client.controller;

import java.util.ArrayList;
import java.util.List;

import edu.adriennicholas.atm.client.view.WithdrawPanel;
import edu.adriennicholas.atm.server.UserService;
import edu.adriennicholas.atm.shared.model.Account;
import edu.adriennicholas.atm.shared.model.User;
import edu.adriennicholas.atm.util.EventMessage;
import edu.adriennicholas.atm.util.EventType;

public class WithdrawPanelController extends MasterController {

	private WithdrawPanel view;
	private UserService userService = new UserService();

	public WithdrawPanelController(WithdrawPanel view) {
		this.view = view;
		this.register();
	}

	@Override
	public void update(AbstractController observer, EventType eventType, EventMessage arg1) {
		if (observer == this) {
			if (eventType == EventType.LOGIN_SUCCESS) {
				List<String> usernames = new ArrayList<String>();
				if (getUserSession().getCurrentUser().isAdmin()) {
					for (User user : userService.findAccountUsers()) {
						usernames.add(user.getUserName());
					}
					view.setAccountUserList(usernames);
				} else {
					String username = getUserSession().getCurrentUser().getUserName();
					usernames.add(username);
					view.setAccountUserList(usernames);
				}

			}
		}
	}

	public Account withdraw(Account account, Float amount) {
		return null;
	}

	public void register() {
		this.register(EventType.LOGIN_SUCCESS, this);
	}

}
