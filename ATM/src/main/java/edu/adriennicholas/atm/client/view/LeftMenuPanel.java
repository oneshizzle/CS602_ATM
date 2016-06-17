package edu.adriennicholas.atm.client.view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import edu.adriennicholas.atm.client.controller.LeftMenuPanelController;

public class LeftMenuPanel extends JPanel {

	private LeftMenuPanelController controller = new LeftMenuPanelController(
			this);

	JButton deposit = new JButton("Deposit");
	JButton withdraw = new JButton("Withdraw");
	JButton transfer = new JButton("Transfer");
	JButton status = new JButton("Balance");

	public LeftMenuPanel() {
		super();
		GridLayout gridLayout = new GridLayout(0, 1);
		setLayout(gridLayout);

		setPreferredSize(new Dimension(125, 10));

		add(deposit);
		add(withdraw);
		add(transfer);
		add(status);
		
		gridLayout.setVgap(Integer.parseInt("10"));
		gridLayout.setHgap(Integer.parseInt("10"));

		addButtonListeners();
	}

	public void enablePanel(boolean enable) {
		deposit.setEnabled(enable);
		withdraw.setEnabled(enable);
		transfer.setEnabled(enable);
		status.setEnabled(enable);
	}

	private void addButtonListeners() {
		deposit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.showDepositPanel();
			}
		});
		withdraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.showWithdrawPanel();
				;
			}
		});
		status.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.showBalancePanel();
				;
			}
		});
		transfer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.showTransferPanel();
			}
		});
	}

}
