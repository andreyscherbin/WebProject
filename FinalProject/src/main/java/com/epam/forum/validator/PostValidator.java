package com.epam.forum.validator;

public class PostValidator {
	private static final String REGEX_CONTENT = "^(?!\\s*$)[\\p{Alnum}\\s\\p{IsCyrillic}?!,.<->;:=+/()\\\\\"\'#]{1,65535}$";

	public static boolean isContentValid(String content) {
		return content.matches(REGEX_CONTENT);
	}
}
