package co.trucom.chat.components;

import java.net.Socket;

public class SimpleClient {

	private Socket socket;
	private String username;
	private boolean allUpperCase;
	private boolean allLowerCase;
	private boolean alternateCases;
	private boolean redDot;
	private boolean crypt;

	public SimpleClient(Socket socket, String username) {
		this.socket = socket;
		this.username = username;
		allUpperCase = false;
		allLowerCase = false;
		alternateCases = false;
		redDot = false;
		crypt = false;
	}

	public Socket getSocket() {
		return socket;
	}
	
	public void switchAlternateCase() {
		alternateCases = alternateCases ? false : true;
		
		if(alternateCases) {
			allUpperCase = false;
			allLowerCase = false;
			redDot = false;
			crypt = false;
		}
	}

	public void switchUpperCase() {
		allUpperCase = allUpperCase ? false : true;

		if(allUpperCase) {
			allLowerCase = false;
			alternateCases = false;
			redDot = false;
			crypt = false;
		}
	}

	public void switchLowerCase() {
		allLowerCase = allLowerCase ? false : true;

		if(allLowerCase) {
			allUpperCase = false;
			alternateCases = false;
			redDot = false;
			crypt = false;
		}
	}
	
	public void switchRedDot() {
		redDot = redDot ? false : true;

		if(redDot) {
			allUpperCase = false;
			alternateCases = false;
			allLowerCase = false;
			crypt = false;
		}
	}
	
	public void switchCrypt() {
		crypt = crypt ? false : true;

		if(redDot) {
			allUpperCase = false;
			alternateCases = false;
			allLowerCase = false;
			redDot = false;
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

	public boolean isAllUpperCase() {
		return allUpperCase;
	}

	public void setAllUpperCase(boolean allUpperCase) {
		this.allUpperCase = allUpperCase;
	}

	public boolean isAllLowerCase() {
		return allLowerCase;
	}

	public void setAllLowerCase(boolean allLowerCase) {
		this.allLowerCase = allLowerCase;
	}

	public boolean isAlternateCases() {
		return alternateCases;
	}

	public void setAlternateCases(boolean alternateCases) {
		this.alternateCases = alternateCases;
	}

	public boolean isRedDot() {
		return redDot;
	}

	public void setRedDot(boolean redDot) {
		this.redDot = redDot;
	}

	public boolean isCrypt() {
		return crypt;
	}

	public void setCrypt(boolean crypt) {
		this.crypt = crypt;
	}

}
