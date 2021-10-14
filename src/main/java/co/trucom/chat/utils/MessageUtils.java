package co.trucom.chat.utils;

public class MessageUtils {
	
	public static String formatRedDot(String message) {
		String [] msgArray = message.split(" ");
		String result = "";
		
		for(String str: msgArray) {
			result += str + " .|. ";
		}
		
		return result;
	}
	
	public static String alternateString(String string) {
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
	
	public static String encrypt(String message) {
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
