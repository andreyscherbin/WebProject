package com.epam.forum.command.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.epam.forum.command.Command;
import com.epam.forum.command.PagePath;
import com.epam.forum.command.Router;
import com.epam.forum.exception.ErrorAttribute;
import com.epam.forum.exception.ServiceException;
import com.epam.forum.model.entity.Topic;
import com.epam.forum.model.service.TopicService;

/**
 * The {@code ViewTopicCommand} class represents view topic command
 * 
 * @author Andrey Shcherbin
 * @version 1.0
 * @since 2021-05-30
 *
 */
public class ViewTopicCommand implements Command {
	private static Logger logger = LogManager.getLogger();
	private static final String ATRIBUTE_NAME_TOPICS = "topics";
	private static final String ATTRIBUTE_NAME_MESSAGE = "message";
	private static final String ATTRIBUTE_VALUE_KEY_TOPICS_EMPTY = "message.topics.empty";
	private TopicService topicService;

	public ViewTopicCommand(TopicService topicService) {
		this.topicService = topicService;
	}

	@Override
	public Router execute(HttpServletRequest request, HttpServletResponse response) {
		Router router = new Router();
		List<Topic> topics;
		try {
			topics = topicService.findAllTopics();
			if (!topics.isEmpty()) {
				request.setAttribute(ATRIBUTE_NAME_TOPICS, topics);
				router.setPage(PagePath.SEARCH);
			} else {
				request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_KEY_TOPICS_EMPTY);
				router.setPage(PagePath.SEARCH);
			}
		} catch (ServiceException e) {
			logger.error("service exception ", e);
			request.setAttribute(ErrorAttribute.ERROR_MESSAGE, e.getMessage());
			request.setAttribute(ErrorAttribute.ERROR_CAUSE, e.getCause());
			request.setAttribute(ErrorAttribute.ERROR_LOCATION, request.getRequestURI());
			request.setAttribute(ErrorAttribute.ERROR_CODE, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			router.setPage(PagePath.ERROR);
		}
		return router;
	}
}
