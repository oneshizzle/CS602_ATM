package edu.adriennicholas.atm.client.controller;

import java.util.ArrayList;
import java.util.List;

import edu.adriennicholas.atm.client.view.DepositPanel;
import edu.adriennicholas.atm.shared.model.Account;
import edu.adriennicholas.atm.shared.model.User;
import edu.adriennicholas.atm.shared.model.Account.AccountType;
import edu.adriennicholas.atm.util.EventMessage;
import edu.adriennicholas.atm.util.EventType;

public class DepositPanelController extends MasterController {

	private DepositPanel view;

	public DepositPanelController(DepositPanel view) {
		this.view = view;
		this.register();
	}

	@Override
	public void update(AbstractController observer, EventType eventType, EventMessage arg1) {
		if (observer == this) {
			if (eventType == EventType.LOGIN_SUCCESS) {
				List<String> usernames = new ArrayList<String>();
				if (getUserSession().getCurrentUser().isAdmin()) {
					for (User user : getUserService().findAccountUsers()) {
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

	public void deposit(Account account, Float amount) {
		if (account.getAccountType().equals(AccountType.CHECKING)) {
			account.setCheckingBalance(account.getCheckingBalance() + amount);
		} else {
			account.setSavingBalance(account.getSavingBalance() + amount);
		}

		Float balance = getAccountService().deposit(account);
		view.clear();
		view.enableMessagePanel(true);
		view.setMessagePanelText("The new balance is: " + balance);
	}

	public void register() {
		this.register(EventType.LOGIN_SUCCESS, this);
	}

}
