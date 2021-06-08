package com.epam.forum.validator;

public class ActivationCodeValidator {
	private static final String REGEX_ACTIVATION_CODE = "^[A-Fa-f\\d]{1,128}$";

	public static boolean isActivationCodeValid(String activationCode) {
		return activationCode.matches(REGEX_ACTIVATION_CODE);
	}
}
