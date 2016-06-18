package edu.adriennicholas.atm.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import edu.adriennicholas.atm.shared.model.TransactionObject;

public class SocketUtil {

	String hostName = "afsaccess2.njit.edu";
	int portNumber = 28189;

	public TransactionObject sendTransaction(TransactionObject transactionObject) {
		TransactionObject response = null;
		Socket echoSocket = null;
		try {
			echoSocket = new Socket();
			SocketAddress endpoint = new InetSocketAddress(hostName, portNumber);
			echoSocket.connect(endpoint, 1000 * 5);
			ObjectOutputStream outputStream = new ObjectOutputStream(
					echoSocket.getOutputStream());
			ObjectInputStream inputStream = new ObjectInputStream(
					echoSocket.getInputStream());
			outputStream.writeObject(transactionObject);
			while ((response = (TransactionObject) inputStream.readObject()) != null) {

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (echoSocket != null) {
				try {
					echoSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return transactionObject;
	}

}
