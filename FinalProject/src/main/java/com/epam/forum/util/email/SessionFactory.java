package com.epam.forum.util.email;

import java.util.Properties;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

class SessionFactory {

	private static final String KEY_NAME = "mail.user.name";
	private static final String KEY_PASSWORD = "mail.user.password";

	static Session createSession(Properties configProperties) {
		String userName = configProperties.getProperty(KEY_NAME);
		String userPassword = configProperties.getProperty(KEY_PASSWORD);
		return Session.getDefaultInstance(configProperties, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, userPassword);
			}
		});
	}
}