package com.epam.forum.validator;

public class SectionValidator {
	private static final String REGEX_HEADER = "[\\p{Alpha}]{1,100}";
	private static final String REGEX_DESCRIPTION = "[\\p{Alnum}\\s\\p{IsCyrillic}]{1,100}";

	public static boolean isHeaderValid(String header) {
		return header.matches(REGEX_HEADER);
	}

	public static boolean isDescriptionValid(String description) {
		return description.matches(REGEX_DESCRIPTION);
	}
}
