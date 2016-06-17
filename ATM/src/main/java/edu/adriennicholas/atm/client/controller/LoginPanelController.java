package edu.adriennicholas.atm.client.controller;

import edu.adriennicholas.atm.client.view.LoginPanel;
import edu.adriennicholas.atm.server.UserService;
import edu.adriennicholas.atm.shared.model.User;
import edu.adriennicholas.atm.util.EventMessage;
import edu.adriennicholas.atm.util.EventType;

public class LoginPanelController extends MasterController {

	private LoginPanel view;
	private UserService loginService = new UserService();

	public LoginPanelController(LoginPanel view) {
		this.view = view;
	}

	public void attemptLogin(String username, String pin) {
		System.out.println(username + " " + pin);
		view.enableMessagePanel(false);
		User currentUser = loginService.login(username, pin);

		if (currentUser != null) {
			if (currentUser.isAuthorized()) {
				getUserSession().setCurrentUser(currentUser);
				notifyObservers(EventType.LOGIN_SUCCESS, new EventMessage(currentUser));
			} else {
				notifyObservers(EventType.LOGIN_BLOCKED, new EventMessage(currentUser));
			}
		} else {
			notifyObservers(EventType.LOGIN_INVALID, new EventMessage(""));
			view.setMessagePanelText("Invalid User Credentials");
			view.enableMessagePanel(true);
		}
	}

	@Override
	public void update(AbstractController observer, EventType eventType, EventMessage arg1) {
		if (observer == this) {

		}
	}

	public void register() {
		this.register(EventType.LOGIN_ATTEMPT, this);

	}

}
