package edu.adriennicholas.atm.server;

import edu.adriennicholas.atm.shared.model.Account;
import edu.adriennicholas.atm.shared.model.User;
import edu.adriennicholas.atm.shared.model.Account.ActionType;
import edu.adriennicholas.atm.shared.model.User.UserRole;
import edu.adriennicholas.atm.shared.model.TransactionObject;

public class AccountService {

	private SocketUtil socketUtil = new SocketUtil();

	public Float deposit(Account account) {
		return 0.0f;
	}

	public Float withdraw(Account account) {
		return 0.0f;
	}

	public Float transfer(Account account) {
		return 0.0f;
	}

	public Account fetchBalance(String username) {
		TransactionObject tx = new TransactionObject();
		tx.setName(username);
		tx.setId(ActionType.BALANCE.name());

		TransactionObject txResponse = socketUtil.sendTransaction(tx);

		Float saving = new Float(txResponse.getMessage().substring("saving=".length(), txResponse.getMessage().indexOf(":")));
		Float checking = new Float(txResponse.getMessage().substring(txResponse.getMessage().indexOf(":") + "checking=".length() + 1,
				txResponse.getMessage().length()));

		User user = null;
		UserRole role = null;
		if (txResponse.getId().contains("ADMIN")) {
			role = UserRole.ADMIN;
		} else {
			role = UserRole.USER;
		}

		boolean authorized = true;
		user = new User(role, txResponse.getName(), authorized);

		Account response = new Account(user, saving, checking, ActionType.BALANCE);
		return response;
	}

	public void createAccount(Account account) {
		TransactionObject tx = new TransactionObject();
		String message = new String();
		message = "saving=" + account.getSavingBalance() + ":checking=" + account.getCheckingBalance();
		tx.setMessage(message);
		tx.setId(account.getAction().name());
		tx.setType(account.getUser().getUserRole().name());
		tx.setName(account.getUser().getUserName());
		tx.setNum(account.getUser().getPassword());

		socketUtil.sendTransaction(tx);
	}
}
