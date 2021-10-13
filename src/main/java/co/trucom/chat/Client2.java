package co.trucom.chat;

import co.trucom.chat.components.Client;

public class Client2 {
	
	public static void main(String [] args) {
		Client client = new Client("127.0.0.1", 9090);
		client.start();
	}

}
