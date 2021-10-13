package co.trucom.chat;

import co.trucom.chat.components.Server;

public class MainServer {

	public static void main(String [] args) {
		Server server = new Server(9090);
		server.start();
	}

}
