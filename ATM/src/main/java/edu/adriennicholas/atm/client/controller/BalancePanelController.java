package edu.adriennicholas.atm.client.controller;

import java.util.ArrayList;
import java.util.List;

import edu.adriennicholas.atm.client.view.BalancePanel;
import edu.adriennicholas.atm.shared.model.Account;
import edu.adriennicholas.atm.shared.model.Account.AccountType;
import edu.adriennicholas.atm.shared.model.User;
import edu.adriennicholas.atm.util.EventMessage;
import edu.adriennicholas.atm.util.EventType;

public class BalancePanelController extends MasterController {

	private BalancePanel view;

	public BalancePanelController(BalancePanel view) {
		this.view = view;
		this.register();
	}

	@Override
	public void update(AbstractController observer, EventType eventType, EventMessage arg1) {
		if (observer == this) {
			if (eventType == EventType.LOGIN_SUCCESS) {
				List<String> usernames = new ArrayList<String>();
				if (getUserSession().getCurrentUser().isAdmin()) {
					for (String user : getUserService().findAccountUsers()) {
						usernames.add(user);
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

	public void register() {
		this.register(EventType.LOGIN_SUCCESS, this);
	}

}
