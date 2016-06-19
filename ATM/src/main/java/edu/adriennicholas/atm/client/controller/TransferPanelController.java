package edu.adriennicholas.atm.client.controller;

import java.util.ArrayList;
import java.util.List;

import edu.adriennicholas.atm.client.view.TransferPanel;
import edu.adriennicholas.atm.server.UserService;
import edu.adriennicholas.atm.shared.model.Account;
import edu.adriennicholas.atm.shared.model.User;
import edu.adriennicholas.atm.util.EventMessage;
import edu.adriennicholas.atm.util.EventType;

public class TransferPanelController extends MasterController {

	private TransferPanel view;
	private UserService userService = new UserService();

	public TransferPanelController(TransferPanel view) {
		this.view = view;
		this.register();
	}

	@Override
	public void update(AbstractController observer, EventType eventType, EventMessage arg1) {
		if (observer == this) {
			if (eventType == EventType.LOGIN_SUCCESS || eventType == EventType.USER_ADDED) {
				List<String> usernames = new ArrayList<String>();
				if (getUserSession().getCurrentUser().isAdmin()) {
					for (String user : userService.findActiveAccountUsers()) {
						usernames.add(user);
					}
					view.setAccountUserList(usernames);
				} else {
					String username = getUserSession().getCurrentUser().getUserName();
					usernames.add(username);
					view.setAccountUserList(usernames);
				}

			} else if (eventType == EventType.TRANSACTION_COMPLETED) {
				view.refreshUI();
			}
		}
	}

	public void transfer(Account account) {
		getAccountService().transfer(account);
		notifyObservers(EventType.TRANSACTION_COMPLETED, new EventMessage(""));
	}

	public void register() {
		register(EventType.USER_ADDED, this);
		register(EventType.LOGIN_SUCCESS, this);
		register(EventType.TRANSACTION_COMPLETED, this);
	}

}
