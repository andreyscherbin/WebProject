package com.epam.forum.validator;

public class LanguageValidator {
	private static final String REGEX_LANG = "(de_DE)|(en_US)|(be_BE)";

	public static boolean isLangValid(String data) {
		return data.matches(REGEX_LANG);
	}
}
