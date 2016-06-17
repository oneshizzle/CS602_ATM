package edu.adriennicholas.atm.client.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import edu.adriennicholas.atm.client.controller.ModifyAccountPanelController;

public class ModifyAccountPanel extends JPanel {

	private JButton submit;
	private JButton clearBtn;
	private JPanel panel;
	private JComboBox<String> accountUserList = new JComboBox<String>();
	private JLabel accountUser = new JLabel("Account User");

	private final static int xOffSet = 55;
	private final static int yOffSet = 80;

	private ModifyAccountPanelController controller = new ModifyAccountPanelController(this);

	public ModifyAccountPanel(Boolean delete) {
		super();

		if (delete) {
			submit = new JButton("Delete");
		} else {
			submit = new JButton("Freeze");
		}

		clearBtn = new JButton("Clear?");

		panel = new JPanel();
		JLabel heading = new JLabel("Please enter the account details: ");

		setLayout(null);

		heading.setBounds(xOffSet, yOffSet - 63, 230, 20);
		heading.setHorizontalAlignment(SwingConstants.LEFT);

		accountUser.setBounds(xOffSet, yOffSet + 10, 154, 20);
		accountUserList.setBounds(xOffSet + 90, yOffSet + 10, 154, 20);

		submit.setBounds(xOffSet, yOffSet + 168, 90, 20);
		clearBtn.setBounds(xOffSet + 140, yOffSet + 168, 100, 20);

		panel.add(submit);

		panel.add(clearBtn);
		panel.add(heading);

		panel.add(accountUser);
		panel.add(accountUserList);

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
