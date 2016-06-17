package edu.adriennicholas.atm.client.controller;

import edu.adriennicholas.atm.client.view.OptionsPanel;
import edu.adriennicholas.atm.util.EventMessage;
import edu.adriennicholas.atm.util.EventType;

public class OptionsPanelController extends MasterController {

	private OptionsPanel view;

	public OptionsPanelController(OptionsPanel view) {
		this.view = view;
		this.register();
	}

	public void logout() {
		System.out.println("logout");
		super.notifyObservers(EventType.LOGOUT_REQUEST, new EventMessage(
				"Adrien Nicholas"));

	}

	@Override
	public void update(AbstractController observer, EventType eventType,
			EventMessage message) {
		if (observer == this) {
			switch (eventType) {
			case SHOW_PANEL: {
				view.showPanel((String) message.getMessage());
				break;
			}
			case LOGIN_SUCCESS: {
				view.showPanel("BLANK");
				break;
			}
			case LOGOUT_REQUEST: {
				view.showPanel("BLANK");
				break;
			}
			case LOGIN_INVALID: {
				view.showPanel("BLANK");
				break;
			}
			case LOGIN_BLOCKED: {
				view.showPanel("BLANK");
				break;
			}
			default:
				break;

			}
		}
	}

	public void register() {
		register(EventType.LOGOUT_REQUEST, this);
		register(EventType.LOGIN_INVALID, this);
		register(EventType.LOGIN_SUCCESS, this);
		register(EventType.LOGIN_BLOCKED, this);
		register(EventType.SHOW_PANEL, this);
	}

}
