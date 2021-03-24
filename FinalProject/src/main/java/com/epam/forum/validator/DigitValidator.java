package com.epam.forum.validator;

public class DigitValidator {
	private static final String REGULAR_DIGIT = "\\d";
	
	public static boolean isDigit(String data) {
		return data.matches(REGULAR_DIGIT);
	}
}
