package edu.adriennicholas.atm.client.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import edu.adriennicholas.atm.client.controller.WithdrawPanelController;
import edu.adriennicholas.atm.shared.model.Account;
import edu.adriennicholas.atm.shared.model.Account.AccountType;
import edu.adriennicholas.atm.util.Utils;

public class WithdrawPanel extends JPanel {

	private WithdrawPanelController controller = new WithdrawPanelController(this);

	private JButton submit;
	private JPanel panel;
	private JTextField amountBox;
	private JTextField balanceBox;
	Account currentAccount;

	private String[] accountTypes = { "Checking", "Saving" };
	private JComboBox<String> accountList = new JComboBox<String>(accountTypes);
	private JComboBox<String> accountUserList = new JComboBox<String>();
	private JLabel accountUser = new JLabel("Account User");
	JButton clearBtn;

	private JLabel message = new JLabel();

	private final static int xOffSet = 55;
	private final static int yOffSet = 80;

	public WithdrawPanel() {
		super();
		submit = new JButton("Withdraw");
		panel = new JPanel();
		amountBox = Utils.createNumericTextField();
		balanceBox = Utils.createNumericTextField();
		clearBtn = new JButton("Clear?");

		JLabel heading = new JLabel("Please enter the withdrawal details: ");
		JLabel amountLabel = new JLabel("Amount");
		JLabel balanceAmount = new JLabel("Balance");

		JLabel accountType = new JLabel("Account Type");

		setLayout(null);

		heading.setBounds(xOffSet, yOffSet - 55, 230, 20);
		heading.setHorizontalAlignment(SwingConstants.LEFT);

		balanceAmount.setBounds(xOffSet, yOffSet - 20, 80, 20);
		balanceBox.setBounds(xOffSet + 90, yOffSet - 20, 154, 20);

		accountUser.setBounds(xOffSet, yOffSet + 10, 154, 20);
		accountUserList.setBounds(xOffSet + 90, yOffSet + 10, 154, 20);

		amountBox.setBounds(xOffSet + 90, yOffSet + 45, 154, 20);
		amountLabel.setBounds(xOffSet, yOffSet + 45, 80, 20);

		accountList.setBounds(xOffSet + 90, yOffSet + 75, 154, 20);
		accountType.setBounds(xOffSet, yOffSet + 75, 80, 20);

		submit.setBounds(xOffSet, yOffSet + 168, 90, 20);
		clearBtn.setBounds(xOffSet + 140, yOffSet + 168, 100, 20);

		message.setBounds(xOffSet, yOffSet + 198, 250, 20);
		message.setHorizontalAlignment(SwingConstants.LEFT);
		message.setVisible(false);

		balanceBox.setEditable(false);
		balanceBox.setHorizontalAlignment(SwingConstants.RIGHT);
		amountBox.setHorizontalAlignment(SwingConstants.RIGHT);

		panel.add(message);
		panel.add(submit);
		panel.add(amountBox);
		panel.add(accountList);
		panel.add(clearBtn);
		panel.add(amountLabel);
		panel.add(heading);
		panel.add(balanceBox);
		panel.add(balanceAmount);
		panel.add(accountType);
		panel.add(accountUserList);
		panel.add(accountUser);

		panel.setSize(500, 500);
		panel.setLayout(null);
		add(panel);
		setVisible(true);

		accountUserList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = (String) ((JComboBox<String>) e.getSource()).getSelectedItem();
				currentAccount = controller.fetchBalance(username);
				accountList.setSelectedIndex(accountList.getSelectedIndex());
			}

		});

		clearBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enableMessagePanel(false);
				amountBox.setText("");
			}

		});

		accountList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String accountType = (String) ((JComboBox<String>) e.getSource()).getSelectedItem();
				if (accountUserList != null && accountUserList.getSelectedItem() != null) {
					if (accountType.equalsIgnoreCase("Checking")) {
						balanceBox.setText(currentAccount.getCheckingBalance().toString());
					} else {
						balanceBox.setText(currentAccount.getSavingBalance().toString());
					}
				}
			}
		});

		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enableMessagePanel(false);
				if (amountBox.getText() == null || amountBox.getText().length() < 1) {
					setMessagePanelText("Please enter the dollar amount");
					enableMessagePanel(true);
				} else if (new Float(amountBox.getText()) > 100) {
					setMessagePanelText("Your balance is too low to withdraw !!");
					enableMessagePanel(true);
				} else {
					String type = accountList.getSelectedItem().toString();
					AccountType accountType = null;
					if (type.equalsIgnoreCase("Saving")) {
						accountType = AccountType.SAVING;
					} else {
						accountType = AccountType.CHECKING;
					}

					currentAccount.setAccountType(accountType);
					controller.withdraw(currentAccount, new Float(amountBox.getText()));
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
		accountList.setSelectedIndex(0);
	}

	public void setMessagePanelText(String text) {
		message.setText("<html><font color='red'>" + text + "</font></html>");
	}

	public void enableMessagePanel(boolean enable) {
		message.setVisible(enable);
	}

	public void refreshUI() {
		accountUserList.setSelectedIndex(accountUserList.getSelectedIndex());
		accountList.setSelectedIndex(accountList.getSelectedIndex());
	}
}
