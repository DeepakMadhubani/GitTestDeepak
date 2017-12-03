/* (c) 2010, Macys, Inc. All Rights Reserved */
package com.macys.stella.java.util;

import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Convenience String utility methods for use inside driver.est suite.
 * 
 * @author Guillaume Jeudy <a href="mailto:gjeudy@teksystems.com">gjeudy@teksystems.com</a>
 * 
 */
public class StringUtils extends org.apache.commons.lang.StringUtils {
	private static final char END_HTML_TAG_DELIMITER = '>';
	private static final char START_HTML_TAG_DELIMITER = '<';

	/**
	 * Returns the first matching () group in string value.
	 * 
	 * @param value
	 * @param regexp
	 * @return first matching group
	 */
	public static String grep(String value, String regexp) {
		Pattern pattern = Pattern.compile(regexp);
		Matcher matcher = pattern.matcher(value);
		if(matcher.find(0)) {
			return matcher.group(1);
		}
		return null;
	}

	/**
	 * Remove all spaces around text blocks and in between HTML tags.
	 */
	public static String normalizeHTML(String htmlString) {
		int length = htmlString.length();
		// should never contain more than 1 object otherwise we are dealing with
		// invalid HTML.
		// However you never know, browsers are lenient these days.
		Stack<Object> tagDepth = new Stack<Object>();
		StringBuilder inBetweenTagsString = new StringBuilder();
		StringBuilder result = new StringBuilder();
		for(int i = 0; i < length; i++) {
			char currChar = htmlString.charAt(i);
			switch(currChar) {
				case START_HTML_TAG_DELIMITER:
					result.append(inBetweenTagsString.toString().trim());
					if(inBetweenTagsString.length() > 0) {
						inBetweenTagsString.delete(0, inBetweenTagsString.length());
					}
					tagDepth.push(new Object());
					break;
				case END_HTML_TAG_DELIMITER:
					tagDepth.pop();
					break;
			}
			if(isInsideATag(tagDepth) || currChar == END_HTML_TAG_DELIMITER) {
				result.append(currChar);
			}
			else {
				inBetweenTagsString.append(currChar);
			}
		}
		return result.toString();
	}

	private static boolean isInsideATag(Stack<Object> tagDepth) {
		return !tagDepth.isEmpty();
	}

	/**
	 * Method to format the String values (like image IDs or product IDs) to have space in between.
	 * 
	 * @param value
	 *            the String array
	 * @return a String with a space between and in the end
	 */
	public static String spaceSeparatedIds(String[] values) {
		String formatted = "";
		for(String value : values) {
			formatted += value + " ";
		}
		return formatted.trim();
	}

	public static String commaSeparatedIds(String[] values) {
		String formatted = "";
		for(String value : values) {
			formatted += value + ",";
		}
		return formatted.substring(0, formatted.length() - 1);
	}
	
	public static String commaSeparatedIds(ArrayList<String> values) {
		String formatted = "";
		for(String value : values) {
			formatted += value + ",";
		}
		return formatted.substring(0, formatted.length() - 1);
	}

	/**
	 * Method to format the String values (like image IDs or product IDs) to have space in between.
	 * 
	 * @param value
	 *            the ArrayList String
	 * @return a String with a space between
	 */
	public static String spaceSeparatedIds(ArrayList<String> values) {
		String formatted = "";
		for(String value : values) {
			formatted += value + " ";
		}
		return formatted.trim();
	}

	public static String formatTime(long millis) {
		long realMillis = millis % 1000;
		long seconds = (millis / 1000) % 60;
		long minutes = millis / 60000;

		if(realMillis + seconds + minutes == 0) {
			return "immediately";
		}
		if(minutes == 0) {
			if(realMillis > 0) {
				return String.format("%1$d.%2$03d seconds", seconds, realMillis);
			}
			if(seconds > 1) {
				return String.format("%1$d seconds", seconds);
			}
			return "1 second";
		}
		if(seconds == 0 && realMillis == 0) {
			if(minutes > 1) {
				return String.format("%1$d minutes", minutes);
			}
			return "1 minute";
		}

		StringBuffer buffer = new StringBuffer(String.format("%1$d:%2$02d", minutes, seconds));
		if(realMillis > 0) {
			buffer.append(String.format(".%1$03d", realMillis));
		}
		return buffer.toString();
	}
	
	/**
	 * Method to append the String values (like image IDs or product IDs).
	 * 
	 * @param value
	 *            the ArrayList String
	 * @return a StringBuffer
	 */
	public static StringBuffer appendValuesToString(StringBuffer result, String[] toAppend) {
		for(String value : toAppend)
		{
			if(result.length()>0){
				result.append(",");
			}
			result.append(value);
		}
		return result;
	}
	
	public static String[] convertCommaSeparatedStringToArrayString(String commaSeparatedString)
	{
		String[] arrayString = commaSeparatedString.split(",");
		return arrayString;
	}



	public static boolean isNullOrEmpty(String string) {

		if((string == null) || (string.trim() == null) || (string.isEmpty()))
			return true;
		else
			return false;
	}

	public static boolean isNullOrNone(String string) {
		if((string == null) || (string.trim().toLowerCase().equals("none")) || (string.trim() == null) || (string.isEmpty()))
			return true;
		else
			return false;
	}

	public static boolean isNullOrZero(String string) {
		if((string == null) || (string.trim() == "0") || (string.trim() == null) || (string.isEmpty()))
			return true;
		else
			return false;
	}
	
public static String convertPriceToRangeFormat(String price){
		
		if(price.endsWith("0")){
			price = price.substring(0, price.length()-1);
		}
		
		String newPriceFormat = price+"-"+price;
		return newPriceFormat;
	}

}
