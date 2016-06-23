package edu.adriennicholas.atm.server;

import java.net.ServerSocket;
import java.net.Socket;

public class ATMServer {
	public static void main(String[] arg) {

		try {
			ServerSocket myServerSocket = new ServerSocket(28189);
			for (;;) {
				Socket incoming = myServerSocket.accept();
				new ThreadedDataObjectHandler(incoming).start();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
