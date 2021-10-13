package co.trucom.chat.components;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class SimpleClient {

	private Socket socket;
	private String username;
	private Map<String, Boolean> commandsMap;

	public SimpleClient(Socket socket, String username) {
		this.socket = socket;
		this.username = username;
		commandsMap = new HashMap<>();
		commandsMap.put("upper", false);
		commandsMap.put("lower", false);
		commandsMap.put("reddot", false);
		commandsMap.put("alternate", false);
		commandsMap.put("street", false);
		commandsMap.put("crypt", false);
	}

	public Socket getSocket() {
		return socket;
	}
	
	public void switchCommand(String cmd) {
		System.out.println(cmd);
		if (cmd == null || commandsMap.get(cmd) == null) {
			throw new IllegalArgumentException("Couldn't find desired command");
		}
		cmd = cmd.toLowerCase();
		
		commandsMap.put(cmd, commandsMap.get(cmd) ? false : true);
		
		if(commandsMap.get(cmd)) {
			disableAllBut(cmd);
		}
	}

	private void disableAllBut(String command) {
		for(Map.Entry<String, Boolean> cmd : commandsMap.entrySet()) {
			if(!cmd.getValue()) {
				commandsMap.put(cmd.getKey(), false);
			}
		}
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Map<String, Boolean> getCommandsMap() {
		return commandsMap;
	}

	public void setCommandsMap(Map<String, Boolean> commandsMap) {
		this.commandsMap = commandsMap;
	}
}
