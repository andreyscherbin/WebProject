package com.epam.forum.validator;

public class PostValidator {
	private static final String REGEX_POST = "[\\p{Alnum}\\s\\p{IsCyrillic}?!,.<->;:=+/()\\\\\"\'#]+";

	public static boolean isValid(String data) {
		return data.matches(REGEX_POST);
	}
}
