package com.epam.forum.util.email;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import com.epam.forum.model.entity.ActivationCode;
import com.epam.forum.model.entity.EmailMessage;
import com.epam.forum.model.entity.User;

public class EmailMessageFactory {

	private static final String SUBJECT = "forum: activate your account!";
	private static final String H1_STARTING_TAG = "<h1>";
	private static final String H1_CLOSING_TAG = "</h1>";
	private static final String A_STARTING_TAG = "<a ";
	private static final String A_CLOSING_TAG = "</a>";
	private static final String HREF_ATTRIBUTE = "href=\"http://localhost:8080/FinalProject/controller?command=activation&username=";
	private static final String ATTRIBUTE_NAME_CODE = "&code=";
	private static final String PLACEHOLDER = "\">click here to activate your account";

	public static EmailMessage createEmailMessage(ActivationCode activationCode) {
		String emailAddress = activationCode.getUser().getEmail();
		StringBuilder builder = new StringBuilder();
		User user = activationCode.getUser();
		String name = user.getUserName();
		name = Jsoup.clean(name, Whitelist.none());
		String id = activationCode.getId();
		builder.append(H1_STARTING_TAG);
		builder.append(A_STARTING_TAG);
		builder.append(HREF_ATTRIBUTE);
		builder.append(name);
		builder.append(ATTRIBUTE_NAME_CODE);
		builder.append(id);
		builder.append(PLACEHOLDER);
		builder.append(A_CLOSING_TAG);
		builder.append(H1_CLOSING_TAG);
		String content = builder.toString();
		EmailMessage emailMessage = new EmailMessage(emailAddress, SUBJECT, content);
		return emailMessage;
	}
}
