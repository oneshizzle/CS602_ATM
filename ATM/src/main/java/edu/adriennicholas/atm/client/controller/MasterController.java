package edu.adriennicholas.atm.client.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.adriennicholas.atm.server.AccountService;
import edu.adriennicholas.atm.server.UserService;
import edu.adriennicholas.atm.shared.model.Account;
import edu.adriennicholas.atm.util.EventMessage;
import edu.adriennicholas.atm.util.EventType;
import edu.adriennicholas.atm.util.UserSession;

public class MasterController extends AbstractController {

	private static HashMap<EventType, List<AbstractController>> registry = new HashMap<EventType, List<AbstractController>>();
	private static UserSession userSession = UserSession.getInstance();

	private static UserService userService = new UserService();
	private static AccountService accountService = new AccountService();

	public void register(EventType eventType, AbstractController controller) {
		List<AbstractController> observers = registry.get(eventType);

		if (observers == null) {
			observers = new ArrayList<AbstractController>();
		}

		observers.add(controller);
		registry.put(eventType, observers);
	}

	@Override
	public void update(AbstractController observer, EventType eventType, EventMessage arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifyObservers(EventType eventType, EventMessage eventMessage) {
		List<AbstractController> observers = registry.get(eventType);
		for (AbstractController observer : observers) {
			observer.update(observer, eventType, eventMessage);
		}
	}

	public UserSession getUserSession() {
		return userSession;
	}

	public AccountService getAccountService() {
		return accountService;
	}

	public UserService getUserService() {
		return userService;
	}

	public Account getBalances(String username) {
		return accountService.getBalance(username);
	}

}
