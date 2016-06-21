package edu.adriennicholas.atm.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import oracle.jdbc.driver.OracleDriver;
import edu.adriennicholas.atm.shared.model.TransactionObject;

public class ThreadedDataObjectHandler extends Thread {
	private Socket incoming;
	private static Connection connection = null;

	public ThreadedDataObjectHandler(Socket i) {
		incoming = i;
	}

	public void run() {
		try {
			ObjectOutputStream myOutputStream = new ObjectOutputStream(incoming.getOutputStream());
			ObjectInputStream myInputStream = new ObjectInputStream(incoming.getInputStream());

			TransactionObject request = null;
			TransactionObject response = null;
			while ((request = (TransactionObject) myInputStream.readObject()) != null) {
				System.out.println("Message received : " + request.getId());
				switch (request.getId()) {
				case "FETCH_ACTIVE_USERS":
					response = fetchUsers(true);
					break;
				case "FETCH_FROZEN_USERS":
					response = fetchUsers(false);
					break;
				case "BALANCE":
					response = fetchAccount(request, true);
					break;
				case "CREATE":
					createAccount(request);
					break;
				case "DELETE":
					deleteAccount(request);
					break;
				case "DEPOSIT":
					updateBalanceAccount(request);
					break;
				case "REACTIVATE":
					reActivateAccount(request);
					break;
				case "FREEZE":
					freezeAccount(request);
					break;
				case "TRANSFER":
					updateBalanceAccount(request);
					break;
				case "WITHDRAW":
					updateBalanceAccount(request);
					break;
				case "LOGIN":
					response = fetchAccount(request, false);
					break;
				}
				System.out.println(response.getId());
				myOutputStream.writeObject(response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static Connection createConnection() throws SQLException {
		String url = "jdbc:oracle:thin:@prophet.njit.edu:1521:course";
		String ucid = "an395"; // your ucid
		String dbpassword = "oxVmnKjQD"; // your Oracle password
		try {
			DriverManager.registerDriver(new OracleDriver());
		} catch (Exception e) {
			System.err.println("Unable to load driver.");
			e.printStackTrace();
		}
		System.out.println("Driver loaded.");
		if (connection == null || connection.isClosed())
			connection = DriverManager.getConnection(url, ucid, dbpassword);

		return connection;
	}

	private static TransactionObject fetchAccount(TransactionObject transactionObject, boolean authorized) {
		if (transactionObject != null) {

			try {
				Connection conn = createConnection();
				Statement stmt = conn.createStatement();
				String username = transactionObject.getName();
				String password = transactionObject.getNum();

				ResultSet rs = null;

				if (authorized) {
					rs = stmt.executeQuery("SELECT * from ATM_TRANSACTION WHERE USERNAME='" + username + "'");
				} else {
					rs = stmt.executeQuery("SELECT * from ATM_TRANSACTION WHERE PASSWORD='" + password
							+ "' AND USERNAME='" + username + "'");
				}

				String balance = "saving=0:checking=0";
				transactionObject.setName(null);

				while (rs.next()) {
					String savingBalance = (new Float(rs.getFloat("SAVING_BALANCE"))).toString();
					String checkingBalance = (new Float(rs.getFloat("CHECKING_BALANCE"))).toString();
					transactionObject.setName(rs.getString("USERNAME"));
					transactionObject.setNum(rs.getString("PASSWORD"));
					transactionObject.setId(rs.getString("USERROLE"));
					transactionObject.setType(rs.getString("STATUS"));
					balance = "saving=" + savingBalance + ":checking=" + checkingBalance;
				}

				transactionObject.setMessage(balance);
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return transactionObject;
	}

	private static TransactionObject fetchUsers(boolean active) {
		TransactionObject transactionObject = new TransactionObject();

		try {
			Connection conn = createConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = null;
			transactionObject.setMessage("");

			if (active) {
				rs = stmt.executeQuery("SELECT USERNAME from ATM_TRANSACTION WHERE STATUS='ACTIVE'");
			} else {
				rs = stmt.executeQuery("SELECT USERNAME from ATM_TRANSACTION WHERE STATUS!='ACTIVE'");
			}

			while (rs.next()) {
				String username = rs.getString("USERNAME");
				System.out.println("USERNAME: " + username);
				transactionObject.setMessage(transactionObject.getMessage() + "," + username);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return transactionObject;
	}

	private static void freezeAccount(TransactionObject transactionObject) {
		if (transactionObject != null) {
			String username = transactionObject.getName();

			try {
				Connection conn = createConnection();
				Statement stmt = conn.createStatement();
				stmt.executeUpdate("UPDATE ATM_TRANSACTION SET STATUS='FROZEN' WHERE USERNAME='" + username + "'");
				System.out.println("Frozen record.");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private static void reActivateAccount(TransactionObject transactionObject) {
		if (transactionObject != null) {
			String username = transactionObject.getName();

			try {
				Connection conn = createConnection();
				Statement stmt = conn.createStatement();
				stmt.executeUpdate("UPDATE ATM_TRANSACTION SET STATUS='ACTIVE' WHERE USERNAME='" + username + "'");
				System.out.println("RE-ACTIVATE ACCOUNT.");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private static void createAccount(TransactionObject transactionObject) {
		try {
			Connection conn = createConnection();
			Statement stmt = conn.createStatement();
			System.out.println("Message: " + transactionObject.getMessage());

			Float saving = new Float(transactionObject.getMessage().substring("saving=".length(),
					transactionObject.getMessage().indexOf(":")));
			Float checking = new Float(transactionObject.getMessage().substring(
					transactionObject.getMessage().indexOf(":") + "checking=".length() + 1,
					transactionObject.getMessage().length()));

			stmt.executeUpdate("INSERT INTO ATM_TRANSACTION " + "VALUES(seq.NEXTVAL" + "," + saving + "," + checking
					+ ",'" + transactionObject.getName() + "','" + transactionObject.getNum() + "','ACTIVE','"
					+ transactionObject.getType() + "')");

			System.out.println("Inserted data.");
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void deleteAccount(TransactionObject transactionObject) {
		try {
			Connection conn = createConnection();
			Statement stmt = conn.createStatement();
			String username = transactionObject.getName();
			stmt.executeUpdate("DELETE ATM_TRANSACTION WHERE USERNAME='" + username + "'");
			System.out.println("DELETED DATA");
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void updateBalanceAccount(TransactionObject transactionObject) {

		try {
			Connection conn = createConnection();
			Statement stmt = conn.createStatement();
			String username = transactionObject.getName();

			System.out.println("Message: " + transactionObject.getMessage());

			Float saving = new Float(transactionObject.getMessage().substring("saving=".length(),
					transactionObject.getMessage().indexOf(":")));
			Float checking = new Float(transactionObject.getMessage().substring(
					transactionObject.getMessage().indexOf(":") + "checking=".length() + 1,
					transactionObject.getMessage().length()));

			stmt.executeUpdate("UPDATE ATM_TRANSACTION SET SAVING_BALANCE=" + saving + ", CHECKING_BALANCE=" + checking
					+ " WHERE USERNAME='" + username + "'");

			System.out.println("Balance Updated");

			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
