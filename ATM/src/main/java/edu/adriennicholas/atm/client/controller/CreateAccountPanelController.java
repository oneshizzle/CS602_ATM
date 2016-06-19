package edu.adriennicholas.atm.client.controller;

import edu.adriennicholas.atm.client.view.CreateAccountPanel;
import edu.adriennicholas.atm.server.UserService;
import edu.adriennicholas.atm.shared.model.Account;
import edu.adriennicholas.atm.util.EventMessage;
import edu.adriennicholas.atm.util.EventType;

public class CreateAccountPanelController extends MasterController {

	private CreateAccountPanel view;
	private UserService userService = new UserService();

	public CreateAccountPanelController(CreateAccountPanel view) {
		this.view = view;
		this.register();
	}

	@Override
	public void update(AbstractController observer, EventType eventType, EventMessage arg1) {
		if (observer == this) {
			if (eventType == EventType.LOGIN_SUCCESS) {
			}
		}
	}

	public void createAccount(Account account) {
		getAccountService().createAccount(account);
	}

	public void register() {
		this.register(EventType.LOGIN_SUCCESS, this);
	}

}
