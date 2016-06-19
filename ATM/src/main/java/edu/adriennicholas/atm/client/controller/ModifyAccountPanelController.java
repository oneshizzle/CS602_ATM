package edu.adriennicholas.atm.client.controller;

import java.util.ArrayList;
import java.util.List;

import edu.adriennicholas.atm.client.view.ModifyAccountPanel;
import edu.adriennicholas.atm.server.UserService;
import edu.adriennicholas.atm.shared.model.User;
import edu.adriennicholas.atm.util.EventMessage;
import edu.adriennicholas.atm.util.EventType;

public class ModifyAccountPanelController extends MasterController {

	private ModifyAccountPanel view;

	public ModifyAccountPanelController(ModifyAccountPanel view) {
		this.view = view;
		this.register();
	}

	@Override
	public void update(AbstractController observer, EventType eventType, EventMessage arg1) {
		if (observer == this) {
			if (eventType == EventType.LOGIN_SUCCESS || eventType == EventType.USER_ADDED) {
			}
		}
	}

	public void register() {
		register(EventType.LOGIN_SUCCESS, this);
		register(EventType.USER_ADDED, this);
	}

	public void freezeUser(String username) {
		getAccountService().freezeAccount(username);
	}

	public void reactiveUser(String username) {
		getAccountService().reActivateAccount(username);
	}

	public void deleteUser(String username) {
		getAccountService().deleteAccount(username);
	}

}
