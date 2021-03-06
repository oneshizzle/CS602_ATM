package edu.adriennicholas.atm.client.controller;

import edu.adriennicholas.atm.client.view.AdminMenuPanel;
import edu.adriennicholas.atm.util.EventMessage;
import edu.adriennicholas.atm.util.EventType;

public class AdminMenuPanelController extends MasterController {

	private AdminMenuPanel view;

	public AdminMenuPanelController(AdminMenuPanel view) {
		this.view = view;
		register();
	}

	@Override
	public void update(AbstractController observer, EventType eventType, EventMessage eventMessage) {

		if (observer == this) {
			switch (eventType) {
			case LOGIN_SUCCESS: {
				view.enablePanel(getUserSession().getCurrentUser().isAdmin());
				break;
			}
			case LOGIN_BLOCKED: {
				view.enablePanel(false);
				break;
			}
			default:
				break;
			}
		}
	}

	public void showCreateAccountPanel() {
		notifyObservers(EventType.SHOW_PANEL, new EventMessage("CREATE"));
	}

	public void showFreezeAccountPanel() {
		notifyObservers(EventType.SHOW_PANEL, new EventMessage("FREEZE"));
	}

	public void showDeleteAccountPanel() {
		notifyObservers(EventType.SHOW_PANEL, new EventMessage("DELETE"));
	}

	public void register() {
		register(EventType.LOGIN_ATTEMPT, this);
		register(EventType.LOGOUT_REQUEST, this);
		register(EventType.LOGIN_INVALID, this);
		register(EventType.LOGIN_SUCCESS, this);
		register(EventType.LOGIN_BLOCKED, this);
	}

}
