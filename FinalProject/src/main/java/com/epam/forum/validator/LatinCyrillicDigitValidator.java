package com.epam.forum.validator;

public class LatinCyrillicDigitValidator {
	private static final String REGEX_LATIN_CYRILLIC = "[\\p{Alnum} \\p{IsCyrillic}]+";

	public static boolean isValid(String data) {
		return data.matches(REGEX_LATIN_CYRILLIC);
	}
}
