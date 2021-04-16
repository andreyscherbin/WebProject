package com.epam.forum.util.email;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.forum.exception.MailException;
import com.epam.forum.model.entity.EmailMessage;

public class MailSender {
	private static Logger logger = LogManager.getLogger();
	private static final Properties properties = new Properties();
	private static final String RESOURCE = "mail.properties";
	private static final String FROM = "andrey123scherbin@gmail.com";
	private static final String TYPE = "text/html; charset=utf-8";

	static {
		try (InputStream inputStream = MailSender.class.getClassLoader().getResourceAsStream(RESOURCE)) {
			properties.load(inputStream);
		} catch (IOException e) {
			logger.fatal("can't load mail properties", e);
			throw new RuntimeException("can't load mail properties: ", e);
		}
	}

	private MailSender() {
	}

	public static void sendEmail(EmailMessage emailMessage) throws MailException {
		Session session = SessionFactory.createSession(properties);
		String sendToEmail = emailMessage.getRecipient();
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(FROM));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(sendToEmail));
			message.setSubject(emailMessage.getSubject());
			message.setContent(emailMessage.getContent(), TYPE);
			Transport.send(message);
			logger.info("Email {} sent successfully...", emailMessage);
		} catch (AddressException e) {
			logger.error("Invalid address: " + sendToEmail + " " + e);
			throw new MailException("Invalid address: " + sendToEmail, e);
		} catch (MessagingException e) {
			logger.error("Error generating or sending message: " + e);
			throw new MailException("Error generating or sending message: ", e);
		}
	}
}
