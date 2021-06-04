package com.epam.forum.validator;

public class TopicValidator {
	private static final String REGEX_HEADER = "[\\p{Alnum}\\s\\p{IsCyrillic}?!,.<->;:=+/()\\\\\"\'#]{1,100}";
	private static final String REGEX_CONTENT = "[\\p{Alnum}\\s\\p{IsCyrillic}?!,.<->;:=+/()\\\\\"\'#]+";

	public static boolean isHeaderValid(String header) {
		return header.matches(REGEX_HEADER);
	}

	public static boolean isContentValid(String content) {
		return content.matches(REGEX_CONTENT);
	}
}
