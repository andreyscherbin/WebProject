package com.epam.forum.validator;

public class EmailValidator {
	private static final String REGEX_EMAIL = "^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";

	public static boolean isValid(String data) {
		return data.matches(REGEX_EMAIL);
	}
}
