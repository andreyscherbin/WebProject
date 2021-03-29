package com.epam.forum.security;

import java.util.Base64;

public class PasswordDecoder {
	public static String decodeString(String data) {
		byte[] decodedBytes = Base64.getDecoder().decode(data);
		String decodedString = new String(decodedBytes);
		return decodedString;
	}
}
