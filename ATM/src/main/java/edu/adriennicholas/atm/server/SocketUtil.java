package edu.adriennicholas.atm.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import edu.adriennicholas.atm.shared.model.TransactionObject;

public class SocketUtil {

	String hostName = "afsaccess2.njit.edu";
	int portNumber = 28189;
	private static Socket echoSocket = null;
	private static ObjectOutputStream outputStream = null;
	private static ObjectInputStream inputStream = null;

	public TransactionObject sendTransaction(TransactionObject transactionObject) {
		TransactionObject response = null;
		try {
			if (echoSocket == null) {
				echoSocket = new Socket();
				SocketAddress endpoint = new InetSocketAddress(hostName, portNumber);
				echoSocket.connect(endpoint, 1000 * 5);
				outputStream = new ObjectOutputStream(echoSocket.getOutputStream());
				inputStream = new ObjectInputStream(echoSocket.getInputStream());
			}

			System.out.println(transactionObject.getId());
			outputStream.writeObject(transactionObject);

			if ((response = (TransactionObject) inputStream.readObject()) != null) {
				System.out.println("Received:=" + response.getMessage());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

}
