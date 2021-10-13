package co.trucom.chat.components;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

	private Socket socket;
	private String host;
	private int port;
	private String username;
	private BufferedReader serverReader;
	private Scanner inputReader;
	private PrintWriter writer;

	public Client(String host, int port) {
		this.host = host;
		this.port = port;
	}

	private void connect() {
		try {
			socket = new Socket(host, port);
		} catch (IOException e) {
			throw new IllegalStateException("Error connecting to server: " + e);
		}
	}

	private void setUsername() {
		System.out.print("Choose a username: ");
		username = inputReader.nextLine().trim();
	}
	public void start() {
		String message = "";

		inputReader = new Scanner(System.in);
		setUsername();

		connect();
		openStreams();
		send(username);
		receiveFromServer();

		while(!(message.equalsIgnoreCase("/quit"))) {
			message = inputReader.nextLine().trim();
			send(message);
		}

		close();
		System.out.println("Bye!");
	}

	private void openStreams() {
		try {
			serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			throw new IllegalStateException("Error creating reader from server: " + e);
		}

		try {
			writer = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
			throw new IllegalStateException("Error creating writer to server: " + e);
		}
	}

	public void send(String message) {
		writer.println(message);
		writer.flush();
	}
	
	private void receiveFromServer() {
		Thread serverThread = new Thread(() -> {
			try {
				while (socket.isBound()) {
					String incomingMsg = serverReader.readLine().trim();
					if(!incomingMsg.trim().equals("")) {
						System.out.println(incomingMsg);
					}
				}
				socket.close();
			} catch(IOException e) {
				throw new IllegalStateException("Error receiving messages from server: " + e);
			}

		});
		
		serverThread.start();	
	}

	private void close() {
		try {
			inputReader.close();
			serverReader.close();
			writer.close();
			socket.close();
		} catch (IOException e) {
			throw new IllegalStateException("Error closing streams and socket: " + e);
		}
	}

}
