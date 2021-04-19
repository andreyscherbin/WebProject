package com.epam.forum.validator;

public class DigitLatinValidator {
	private static final String REGEX_DIGIT_LATIN = "[\\p{Alnum}]+";

	public static boolean isValid(String data) {
		return data.matches(REGEX_DIGIT_LATIN);
	}
}
