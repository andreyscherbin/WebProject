package com.epam.forum.util.activation;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.forum.exception.ServiceException;

public class Sha256ActivationCodeGenerator {

	private static Logger logger = LogManager.getLogger();
	private static final String ALGORITHM = "SHA-256";

	public static String generate(String input) throws ServiceException {
		MessageDigest mDigest = null;
		try {
			mDigest = MessageDigest.getInstance(ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			logger.error("no such algorithm: {} exception {}", ALGORITHM, e);
			throw new ServiceException("no such algorithm: " + ALGORITHM, e);
		}
		byte[] result = mDigest.digest(input.getBytes());
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < result.length; i++) {
			sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
	}
}
