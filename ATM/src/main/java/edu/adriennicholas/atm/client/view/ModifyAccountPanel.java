package edu.adriennicholas.atm.client.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import edu.adriennicholas.atm.client.controller.ModifyAccountPanelController;

public class ModifyAccountPanel extends JPanel {

	private JButton submit;
	private JPanel panel;
	private JComboBox<String> accountUserList = new JComboBox<String>();
	private JLabel accountType = new JLabel("Account Type");
	private JLabel accountUser = new JLabel("Account User");

	private JRadioButton open;
	private JRadioButton frozen;
	private JLabel message = new JLabel();
	private final static int xOffSet = 55;
	private final static int yOffSet = 80;
	private boolean isDeletePanel = false;
	private ButtonGroup group = new ButtonGroup();

	private ModifyAccountPanelController controller = new ModifyAccountPanelController(this);

	public ModifyAccountPanel(Boolean delete) {
		super();

		isDeletePanel = delete;
		if (delete) {
			submit = new JButton("Delete");
		} else {
			submit = new JButton("Freeze");
		}

		panel = new JPanel();
		JLabel heading = new JLabel("Please enter the account details: ");

		setLayout(null);

		heading.setBounds(xOffSet, yOffSet - 63, 230, 20);
		heading.setHorizontalAlignment(SwingConstants.LEFT);

		accountUser.setBounds(xOffSet, yOffSet + 60, 154, 20);
		accountUserList.setBounds(xOffSet + 90, yOffSet + 60, 154, 20);

		message.setBounds(xOffSet, yOffSet + 198, 250, 20);
		message.setHorizontalAlignment(SwingConstants.LEFT);
		message.setVisible(false);

		open = new JRadioButton("OPEN");
		open.setSelected(true);
		frozen = new JRadioButton("FROZEN");

		group.add(open);
		group.add(frozen);

		accountType.setBounds(xOffSet, yOffSet + 10, 154, 20);
		open.setBounds(xOffSet + 90, yOffSet + 10, 150, 20);
		frozen.setBounds(xOffSet + 90, yOffSet + 30, 150, 20);

		panel.add(accountType);
		panel.add(open);
		panel.add(frozen);
		group.clearSelection();

		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (((JRadioButton) arg0.getSource()).isSelected()) {
					if (!isDeletePanel) {
						submit.setText("Freeze");
					}
					enableMessagePanel(false);
					List<String> usernames = new ArrayList<String>();
					for (String user : controller.getUserService().findActiveAccountUsers()) {
						usernames.add(user);
					}
					setAccountUserList(usernames);
				}
			}
		});

		frozen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (((JRadioButton) arg0.getSource()).isSelected()) {
					if (!isDeletePanel) {
						submit.setText("Unfreeze");
					}
					enableMessagePanel(false);
					List<String> usernames = new ArrayList<String>();
					for (String user : controller.getUserService().findFrozenAccountUsers()) {
						usernames.add(user);
					}
					setAccountUserList(usernames);
				}
			}
		});

		submit.setBounds(xOffSet, yOffSet + 168, 90, 20);

		panel.add(submit);
		panel.add(message);
		panel.add(heading);
		panel.add(accountUser);
		panel.add(accountUserList);
		panel.setSize(500, 500);
		panel.setLayout(null);
		add(panel);
		setVisible(true);

		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = accountUserList.getSelectedItem().toString();
				enableMessagePanel(true);
				if (!isDeletePanel) {
					if (open.isSelected()) {
						controller.freezeUser(username);
						frozen.setSelected(true);
						setMessagePanelText(username + "'s account is frozen.");
					} else if (frozen.isSelected()) {
						controller.reactiveUser(username);
						open.setSelected(true);
						setMessagePanelText(username + "'s account is reactivated.");
					}
				} else {
					controller.deleteUser(username);
					setMessagePanelText(username + "'s account is deleted.");
				}
				group.clearSelection();
			}
		});
	}

	public void setAccountUserList(List<String> accountUsers) {
		accountUserList.setEditable(false);
		accountUserList.removeAllItems();
		for (String accountUser : accountUsers) {
			accountUserList.addItem(accountUser);
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
