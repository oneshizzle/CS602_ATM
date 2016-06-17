package edu.adriennicholas.atm;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
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
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ATMMainWindow window = new ATMMainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
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
		frame.setLocation(dim.width / 2 - frame.getSize().width / 2, 100);

		frame.setSize(600, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel comboBoxPane = new JPanel(); // use FlowLayout
		String comboBoxItems[] = { "BUTTONPANEL", "TEXTPANEL" };
		JComboBox cb = new JComboBox(comboBoxItems);
		cb.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent evt) {
				CardLayout cl = (CardLayout) (cards.getLayout());
				cl.show(cards, (String) evt.getItem());
			}
		});

		cb.setEditable(false);
		comboBoxPane.add(cb);

		// Create the "cards".
		JPanel card1 = new JPanel();
		card1.add(new JButton("Button 1"));
		card1.add(new JButton("Button 2"));
		card1.add(new JButton("Button 3"));

		// Create the panel that contains the "cards".
		cards.add(new LoginPanel(), "BUTTONPANEL");
		cards.add(new OptionsPanel(), "TEXTPANEL");

		frame.add(comboBoxPane, BorderLayout.PAGE_START);
		frame.add(cards, BorderLayout.CENTER);
		frame.setResizable(false);
	}

	public void showLoginPanel() {
		CardLayout cl = (CardLayout) (cards.getLayout());
		cl.show(cards, "BUTTONPANEL");
	}

	public void showMainPanel() {
		CardLayout cl = (CardLayout) (cards.getLayout());
		cl.show(cards, "TEXTPANEL");
	}
}
