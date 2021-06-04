package com.epam.forum.util.encryptor;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.epam.forum.exception.ServiceException;

public class ActivationCodeEncryptor {

	private static Logger logger = LogManager.getLogger();
	private static final String ALGORITHM = "SHA-512";

	public static String encryptActivationCode(String input) throws ServiceException {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance(ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			logger.error("no such algorithm: {} exception {}", ALGORITHM, e);
			throw new ServiceException("no such algorithm: " + ALGORITHM, e);
		}
		byte[] messageDigest = md.digest(input.getBytes());
		BigInteger no = new BigInteger(1, messageDigest);
		String hashtext = no.toString(16);
		StringBuilder sb = new StringBuilder(hashtext);
		while (sb.length() < 32) {
			sb.append("0");
		}
		return sb.toString();
	}
}
