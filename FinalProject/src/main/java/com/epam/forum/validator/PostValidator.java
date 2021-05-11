package com.epam.forum.validator;

public class PostValidator {
	private static final String REGEX_CONTENT = "[\\p{Alnum}\\s\\p{IsCyrillic}?!,.<->;:=+/()\\\\\"\'#]+";

	public static boolean isContentValid(String content) {
		return content.matches(REGEX_CONTENT);
	}
}
