package com.epam.forum.validator;

public class EmailValidator {
	private static final String REGEX_EMAIL = "^[\\p{Alnum}.!#$%&’*+/=?^_`{|}~-]+@[\\p{Alnum}-]+(?:\\.[\\p{Alnum}-]+)*$";

	public static boolean isValid(String data) {
		return data.matches(REGEX_EMAIL);
	}
}
