package com.epam.forum.security;

import java.util.Base64;

public class PasswordEncoder {
	public static String encodeString(String data) {
		String encodedString = Base64.getEncoder().encodeToString(data.getBytes());
		return encodedString;
	}
}
