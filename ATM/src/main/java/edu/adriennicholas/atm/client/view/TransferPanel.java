package edu.adriennicholas.atm.client.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import edu.adriennicholas.atm.client.controller.TransferPanelController;
import edu.adriennicholas.atm.shared.model.Account;
import edu.adriennicholas.atm.shared.model.Account.ActionType;
import edu.adriennicholas.atm.util.Utils;

public class TransferPanel extends JPanel {

	private TransferPanelController controller = new TransferPanelController(this);

	private JLabel message = new JLabel();
	private JButton submit;
	private JPanel panel;
	private JTextField amountBox;
	private String[] accountTypes = { "Checking", "Saving" };

	private JComboBox<String> fromAccountList = new JComboBox<String>(accountTypes);
	private JComboBox<String> toAccountList = new JComboBox<String>(accountTypes);
	private JComboBox<String> accountUserList = new JComboBox<String>();

	private JButton resetBtn;

	private JTextField savingAmountBox;
	private JTextField checkingAmountBox;
	private JLabel checkingAmount = new JLabel("Checking Bal.");
	private JLabel savingAmount = new JLabel("Saving Bal.");

	private final static int xOffSet = 55;
	private final static int yOffSet = 80;

	public TransferPanel() {
		super();
		panel = new JPanel();
		amountBox = Utils.createNumericTextField();
		savingAmountBox = Utils.createNumericTextField();
		checkingAmountBox = Utils.createNumericTextField();

		submit = new JButton("Transfer");
		resetBtn = new JButton("Reset");

		JLabel accountUser = new JLabel("Account User");

		JLabel heading = new JLabel("Please enter the transfer details: ");
		JLabel balanceStatus = new JLabel("Balance: " + 100.01 + " (Checking) " + 75.01 + " (Saving)");
		JLabel amount = new JLabel("Amount");

		JLabel toAccountType = new JLabel("To:");
		JLabel fromAccountType = new JLabel("From:");

		setLayout(null);

		heading.setBounds(xOffSet, yOffSet - 55, 230, 20);
		heading.setHorizontalAlignment(SwingConstants.LEFT);

		accountUser.setBounds(xOffSet, yOffSet, 154, 20);
		accountUserList.setBounds(xOffSet + 90, yOffSet, 154, 20);

		savingAmountBox.setBounds(xOffSet + 90, yOffSet + 30, 154, 20);
		savingAmount.setBounds(xOffSet, yOffSet + 30, 80, 20);

		checkingAmountBox.setBounds(xOffSet + 90, yOffSet + 60, 154, 20);
		checkingAmount.setBounds(xOffSet, yOffSet + 60, 80, 20);

		amount.setBounds(xOffSet, yOffSet + 90, 85, 20);
		amountBox.setBounds(xOffSet + 90, yOffSet + 90, 154, 20);

		fromAccountList.setBounds(xOffSet + 90, yOffSet + 120, 154, 20);
		fromAccountType.setBounds(xOffSet, yOffSet + 120, 80, 20);

		toAccountList.setBounds(xOffSet + 90, yOffSet + 150, 154, 20);
		toAccountType.setBounds(xOffSet, yOffSet + 150, 80, 20);

		submit.setBounds(xOffSet, yOffSet + 210, 90, 20);
		resetBtn.setBounds(xOffSet + 140, yOffSet + 210, 100, 20);

		panel.add(submit);
		panel.add(amountBox);
		panel.add(fromAccountList);
		panel.add(toAccountList);
		panel.add(resetBtn);
		panel.add(amount);
		panel.add(heading);
		panel.add(balanceStatus);
		panel.add(fromAccountType);
		panel.add(toAccountType);
		panel.add(accountUser);
		panel.add(accountUserList);
		panel.add(checkingAmountBox);
		panel.add(checkingAmount);
		panel.add(savingAmountBox);
		panel.add(savingAmount);
		panel.add(message);

		message.setBounds(xOffSet, yOffSet + 230, 250, 20);
		message.setHorizontalAlignment(SwingConstants.LEFT);
		message.setVisible(false);

		amountBox.setHorizontalAlignment(SwingConstants.RIGHT);
		checkingAmountBox.setEditable(false);
		checkingAmountBox.setHorizontalAlignment(SwingConstants.RIGHT);
		savingAmountBox.setEditable(false);
		savingAmountBox.setHorizontalAlignment(SwingConstants.RIGHT);

		panel.setSize(500, 500);
		panel.setLayout(null);
		add(panel);
		setVisible(true);

		fromAccountList.setSelectedIndex(0);
		toAccountList.setSelectedIndex(1);

		resetBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshUI();
			}
		});

		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

					Float amount = 0.0f;

					if (amountBox.getText() != null && amountBox.getText().length() > 0) {
						amount = new Float(amountBox.getText());
					}

					Float checkingBalance = NumberFormat.getCurrencyInstance().parse(checkingAmountBox.getText())
							.floatValue();
					Float savingBalance = NumberFormat.getCurrencyInstance().parse(savingAmountBox.getText())
							.floatValue();

					enableMessagePanel(false);

					if (fromAccountList.getSelectedItem().toString().equalsIgnoreCase("Saving")) {
						if (savingBalance < amount) {
							setMessagePanelText("Saving balance is not sufficient");
							enableMessagePanel(true);
						} else {
							Account account = controller.fetchBalance(accountUserList.getSelectedItem().toString());
							account.setActionType(ActionType.TRANSFER);
							account.setSavingBalance(account.getSavingBalance() - amount);
							account.setCheckingBalance(account.getCheckingBalance() + amount);
							controller.transfer(account);
							setMessagePanelText("Transfer Completed.");
							enableMessagePanel(true);
							amountBox.setText("");
							accountUserList.setSelectedIndex(accountUserList.getSelectedIndex());
						}
					} else {
						if (checkingBalance < amount) {
							setMessagePanelText("Checking balance is not sufficient");
							enableMessagePanel(true);
						} else {
							Account account = controller.fetchBalance(accountUserList.getSelectedItem().toString());
							account.setActionType(ActionType.TRANSFER);
							account.setSavingBalance(account.getSavingBalance() + amount);
							account.setCheckingBalance(account.getCheckingBalance() - amount);
							controller.transfer(account);
							setMessagePanelText("Transfer Completed.");
							enableMessagePanel(true);
							amountBox.setText("");
							accountUserList.setSelectedIndex(accountUserList.getSelectedIndex());
						}
					}

				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		accountUserList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = (String) ((JComboBox<String>) e.getSource()).getSelectedItem();
				Account balance = controller.fetchBalance(username);
				checkingAmountBox.setText(NumberFormat.getCurrencyInstance().format(balance.getCheckingBalance()));
				savingAmountBox.setText(NumberFormat.getCurrencyInstance().format(balance.getSavingBalance()));
			}

		});

		fromAccountList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (((JComboBox) e.getSource()).getSelectedItem().toString().equalsIgnoreCase("Saving")) {
					toAccountList.setSelectedIndex(0);
				} else {
					toAccountList.setSelectedIndex(1);
				}
			}

		});

		toAccountList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (((JComboBox) e.getSource()).getSelectedItem().toString().equalsIgnoreCase("Saving")) {
					fromAccountList.setSelectedIndex(0);
				} else {
					fromAccountList.setSelectedIndex(1);
				}
			}
		});
	}

	public void setAccountUserList(List<String> accountUsers) {
		accountUserList.setEditable(false);
		accountUserList.removeAllItems();
		for (String accountUser : accountUsers) {
			accountUserList.addItem(accountUser);
		}
		if (accountUsers != null && accountUsers.size() > 1) {
			accountUserList.setEnabled(true);
		} else {
			accountUserList.setEnabled(false);
		}
	}

	public void refreshUI() {
		amountBox.setText("");
		enableMessagePanel(false);
		accountUserList.setSelectedIndex(accountUserList.getSelectedIndex());
	}

	public void setMessagePanelText(String text) {
		message.setText("<html><font color='red'>" + text + "</font></html>");
	}

	public void enableMessagePanel(boolean enable) {
		message.setVisible(enable);
	}
}
