package edu.adriennicholas.atm;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Panel;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import edu.adriennicholas.atm.client.controller.ATMMainController;
import edu.adriennicholas.atm.client.view.LoginPanel;
import edu.adriennicholas.atm.client.view.OptionsPanel;

public class ATMMainWindow {

	private JFrame frame;
	private JPanel cards = new JPanel(new CardLayout());
	private ATMMainController controller = new ATMMainController(this);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		ATMMainWindow window = new ATMMainWindow();
		window.frame.setVisible(true);
		window.showLoginPanel();
	}

	/**
	 * Create the application.
	 */
	public ATMMainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width / 2 - 300, dim.height / 2 - 300);

		frame.setSize(600, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create the panel that contains the "cards".
		cards.add(new Panel());
		cards.add(new LoginPanel(), "LOGIN_PANEL");
		cards.add(new OptionsPanel(), "MAIN_PANEL");

		frame.add(cards, BorderLayout.CENTER);
		frame.setResizable(false);
	}

	public void showLoginPanel() {
		CardLayout cl = (CardLayout) (cards.getLayout());
		cl.show(cards, "LOGIN_PANEL");
	}

	public void showMainPanel() {
		CardLayout cl = (CardLayout) (cards.getLayout());
		cl.show(cards, "MAIN_PANEL");
	}
}
