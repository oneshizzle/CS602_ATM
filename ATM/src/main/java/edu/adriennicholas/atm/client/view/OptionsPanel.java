package edu.adriennicholas.atm.client.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import edu.adriennicholas.atm.client.controller.OptionsPanelController;
import edu.adriennicholas.atm.util.UserSession;

public class OptionsPanel extends JPanel {

	private OptionsPanelController controller = new OptionsPanelController(this);
	private JPanel cards = new JPanel(new CardLayout());
	private JLabel headerlabel = new JLabel();
	private final static int xOffSet = 50;
	private final static int yOffSet = 180;

	public OptionsPanel() {
		super();
		setLayout(new BorderLayout());
		JPanel header = new JPanel();
		header.setLayout(new BorderLayout());
		Border paddingBorder = new EmptyBorder(10, 10, 10, 10);
		header.setBorder(paddingBorder);

		headerlabel.setHorizontalTextPosition(JLabel.LEFT);
		header.add(headerlabel, BorderLayout.WEST);

		JButton logOffButton = new JButton("Log Off");
		logOffButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.logout();
			}
		});

		logOffButton.setPreferredSize(new Dimension(100, 40));
		header.add(logOffButton, BorderLayout.EAST);

		add(header, BorderLayout.NORTH);

		JPanel welcomePanel = new JPanel();
		welcomePanel.setLayout(null);
		JLabel heading = new JLabel("Please select an option");
		JLabel subheading = new JLabel("This financial institution is too small to bail");

		heading.setBounds(xOffSet, yOffSet - 55, 240, 20);
		heading.setHorizontalAlignment(SwingConstants.CENTER);
		subheading.setBounds(xOffSet, yOffSet - 15, 240, 20);
		subheading.setHorizontalAlignment(SwingConstants.CENTER);

		welcomePanel.add(heading);
		welcomePanel.add(subheading);

		// Create the panel that contains the "cards".
		cards.add(welcomePanel, "BLANK");
		cards.add(new BalancePanel(), "BALANCE");
		cards.add(new TransferPanel(), "TRANSFER");
		cards.add(new DepositPanel(), "DEPOSIT");
		cards.add(new WithdrawPanel(), "WITHDRAW");
		cards.add(new CreateAccountPanel(), "CREATE");
		cards.add(new ModifyAccountPanel(false), "FREEZE");
		cards.add(new ModifyAccountPanel(true), "DELETE");

		add(cards, BorderLayout.CENTER);

		add(new LeftMenuPanel(), BorderLayout.WEST);

		JLabel footer = new JLabel("Thank for you doing business here.");
		JPanel bottomPanel = new JPanel();
		bottomPanel.add(footer);
		bottomPanel.setLayout(new GridBagLayout());
		add(bottomPanel, BorderLayout.PAGE_END);

		add(new AdminMenuPanel(), BorderLayout.EAST);
	}

	public void showPanel(String panelName) {
		updateLoggedInUser();
		CardLayout cl = (CardLayout) (cards.getLayout());
		cl.show(cards, panelName);
	}

	public void updateLoggedInUser() {
		SimpleDateFormat formatter = new SimpleDateFormat("MMM-dd-yyyy");
		String formattedDate = formatter.format(Calendar.getInstance().getTime());
		if (UserSession.getInstance().getCurrentUser() != null) {
			headerlabel.setText(UserSession.getInstance().getCurrentUser().getUserName() + " is logged in as "
					+ UserSession.getInstance().getCurrentUser().getUserRole() + " on " + formattedDate);
		}
	}

}
