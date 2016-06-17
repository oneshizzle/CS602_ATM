package edu.adriennicholas.atm.client.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class CreateAccountPanel extends JPanel {

	private JButton submit;
	private JPanel panel;
	private JTextField amountBox;
	private JTextField userName;

	private String[] userTypes = { "ADMIN", "CLIENT" };
	private JComboBox<String> userTypesList = new JComboBox<String>(userTypes);
	private JLabel accountUser = new JLabel("Account User");
	JButton clearBtn;

	private final static int xOffSet = 55;
	private final static int yOffSet = 80;

	public CreateAccountPanel() {
		super();
		submit = new JButton("Create");
		clearBtn = new JButton("Clear?");
		
		panel = new JPanel();
		amountBox = new JTextField(15);
		userName = new JTextField(15);
		

		JLabel heading = new JLabel("Please enter the account details: ");
		JLabel amountLabel = new JLabel("Initial Balance");
		JLabel accountType = new JLabel("Account Type");

		setLayout(null);

		heading.setBounds(xOffSet, yOffSet - 63, 230, 20);
		heading.setHorizontalAlignment(SwingConstants.LEFT);
		
		accountUser.setBounds(xOffSet, yOffSet + 70, 154, 20);
		userName.setBounds(xOffSet + 90, yOffSet + 70, 154, 20);
		
		amountLabel.setBounds(xOffSet, yOffSet + 40, 80, 20);
		amountBox.setBounds(xOffSet + 90, yOffSet + 40, 154, 20);
		
		accountType.setBounds(xOffSet, yOffSet + 10, 80, 20);
		userTypesList.setBounds(xOffSet + 90, yOffSet + 10, 154, 20);
		
		submit.setBounds(xOffSet, yOffSet + 168, 90, 20);
		clearBtn.setBounds(xOffSet + 140, yOffSet + 168, 100, 20);

		panel.add(submit);
		panel.add(amountBox);
		panel.add(userTypesList);
		panel.add(clearBtn);
		panel.add(amountLabel);
		panel.add(heading);
		panel.add(userName);
		panel.add(accountType);
		panel.add(accountUser);

		panel.setSize(500, 500);
		panel.setLayout(null);
		add(panel);
		setVisible(true);

		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// controller.attemptLogin();
			}

		});
	}

}
