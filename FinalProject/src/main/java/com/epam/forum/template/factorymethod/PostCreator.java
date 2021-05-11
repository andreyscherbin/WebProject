package com.epam.forum.template.factorymethod;

import java.time.LocalDateTime;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.epam.forum.exception.EntityException;
import com.epam.forum.model.entity.Post;
import com.epam.forum.model.entity.Topic;
import com.epam.forum.model.entity.User;

public class PostCreator {
	private static Logger logger = LogManager.getLogger();

	public static Post getPostFromFactoryMethod(LocalDateTime creationDate, String content, User user, Topic topic)
			throws EntityException {
		if (creationDate == null || content == null || user == null || topic == null) {
			logger.error("invalid creationDate: {} or content: {} or user: {} or topic: {}", creationDate, content,
					user, topic);
			throw new EntityException("invalid creationDate: " + creationDate + "or content: " + content + "or user: "
					+ user + "or topic: ");
		}
		return new Post(content, creationDate, topic, user);
	}
}
