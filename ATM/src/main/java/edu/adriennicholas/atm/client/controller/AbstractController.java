package edu.adriennicholas.atm.client.controller;

import edu.adriennicholas.atm.util.EventMessage;
import edu.adriennicholas.atm.util.EventType;

public abstract class AbstractController {

	public abstract void update(AbstractController observer,
			EventType eventType, EventMessage eventMessage);

	public abstract void notifyObservers(EventType eventType,
			EventMessage eventMessage);

}
