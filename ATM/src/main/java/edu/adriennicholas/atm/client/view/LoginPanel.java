package edu.adriennicholas.atm.client.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import edu.adriennicholas.atm.client.controller.LoginPanelController;
import edu.adriennicholas.atm.util.Utils;

public class LoginPanel extends JPanel {

	private LoginPanelController controller = new LoginPanelController(this);

	JButton blogin;
	JPanel loginpanel;
	JTextField txuser;
	JTextField pass;
	JButton newUser;
	JLabel username;
	JLabel pin;
	JLabel message;

	private final static int xOffSet = 165;
	private final static int yOffSet = 80;

	public LoginPanel() {
		super();
		blogin = new JButton("Login");
		loginpanel = new JPanel();
		txuser = Utils.createUppercaseTextField(15);
		pass = new JPasswordField(15);
		newUser = new JButton("New User?");
		username = new JLabel("Username");
		pin = new JLabel("Pin");
		message = new JLabel();

		setLayout(null);

		txuser.setBounds(xOffSet + 74, yOffSet + 28, 154, 20);
		pass.setBounds(xOffSet + 74, yOffSet + 65, 154, 20);
		blogin.setBounds(xOffSet, yOffSet + 112, 80, 20);
		username.setBounds(xOffSet, yOffSet + 28, 80, 20);
		pin.setBounds(xOffSet, yOffSet + 63, 80, 20);
		message.setBounds(xOffSet, yOffSet, 200, 20);
		message.setVisible(false);

		loginpanel.add(blogin);
		loginpanel.add(txuser);
		loginpanel.add(pass);
		loginpanel.add(newUser);
		loginpanel.add(username);
		loginpanel.add(pin);
		loginpanel.add(message);
		loginpanel.setSize(500, 500);
		loginpanel.setLayout(null);
		add(loginpanel);
		setVisible(true);

		blogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.attemptLogin(txuser.getText(), pass.getText());
			}

		});

		pass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.attemptLogin(txuser.getText(), pass.getText());
			}

		});
	}

	public void setMessagePanelText(String text) {
		message.setText("<html><font color='red'>" + text + "</font></html>");
		message.setHorizontalAlignment(SwingConstants.CENTER);
	}

	public void enableMessagePanel(boolean enable) {
		message.setVisible(enable);
	}
}
