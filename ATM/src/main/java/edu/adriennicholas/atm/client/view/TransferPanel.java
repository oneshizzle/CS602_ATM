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

import edu.adriennicholas.atm.client.controller.TransferPanelController;
import edu.adriennicholas.atm.util.Utils;

public class TransferPanel extends JPanel {

	private TransferPanelController controller = new TransferPanelController(this);

	private JButton submit;
	private JPanel panel;
	private JTextField amountBox;

	private String[] accountTypes = { "Checking", "Saving" };

	private JComboBox<String> fromAccountList = new JComboBox<String>(accountTypes);
	private JComboBox<String> toAccountList = new JComboBox<String>(accountTypes);
	private JComboBox<String> accountUserList = new JComboBox<String>();

	private JButton resetBtn;

	private final static int xOffSet = 55;
	private final static int yOffSet = 80;

	public TransferPanel() {
		super();
		submit = new JButton("Transfer");
		panel = new JPanel();
		amountBox = Utils.createNumericTextField();
		resetBtn = new JButton("Reset");

		JLabel accountUser = new JLabel("Account User");

		JLabel heading = new JLabel("Please enter the transfer details: ");
		JLabel balanceStatus = new JLabel("Balance: " + 100.01 + " (Checking) " + 75.01 + " (Saving)");
		JLabel amount = new JLabel("Amount");

		JLabel toAccountType = new JLabel("To:");
		JLabel fromAccountType = new JLabel("From:");

		setLayout(null);

		heading.setBounds(xOffSet, yOffSet - 63, 230, 20);
		heading.setHorizontalAlignment(SwingConstants.LEFT);
		balanceStatus.setBounds(xOffSet, yOffSet - 33, 280, 20);
		balanceStatus.setHorizontalAlignment(SwingConstants.LEFT);

		accountUser.setBounds(xOffSet, yOffSet + 18, 154, 20);
		accountUserList.setBounds(xOffSet + 90, yOffSet + 18, 154, 20);

		amount.setBounds(xOffSet, yOffSet + 48, 85, 20);
		amountBox.setBounds(xOffSet + 90, yOffSet + 48, 154, 20);

		fromAccountList.setBounds(xOffSet + 90, yOffSet + 78, 154, 20);
		fromAccountType.setBounds(xOffSet, yOffSet + 78, 80, 20);

		toAccountList.setBounds(xOffSet + 90, yOffSet + 108, 154, 20);
		toAccountType.setBounds(xOffSet, yOffSet + 108, 80, 20);

		submit.setBounds(xOffSet, yOffSet + 168, 90, 20);
		resetBtn.setBounds(xOffSet + 140, yOffSet + 168, 100, 20);

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

		panel.setSize(500, 500);
		panel.setLayout(null);
		add(panel);
		setVisible(true);

		fromAccountList.setSelectedIndex(0);
		toAccountList.setSelectedIndex(1);

		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// controller.attemptLogin();
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
}
