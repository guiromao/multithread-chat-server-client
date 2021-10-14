package co.trucom.chat.components;

import java.net.Socket;

public class SimpleClient {

	private long id;
	private Socket socket;
	private String username;
	private CommandsFeature commands;

	public SimpleClient(Socket socket, String username, long id) {
		this.socket = socket;
		this.username = username;
		this.id = id;	
		this.commands = new CommandsFeature();
	}
	
	public void switchCommand(String cmd) {
		commands.switchCommand(cmd);
	}
	
	public boolean hasCommand(String cmd) {
		return commands.hasCommand(cmd);
	}
	
	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public String getUsername() {
		return username;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public CommandsFeature getCommands() {
		return commands;
	}

	public void setCommands(CommandsFeature commands) {
		this.commands = commands;
	}

	
}