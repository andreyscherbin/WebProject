package com.epam.forum.validator;

public class CyrillicLatinValidator {
	private static final String REGEX_CYRILLIC_LATIN = "\\p{Alpha}";

	public static boolean isCyrillicLatin(String data) {
		return data.matches(REGEX_CYRILLIC_LATIN);
	}
}
