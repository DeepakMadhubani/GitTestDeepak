package com.macys.stella.attribute.dbreader.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParsePosition;

public class NumberUtils {
	private static DecimalFormat decimalFormat;

	public static BigDecimal parseDecimal(String value) {
		BigDecimal result = (BigDecimal) decimalFormat.parse(value, new ParsePosition(0));
		result.setScale(2, RoundingMode.HALF_UP);
		return result;
	}

	static {
		decimalFormat = new DecimalFormat();
		decimalFormat.setParseBigDecimal(true);
	}
	
	public static String RoundTo2Decimals(double d) {
		DecimalFormat twoDForm = new DecimalFormat("#.00");
		return twoDForm.format(d);
	}
}
