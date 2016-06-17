package edu.adriennicholas.atm.shared.model;

public class Account {

	public enum AccountType {
		SAVING, CHECKING
	}

	public enum ActionType {
		WITHDRAW, DEPOSIT, TRANSFER, CREATE, FREEZE, DELETE
	}

	public Account(AccountType accountType, String username, Float savingBalance, Float checkingBalance, ActionType action) {
		setAccountType(accountType);
		setUserName(username);
		setSavingBalance(savingBalance);
		setCheckingBalance(checkingBalance);
		setActionType(action);
	}

	private AccountType accountType;
	private String userName;
	private Float savingBalance;
	private Float checkingBalance;
	private ActionType action;

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public String getUserName() {
		return userName;
	}

	private void setUserName(String userName) {
		this.userName = userName;
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

	public String toString() {
		return userName + " " + checkingBalance + " " + accountType + " " + savingBalance;
	}

}
