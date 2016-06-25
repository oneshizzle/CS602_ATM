package edu.adriennicholas.atm.client.controller;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import edu.adriennicholas.atm.client.view.WithdrawPanel;
import edu.adriennicholas.atm.server.UserService;
import edu.adriennicholas.atm.shared.model.Account;
import edu.adriennicholas.atm.shared.model.User;
import edu.adriennicholas.atm.shared.model.Account.AccountType;
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

	public void withdraw(Account account, Float amount) {
		if (account.getAccountType().equals(AccountType.CHECKING)) {
			account.setCheckingBalance(account.getCheckingBalance() - amount);
			view.setMessagePanelText("The new balance is: " + NumberFormat.getCurrencyInstance().format(account.getCheckingBalance()));

		} else {
			account.setSavingBalance(account.getSavingBalance() - amount);
			view.setMessagePanelText("The new balance is: " + NumberFormat.getCurrencyInstance().format(account.getSavingBalance()));
		}

		getAccountService().deposit(account);
		getAccountService().fetchBalance(account.getUser().getUserName());
		notifyObservers(EventType.TRANSACTION_COMPLETED, new EventMessage(""));
		view.enableMessagePanel(true);
	}

	public void register() {
		register(EventType.LOGIN_SUCCESS, this);
		register(EventType.USER_ADDED, this);
		register(EventType.TRANSACTION_COMPLETED, this);
	}

}
