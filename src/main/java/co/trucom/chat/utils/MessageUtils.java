package co.trucom.chat.utils;

import static co.trucom.chat.utils.MessageUtils.alternateString;
import static co.trucom.chat.utils.MessageUtils.encrypt;
import static co.trucom.chat.utils.MessageUtils.formatRedDot;

import co.trucom.chat.components.SimpleClient;

public class MessageUtils {
	
	public static String formatMessage(SimpleClient client, String message) {
		String result = message;
		
		if(client.hasCommand("lower")) {
			result = result.toLowerCase();
		}

		else if(client.hasCommand("upper")) {
			result = result.toUpperCase();
		}

		else if(client.hasCommand("street")) {
			result = alternateString(result);
		}

		else if(client.hasCommand("roosters")) {
			result = formatRedDot(result);
		}

		else if (client.hasCommand("crypt")) {
			result = encrypt(result);
		}

		return result;
	}
	
	private static String formatRedDot(String message) {
		String [] msgArray = message.split(" ");
		String result = "";
		
		for(String str: msgArray) {
			result += str + " .|. ";
		}
		
		return result;
	}
	
	private static String alternateString(String string) {
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
	
	private static String encrypt(String message) {
		String result = "";
		
		for(int i = 0; i < message.length(); i++) {
			int n = (int) Math.round(Math.random() * 20);
			int op = (int) Math.round(Math.random());
			n = (op == 1) ? n : -n;
			
			result += (char) (message.charAt(i) + n);
		}
		
		return result;
	}

}
