package co.trucom.chat.components;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Server {
	
	private static final String HELP_STRING = "\nList of Commands:\n\n/upper -> Your string will be all uppercase.\n"
			+ "/lower -> Your string will be all lowercase.\n"
			+ "/street -> Your string will be of alternate cases.\n"
			+ "/roosters -> This is a +18 kind of string.\n"
			+ "/crypt -> You'll be writing encrypted strings.\n"
			+ "/quit -> You'll quit this chat session.\n\n"
			+ "Write the same command to cancel it. Or just choose another command\n\n";

	private ThreadPoolExecutor executor;
	private ServerSocket serverSocket;
	private int port;
	private List<SimpleClient> clients;

	public Server(int port) {
		this.port = port;
		executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
		clients = new ArrayList<>();
	}

	public void start() {
		connect();
		System.out.println("Server started...");

		try {
			System.out.println("Accepting connections...");
			handleClients();
			serverSocket.close();
		} catch(IOException e) {
			throw new IllegalStateException("Error handling clients: " + e);
		}

	}

	private void handleClients() throws IOException{
		while(serverSocket.isBound()) {
			final Socket socket = serverSocket.accept();
			executor.execute(() -> {
				try {
					BufferedReader inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					String username = inStream.readLine().trim();
					final SimpleClient client = new SimpleClient(socket, username);
					clients.add(client);
					String incomingMsg;

					String welcomeStr = "Welcome to the chat, " + client.getUsername() + "!\n"
							+ "You may start typing.\n"
							+ "Type '/help' to get to know the chat's commands.\n";
					sendMessage(client, welcomeStr, true);
					String enterMsg = client.getUsername() + " entered the chat.";
					sendAllExceptUser(client, enterMsg, true);
					System.out.println(enterMsg);

					while(!(incomingMsg = inStream.readLine().trim()).equalsIgnoreCase("/quit")) {
						if(incomingMsg.charAt(0) == '/' && !incomingMsg.equals("/help")) {
							handleCommand(incomingMsg, client);
						} else if (incomingMsg.equals("/help")) {
							sendMessage(client, HELP_STRING, true);
						} else {
							sendAllExceptUser(client, incomingMsg, false);
						}
					}

					sendAllExceptUser(client, client.getUsername() + " left the chat.", true);
				} catch(IOException e) {
					throw new IllegalStateException("Error interacting with client: " + e);
				}
			});
		}
	}

	private void sendMessage(SimpleClient client, String message, boolean isServerMsg) {
		try {
			PrintWriter writer = new PrintWriter(client.getSocket().getOutputStream());

			writer.println(message);
			writer.flush();			
		} catch(IOException e) {
			throw new IllegalStateException("Error writing to client " + client.getUsername() + ": " + e);
		}
	}

	private void sendAllExceptUser(SimpleClient client, String message, boolean isFromServer) {
		String msgToSend = ((isFromServer) ? "" : client.getUsername() + " > ");
		
		msgToSend += (isFromServer) ? message : formatMessage(client, message);
		
		for(SimpleClient simpleClient: clients) {
			if(!simpleClient.getUsername().equals(client.getUsername())) {
				sendMessage(simpleClient, msgToSend, isFromServer);
			}
		}
	}

	private String formatMessage(SimpleClient client, String message) {
		String result = message;
		
		if(client.getCommandsMap().get("upper")) {
			result = result.toLowerCase();
		}

		if(client.getCommandsMap().get("lower")) {
			result = result.toUpperCase();
		}
		
		if(client.getCommandsMap().get("alternate")) {
			result = alternateString(result);
		}
		
		if(client.getCommandsMap().get("reddot")) {
			result = formatRedDot(result);
		}
		
		if (client.getCommandsMap().get("crypt")) {
			result = encrypt(result);
		}

		return result;
	}
	
	private String encrypt(String message) {
		String result = "";
		
		for(int i = 0; i < message.length(); i++) {
			int n = (int) Math.round(Math.random() * 20);
			int op = (int) Math.round(Math.random());
			n = (op == 1) ? n : -n;
			
			result += (char) (message.charAt(i) + n);
		}
		
		return result;
	}
	
	private String alternateString(String string) {
		String result = "";
		
		for(int i = 0; i < string.length(); i++) {
			if(i % 2 == 0) {
				result += ("" + string.charAt(i)).toUpperCase();
			} else  {
				result += ("" + string.charAt(i)).toLowerCase();
			}
		}
		
		return result;
	}

	private void handleCommand(String message, SimpleClient client) {
		message = message.trim().toLowerCase();
		
		client.switchCommand(message.substring(1));
		
		switch(message) {
			case "/upper": sendMessage(client, "UpperCase activated!", true); break;
			case "/lower": sendMessage(client, "LowerCase activated!", true); break;
			case "/street" : sendMessage(client, "StreetCase activated!", true); break;
			case "/roosters": sendMessage(client, "+18 activated!", true); break;
			case "/crypt": sendMessage(client, "Encryption activated!", true); break;
		}
	}
	
	private String formatRedDot(String message) {
		String [] msgArray = message.split(" ");
		String result = "";
		
		for(String str: msgArray) {
			result += str + " .|. ";
		}
		
		return result;
	}

	private void connect() {
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			throw new IllegalStateException("Error creating server: " + e);
		}
	}

}