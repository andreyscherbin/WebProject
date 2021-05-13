package com.epam.forum.validator;

public class UserValidator {
	private static final String REGEX_USERNAME = "[\\p{Alnum}]{1,10}";
	private static final String REGEX_PASSWORD = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,60}";
	private static final String REGEX_EMAIL = "(?=^.{1,255}$)^[\\p{Alnum}.!#$%&’*+/=?^_`{|}~-]+@[\\p{Alnum}-]+(?:\\.[\\p{Alnum}-]+)*$";
	private static final String REGEX_ROLE = "(ADMIN)|(MODER)|(USER)";

	public static boolean isUserNameValid(String userName) {
		return userName.matches(REGEX_USERNAME);
	}

	public static boolean isPasswordValid(String password) {
		return password.matches(REGEX_PASSWORD);
	}

	public static boolean isEmailValid(String email) {
		return email.matches(REGEX_EMAIL);
	}

	public static boolean isRoleValid(String role) {
		return role.matches(REGEX_ROLE);
	}
}
