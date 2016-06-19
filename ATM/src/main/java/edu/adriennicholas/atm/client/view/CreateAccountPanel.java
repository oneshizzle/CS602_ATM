package edu.adriennicholas.atm.client.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import edu.adriennicholas.atm.client.controller.CreateAccountPanelController;
import edu.adriennicholas.atm.shared.model.Account;
import edu.adriennicholas.atm.shared.model.Account.ActionType;
import edu.adriennicholas.atm.shared.model.User;
import edu.adriennicholas.atm.shared.model.User.UserRole;
import edu.adriennicholas.atm.util.Utils;

public class CreateAccountPanel extends JPanel {

	private JButton submit;
	private JPanel panel;
	private JTextField checkingAmountBox;
	private JTextField savingAmountBox;
	private JTextField userName;
	private JTextField password = new JPasswordField(15);
	private JLabel message = new JLabel();

	private String[] userTypes = { "ADMIN", "CLIENT" };
	private JComboBox<String> userTypesList = new JComboBox<String>(userTypes);
	private CreateAccountPanelController controller = new CreateAccountPanelController(this);

	JButton clearBtn;

	private final static int xOffSet = 55;
	private final static int yOffSet = 60;

	public CreateAccountPanel() {
		super();
		submit = new JButton("Create");
		clearBtn = new JButton("Clear?");

		panel = new JPanel();
		checkingAmountBox = Utils.createNumericTextField();
		savingAmountBox = Utils.createNumericTextField();
		userName = Utils.createUppercaseTextField(15);

		JLabel heading = new JLabel("Please enter the account details: ");
		JLabel checkingBalanceLabel = new JLabel("Init. Checking");
		JLabel savingBalanceLabel = new JLabel("Init. Saving");
		JLabel accountUser = new JLabel("Account User");
		JLabel accountPass = new JLabel("Account Pass");
		JLabel accountType = new JLabel("Account Type");

		setLayout(null);

		heading.setBounds(xOffSet, yOffSet - 43, 230, 20);
		heading.setHorizontalAlignment(SwingConstants.LEFT);

		accountType.setBounds(xOffSet, yOffSet + 10, 80, 20);
		userTypesList.setBounds(xOffSet + 90, yOffSet + 10, 154, 20);

		checkingBalanceLabel.setBounds(xOffSet, yOffSet + 40, 80, 20);
		checkingAmountBox.setBounds(xOffSet + 90, yOffSet + 40, 154, 20);
		checkingAmountBox.setText("0");
		savingBalanceLabel.setBounds(xOffSet, yOffSet + 70, 80, 20);
		savingAmountBox.setBounds(xOffSet + 90, yOffSet + 70, 154, 20);
		savingAmountBox.setText("0");

		accountUser.setBounds(xOffSet, yOffSet + 100, 154, 20);
		userName.setBounds(xOffSet + 90, yOffSet + 100, 154, 20);

		accountPass.setBounds(xOffSet, yOffSet + 130, 154, 20);
		password.setBounds(xOffSet + 90, yOffSet + 130, 154, 20);

		submit.setBounds(xOffSet, yOffSet + 168, 90, 20);
		clearBtn.setBounds(xOffSet + 140, yOffSet + 168, 100, 20);

		checkingAmountBox.setHorizontalAlignment(SwingConstants.RIGHT);
		savingAmountBox.setHorizontalAlignment(SwingConstants.RIGHT);

		message.setBounds(xOffSet, yOffSet - 10, 200, 20);
		message.setHorizontalAlignment(SwingConstants.LEFT);
		message.setVisible(false);

		panel.add(submit);
		panel.add(message);
		panel.add(savingAmountBox);
		panel.add(checkingAmountBox);
		panel.add(userTypesList);
		panel.add(clearBtn);
		panel.add(checkingBalanceLabel);
		panel.add(savingBalanceLabel);
		panel.add(heading);
		panel.add(userName);
		panel.add(accountPass);
		panel.add(password);
		panel.add(accountType);
		panel.add(accountUser);

		panel.setSize(500, 500);
		panel.setLayout(null);
		add(panel);
		setVisible(true);

		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				UserRole userRole = null;
				if (userTypesList.getSelectedItem().toString().contains("ADMIN")) {
					userRole = UserRole.ADMIN;
				} else {
					userRole = UserRole.USER;
				}

				if (userName.getText() == null || userName.getText().length() < 1 || password.getText() == null
						|| password.getText().length() < 1 || savingAmountBox.getText() == null
						|| savingAmountBox.getText().length() < 1 || checkingAmountBox.getText() == null
						|| checkingAmountBox.getText().length() < 1) {
					setMessagePanelText("Please enter all details");
					enableMessagePanel(true);
				} else {
					User user = new User(userRole, userName.getText(), true);
					user.setPassword(password.getText());
					Float saving = new Float(savingAmountBox.getText());
					Float checking = new Float(checkingAmountBox.getText());

					Account account = new Account(user, saving, checking, ActionType.CREATE);
					controller.createAccount(account);
					setMessagePanelText("User: " + user.getUserName() + " added.");
					clear();
					enableMessagePanel(true);
				}
			}
		});

		clearBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				savingAmountBox.setText("");
				checkingAmountBox.setText("");
				userName.setText("");
				password.setText("");
			}
		});
	}

	private void clear() {
		savingAmountBox.setText("");
		checkingAmountBox.setText("");
		userName.setText("");
		password.setText("");
	}

	public void setMessagePanelText(String text) {
		message.setText("<html><font color='red'>" + text + "</font></html>");
	}

	public void enableMessagePanel(boolean enable) {
		message.setVisible(enable);
	}
}
