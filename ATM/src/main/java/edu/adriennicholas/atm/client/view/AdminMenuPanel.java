package edu.adriennicholas.atm.client.view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import edu.adriennicholas.atm.client.controller.AdminMenuPanelController;

public class AdminMenuPanel extends JPanel {

	private AdminMenuPanelController controller = new AdminMenuPanelController(this);

	JButton createAccount = new JButton("Create Account");
	JButton freezeAccount = new JButton("Modify Account");
	JButton deleteAccount = new JButton("Delete Account");

	public AdminMenuPanel() {
		super();
		GridLayout gridLayout = new GridLayout(0, 1);
		setLayout(gridLayout);

		setPreferredSize(new Dimension(135, 10));
		Border paddingBorder = new EmptyBorder(0, 0, 0, 10);
		setBorder(paddingBorder);

		add(createAccount);
		add(freezeAccount);
		add(deleteAccount);

		gridLayout.setVgap(Integer.parseInt("10"));
		addButtonListeners();
	}

	public void enablePanel(boolean enable) {
		this.setEnabled(enable);
		this.setVisible(enable);
	}

	private void addButtonListeners() {
		createAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.showCreateAccountPanel();
			}
		});
		freezeAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.showFreezeAccountPanel();
				;
			}
		});
		deleteAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.showDeleteAccountPanel();
				;
			}
		});
	}

}
