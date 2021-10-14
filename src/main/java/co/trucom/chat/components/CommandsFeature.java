package co.trucom.chat.components;

import java.util.HashMap;
import java.util.Map;

public class CommandsFeature {

	private Map<String, Boolean> commandsMap;
	
	public CommandsFeature() {
		commandsMap = new HashMap<>();
		commandsMap.put("upper", false);
		commandsMap.put("lower", false);
		commandsMap.put("roosters", false);
		commandsMap.put("street", false);
		commandsMap.put("crypt", false);
	}
	
	public void switchCommand(String cmd) {
		assertCommandExists(cmd);

		cmd = cmd.toLowerCase();
		
		commandsMap.put(cmd, !commandsMap.get(cmd));
		
		if(commandsMap.get(cmd)) {
			disableAllBut(cmd);
		}
	}

	private void disableAllBut(String command) {
		for(Map.Entry<String, Boolean> cmd : commandsMap.entrySet()) {
			if(!cmd.getKey().equals(command)) {
				commandsMap.put(cmd.getKey(), false);
			}
		}
	}
	
	public boolean hasCommand(String cmd) {
		assertCommandExists(cmd);

		return commandsMap.get(cmd);
	}
	
	private void assertCommandExists(String cmd) {
		if(cmd == null || !commandsMap.containsKey(cmd)) {
			throw new IllegalArgumentException("Command '" + cmd + "' not found in commands list.");
		}
	}

	public Map<String, Boolean> getCommandsMap() {
		return commandsMap;
	}

	public void setCommandsMap(Map<String, Boolean> commandsMap) {
		this.commandsMap = commandsMap;
	}

}
