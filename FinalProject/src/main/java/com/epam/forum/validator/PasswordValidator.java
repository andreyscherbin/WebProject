package com.epam.forum.validator;

public class PasswordValidator {
	private static final String REGEX_PASSWORD = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}";

	public static boolean isValid(String data) {
		return data.matches(REGEX_PASSWORD);
	}
}
