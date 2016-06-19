package edu.adriennicholas.atm.shared.model;

import java.io.Serializable;
import java.util.Date;

public class TransactionObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String type;
	String id;
	String num;
	String name;
	String message;
	Date date;
	Float amount;

	public TransactionObject() {
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getNum() {
		return num;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDate() {
		return date;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public float getAmount() {
		return amount;
	}

}
