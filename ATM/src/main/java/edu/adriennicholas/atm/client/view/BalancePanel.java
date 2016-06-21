package edu.adriennicholas.atm.client.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import edu.adriennicholas.atm.client.controller.BalancePanelController;
import edu.adriennicholas.atm.shared.model.Account;
import edu.adriennicholas.atm.util.Utils;

public class BalancePanel extends JPanel {

	private BalancePanelController controller = new BalancePanelController(this);

	JPanel panel;
	private JTextField savingAmountBox;
	private JTextField checkingAmountBox;
	private JLabel checkingAmount = new JLabel("Checking Bal.");
	private JLabel savingAmount = new JLabel("Saving Bal.");

	private JLabel accountUser = new JLabel("Account User");
	private JComboBox<String> accountUserList = new JComboBox<String>();
	private JButton clearBtn;
	private JLabel message = new JLabel();

	private final static int xOffSet = 55;
	private final static int yOffSet = 80;

	public BalancePanel() {
		super();
		panel = new JPanel();
		savingAmountBox = Utils.createNumericTextField();
		checkingAmountBox = Utils.createNumericTextField();
		clearBtn = new JButton("Clear?");

		JLabel heading = new JLabel("Here is your balance information");

		setLayout(null);

		heading.setBounds(xOffSet, yOffSet - 55, 200, 20);
		heading.setHorizontalAlignment(SwingConstants.LEFT);

		accountUser.setBounds(xOffSet, yOffSet + 10, 154, 20);
		accountUserList.setBounds(xOffSet + 90, yOffSet + 10, 154, 20);

		savingAmountBox.setBounds(xOffSet + 90, yOffSet + 40, 154, 20);
		savingAmount.setBounds(xOffSet, yOffSet + 40, 80, 20);

		checkingAmountBox.setBounds(xOffSet + 90, yOffSet + 70, 154, 20);
		checkingAmount.setBounds(xOffSet, yOffSet + 70, 80, 20);

		message.setBounds(xOffSet - 10, yOffSet - 20, 200, 20);
		message.setHorizontalAlignment(SwingConstants.LEFT);
		message.setVisible(false);

		checkingAmountBox.setEditable(false);
		savingAmountBox.setEditable(false);

		panel.add(checkingAmountBox);
		panel.add(checkingAmount);
		panel.add(savingAmountBox);
		panel.add(savingAmount);
		panel.add(clearBtn);
		panel.add(message);
		panel.add(heading);
		panel.add(accountUser);
		panel.add(accountUserList);

		accountUserList.setBackground(Color.WHITE);

		checkingAmountBox.setHorizontalAlignment(SwingConstants.RIGHT);
		savingAmountBox.setHorizontalAlignment(SwingConstants.RIGHT);

		panel.setSize(500, 500);
		panel.setLayout(null);
		add(panel);
		setVisible(true);

		accountUserList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = (String) ((JComboBox<String>) e.getSource()).getSelectedItem();
				Account balance = controller.fetchBalance(username);
				checkingAmountBox.setText(NumberFormat.getCurrencyInstance().format(balance.getCheckingBalance()));
				savingAmountBox.setText(NumberFormat.getCurrencyInstance().format(balance.getSavingBalance()));
			}
		});
	}

	public void clear() {
		enableMessagePanel(false);
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

	public void refreshUI() {
		accountUserList.setSelectedIndex(accountUserList.getSelectedIndex());
	}

}
