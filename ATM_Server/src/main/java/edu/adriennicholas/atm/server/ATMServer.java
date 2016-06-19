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

import oracle.jdbc.driver.OracleDriver;
import edu.adriennicholas.atm.shared.model.TransactionObject;

public class ATMServer {
	public static void main(String[] arg) {

		try {
			ServerSocket myServerSocket = new ServerSocket(28189);
			Socket incoming = myServerSocket.accept();
			ObjectOutputStream myOutputStream = new ObjectOutputStream(incoming.getOutputStream());
			ObjectInputStream myInputStream = new ObjectInputStream(incoming.getInputStream());

			TransactionObject request = null;
			TransactionObject response = null;
			while ((request = (TransactionObject) myInputStream.readObject()) != null) {
				System.out.println("Message received : " + request.getId());
				switch (request.getId()) {
				case "FETCH_USERS":
					response = fetchUsers();
					break;
				case "BALANCE":
					response = fetchAccount(request, true);
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

	private static Connection connection = null;

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
		if (connection == null)
			connection = DriverManager.getConnection(url, ucid, dbpassword);

		return connection;
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
					stmt.executeUpdate("UPDATE ATM_TRANSACTION SET CHECKING_BALANCE=" + amount + " WHERE USERNAME='" + username + "')");

				} else {
					stmt.executeUpdate("UPDATE ATM_TRANSACTION SET SAVING_BALANCE=" + amount + " WHERE USERNAME='" + username + "')");
				}

				System.out.println("Update balance.");

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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
					rs = stmt.executeQuery("SELECT * from ATM_TRANSACTION WHERE PASSWORD='" + password + "' AND USERNAME='" + username + "'");
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

	private static TransactionObject fetchUsers() {
		TransactionObject transactionObject = new TransactionObject();

		try {
			Connection conn = createConnection();
			Statement stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT USERNAME from ATM_TRANSACTION ");
			transactionObject.setMessage("");

			while (rs.next()) {
				String username = rs.getString("USERNAME");
				System.out.println("USERNAME: " + username);
				transactionObject.setMessage(transactionObject.getMessage() + "," + username);
			}

			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return transactionObject;
	}

	private void freezeAccount(TransactionObject transactionObject) {
		if (transactionObject != null) {
			String username = transactionObject.getName();

			try {
				Connection conn = createConnection();
				Statement stmt = conn.createStatement();

				stmt.executeUpdate("UPDATE ATM_TRANSACTION SET STATUS='FROZEN' WHERE USERNAME='" + username + "')");

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

			stmt.executeUpdate("INSERT INTO ATM_TRANSACTION " + "VALUES(seq.NEXTVAL" + "," + transactionObject.getAmount() + ","
					+ transactionObject.getAmount() + "," + transactionObject.getName() + "','" + transactionObject.getNum() + "','"
					+ transactionObject.getMessage() + "','" + transactionObject.getType() + "')");

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
