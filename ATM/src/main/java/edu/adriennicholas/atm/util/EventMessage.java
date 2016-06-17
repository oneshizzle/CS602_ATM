package edu.adriennicholas.atm.util;

public class EventMessage {

	private Object message;
	
	public EventMessage(Object message){
		setMessage(message);
	}

	public Object getMessage() {
		return message;
	}

	private void setMessage(Object message) {
		this.message = message;
	}

}
