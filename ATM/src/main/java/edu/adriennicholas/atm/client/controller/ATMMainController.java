package edu.adriennicholas.atm.client.controller;

import edu.adriennicholas.atm.ATMMainWindow;
import edu.adriennicholas.atm.util.EventMessage;
import edu.adriennicholas.atm.util.EventType;

public class ATMMainController extends MasterController {

	private ATMMainWindow view;

	public ATMMainController(ATMMainWindow view) {
		this.view = view;
		register();
	}

	@Override
	public void update(AbstractController observer, EventType eventType, EventMessage arg1) {
		if (observer == this) {
			switch (eventType) {
			case LOGIN_BLOCKED: {
				view.showMainPanel();
				break;
			}
			case LOGIN_SUCCESS: {
				view.showMainPanel();
				break;
			}
			case LOGOUT_REQUEST: {
				view.showLoginPanel();
				break;
			}
			default:
				break;

			}
		}
	}

	public void register() {
		this.register(EventType.LOGIN_SUCCESS, this);
		this.register(EventType.LOGIN_BLOCKED, this);
		this.register(EventType.LOGOUT_REQUEST, this);
	}

}
