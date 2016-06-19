package edu.adriennicholas.atm.shared.model;

public class Account {
	
	private User user;

	public enum AccountType {
		SAVING, CHECKING
	}

	public enum ActionType {
		WITHDRAW, DEPOSIT, TRANSFER, CREATE, FREEZE, DELETE, BALANCE
	}

	public Account(User user, Float savingBalance, Float checkingBalance, ActionType action) {
		setUser(user);
		setSavingBalance(savingBalance);
		setCheckingBalance(checkingBalance);
		setActionType(action);
	}

	private AccountType accountType;
	private Float savingBalance;
	private Float checkingBalance;
	private ActionType action;

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public Float getSavingBalance() {
		return savingBalance;
	}

	public void setSavingBalance(Float amount) {
		this.savingBalance = amount;
	}

	public Float getCheckingBalance() {
		return checkingBalance;
	}

	public void setCheckingBalance(Float amount) {
		this.checkingBalance = amount;
	}

	private void setActionType(ActionType action) {
		this.action = action;
	}

	public ActionType getAction() {
		return this.action;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String toString() {
		return user + " " + checkingBalance + " " + accountType + " " + savingBalance;
	}

}
