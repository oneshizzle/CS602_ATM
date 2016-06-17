package edu.adriennicholas.atm.client.controller;

import edu.adriennicholas.atm.client.view.LeftMenuPanel;
import edu.adriennicholas.atm.util.EventMessage;
import edu.adriennicholas.atm.util.EventType;

public class LeftMenuPanelController extends MasterController {

	private LeftMenuPanel view;

	public LeftMenuPanelController(LeftMenuPanel view) {
		this.view = view;
		register();
	}

	@Override
	public void update(AbstractController observer, EventType eventType, EventMessage arg1) {

		if (observer == this) {
			switch (eventType) {
			case LOGIN_SUCCESS: {
				view.enablePanel(true);
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

	public void showDepositPanel() {
		notifyObservers(EventType.SHOW_PANEL, new EventMessage("DEPOSIT"));
	}

	public void showWithdrawPanel() {
		notifyObservers(EventType.SHOW_PANEL, new EventMessage("WITHDRAW"));
	}

	public void showTransferPanel() {
		notifyObservers(EventType.SHOW_PANEL, new EventMessage("TRANSFER"));
	}

	public void showBalancePanel() {
		notifyObservers(EventType.SHOW_PANEL, new EventMessage("BALANCE"));
	}

	public void register() {
		register(EventType.LOGIN_ATTEMPT, this);
		register(EventType.LOGOUT_REQUEST, this);
		register(EventType.LOGIN_INVALID, this);
		register(EventType.LOGIN_SUCCESS, this);
		register(EventType.LOGIN_BLOCKED, this);
	}

}
