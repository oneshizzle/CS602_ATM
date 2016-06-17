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

import edu.adriennicholas.atm.client.controller.DepositPanelController;
import edu.adriennicholas.atm.shared.model.Account;
import edu.adriennicholas.atm.shared.model.Account.AccountType;
import edu.adriennicholas.atm.shared.model.Account.ActionType;
import edu.adriennicholas.atm.util.UserSession;
import edu.adriennicholas.atm.util.Utils;

public class DepositPanel extends JPanel {

	private DepositPanelController controller = new DepositPanelController(this);

	JButton submit;
	JPanel panel;
	JTextField amountBox;

	String[] accountTypes = { "Checking", "Saving" };
	private JComboBox<String> accountList = new JComboBox<String>(accountTypes);
	private JLabel accountUser = new JLabel("Account User");
	private JComboBox<String> accountUserList = new JComboBox<String>();
	private JButton clearBtn;
	private JLabel message = new JLabel();

	private final static int xOffSet = 55;
	private final static int yOffSet = 80;

	public DepositPanel() {
		super();
		submit = new JButton("Deposit");
		panel = new JPanel();
		amountBox = Utils.createNumericTextField();
		clearBtn = new JButton("Clear?");

		JLabel heading = new JLabel("Please enter the deposit details: ");
		JLabel amount = new JLabel("Amount");
		JLabel accountType = new JLabel("Account Type");

		setLayout(null);

		heading.setBounds(xOffSet, yOffSet - 73, 200, 20);
		heading.setHorizontalAlignment(SwingConstants.LEFT);

		accountUser.setBounds(xOffSet, yOffSet + 10, 154, 20);
		accountUserList.setBounds(xOffSet + 90, yOffSet + 10, 154, 20);

		amountBox.setBounds(xOffSet + 90, yOffSet + 40, 154, 20);
		amount.setBounds(xOffSet, yOffSet + 40, 80, 20);

		accountList.setBounds(xOffSet + 90, yOffSet + 70, 154, 20);
		accountType.setBounds(xOffSet, yOffSet + 70, 80, 20);

		submit.setBounds(xOffSet, yOffSet + 168, 80, 20);
		clearBtn.setBounds(xOffSet + 140, yOffSet + 168, 100, 20);

		message.setBounds(xOffSet - 10, yOffSet - 20, 200, 20);
		message.setHorizontalAlignment(SwingConstants.LEFT);
		message.setVisible(false);

		panel.add(submit);
		panel.add(amountBox);
		panel.add(accountList);
		panel.add(clearBtn);
		panel.add(amount);
		panel.add(message);
		panel.add(heading);
		panel.add(accountType);
		panel.add(accountUser);
		panel.add(accountUserList);

		panel.setSize(500, 500);
		panel.setLayout(null);
		add(panel);
		setVisible(true);

		accountUserList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = (String) ((JComboBox<String>) e.getSource()).getSelectedItem();
				controller.getBalances(username);
			}

		});

		clearBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clear();
			}

		});

		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enableMessagePanel(false);
				if (amountBox.getText() == null || amountBox.getText().length() < 1) {
					setMessagePanelText("Please enter the dollar amount");
					enableMessagePanel(true);
				} else {
					String type = accountList.getSelectedItem().toString();
					AccountType accountType = null;
					if (type.equalsIgnoreCase("Saving")) {
						accountType = AccountType.SAVING;

					} else {
						accountType = AccountType.CHECKING;
					}

					Account account = controller.getBalances(controller.getUserSession().getCurrentUser().getUserName());
					account.setAccountType(accountType);

					controller.deposit(account, new Float(amount.getText()));
				}

			}

		});
	}

	public void clear() {
		enableMessagePanel(false);
		amountBox.setText("");
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

	public void setMessagePanelText(String text) {
		message.setText("<html><font color='red'>" + text + "</font></html>");
		message.setHorizontalAlignment(SwingConstants.LEFT);
	}

	public void enableMessagePanel(boolean enable) {
		message.setVisible(enable);
	}
}
