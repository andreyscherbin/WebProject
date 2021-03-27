package com.epam.forum.validator;

public class DigitValidator {
	private static final String REGEX_DIGIT = "\\d";
	
	public static boolean isDigit(String data) {
		return data.matches(REGEX_DIGIT);
	}
}
