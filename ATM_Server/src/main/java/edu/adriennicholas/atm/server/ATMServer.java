package edu.adriennicholas.atm.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

public class ATMServer {
	public static void main(String[] arg) {

		TransactionObject myObject = null;

		try {
			ServerSocket myServerSocket = new ServerSocket(28189);

			Socket incoming = myServerSocket.accept();

			ObjectOutputStream myOutputStream = new ObjectOutputStream(
					incoming.getOutputStream());

			ObjectInputStream myInputStream = new ObjectInputStream(
					incoming.getInputStream());

			myObject = (TransactionObject) myInputStream.readObject();

			System.out.println("Message received : " + myObject.getMessage());

			switch (myObject.getId()) {
			case "BALANCE":
				break;
			case "CREATE":
				break;
			case "DELETE":
				break;
			case "DEPOSIT":
				break;
			case "FREEZE":
				break;
			case "TRANSFER":
				break;
			case "WITHDRAW":
				break;
			case "LOGIN":
				break;
			}
			// myObject.setMessage("Got it!");

			System.out.println("Message sent : " + myObject.getMessage());

			myOutputStream.writeObject(myObject);

			myOutputStream.close();

			myInputStream.close();

			myServerSocket.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private Connection createConnection() throws SQLException {
		String url = "jdbc:oracle:thin:@prophet.njit.edu:1521:course";
		String ucid = "an395"; // your ucid
		String dbpassword = "oxVmnKjQD"; // your Oracle password
		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		} catch (Exception e) {
			System.err.println("Unable to load driver.");
			e.printStackTrace();
		}
		System.out.println("Driver loaded.");
		Connection conn = DriverManager.getConnection(url, ucid, dbpassword);

		return conn;
	}

	private void updateAccountBalance(TransactionObject transactionObject) {
		if (transactionObject != null) {

			try {
				Connection conn = createConnection();
				Statement stmt = conn.createStatement();

				Float amount = transactionObject.getAmount();
				String accountType = transactionObject.getType();
				String username = transactionObject.getName();

				if (accountType.equalsIgnoreCase("CHECKING")) {
					stmt.executeUpdate("UPDATE ATM_TRANSACTION SET CHECKING_BALANCE="
							+ amount + " WHERE USERNAME='" + username + "')");

				} else {
					stmt.executeUpdate("UPDATE ATM_TRANSACTION SET SAVING_BALANCE="
							+ amount + " WHERE USERNAME='" + username + "')");
				}

				System.out.println("Update balance.");

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private TransactionObject fetchAccount(
			TransactionObject transactionObject) {
		if (transactionObject != null) {

			try {
				Connection conn = createConnection();
				Statement stmt = conn.createStatement();
				String username = transactionObject.getName();

				ResultSet rs = stmt
						.executeQuery("SELECT * from ATM_TRANSACTION WHERE USERNAME='"
								+ username + "'");

				String balance = "";

				while (rs.next()) {
					String savingBalance = (new Float(
							rs.getFloat("SAVING_BALANCE"))).toString();
					String checkingBalance = (new Float(
							rs.getFloat("CHECKING_BALANCE"))).toString();
					transactionObject.setName(rs.getString("USERNAME"));
					balance = "saving=" + savingBalance + "checking="
							+ checkingBalance;
				}

				transactionObject.setMessage(balance);
				rs.close();
				System.out.println("Update balance.");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return transactionObject;
	}

	private void freezeAccount(TransactionObject transactionObject) {
		if (transactionObject != null) {
			String username = transactionObject.getName();

			try {
				Connection conn = createConnection();
				Statement stmt = conn.createStatement();

				stmt.executeUpdate("UPDATE ATM_TRANSACTION SET STATUS='FROZEN' WHERE USERNAME='"
						+ username + "')");

				System.out.println("Frozen record.");

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void createAccount(TransactionObject transactionObject) {

		try {
			Connection conn = createConnection();
			Statement stmt = conn.createStatement();

			stmt.executeUpdate("INSERT INTO ATM_TRANSACTION "
					+ "VALUES(seq.NEXTVAL" + ",'"
					+ transactionObject.getAmount() + ","
					+ transactionObject.getAmount() + ","
					+ transactionObject.getName() + "','"
					+ transactionObject.getNum() + "','"
					+ transactionObject.getMessage() + "','"
					+ transactionObject.getType() + ")");

			System.out.println("Inserted data.");

			stmt.close();
			conn.close();
		} catch (SQLException E) {
			System.out.println("SQLException: " + E.getMessage());
			System.out.println("SQLState:     " + E.getSQLState());
			System.out.println("VendorError:  " + E.getErrorCode());
		}
	}
}
