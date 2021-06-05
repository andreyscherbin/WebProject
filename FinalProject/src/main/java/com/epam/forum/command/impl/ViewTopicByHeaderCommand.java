package com.epam.forum.command.impl;

import java.util.ArrayList;
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
import com.epam.forum.validator.TopicValidator;

/**
 * The {@code ViewTopicByHeaderCommand} class represents view topic by header
 * command
 * 
 * @author Andrey Shcherbin
 * @version 1.0
 * @since 2021-05-30
 *
 */
public class ViewTopicByHeaderCommand implements Command {
	private static Logger logger = LogManager.getLogger();
	private static final String PARAM_NAME_HEADER = "header";
	private static final String ATRIBUTE_NAME_TOPICS = "topics";
	private static final String ATTRIBUTE_NAME_MESSAGE = "message";
	private static final String ATTRIBUTE_VALUE_WRONG_INPUT = "message.wrong.input";
	private static final String ATTRIBUTE_VALUE_TOPICS_EMPTY = "message.topics.empty";
	private TopicService topicService;

	public ViewTopicByHeaderCommand(TopicService topicService) {
		this.topicService = topicService;
	}

	@Override
	public Router execute(HttpServletRequest request, HttpServletResponse response) {
		Router router = new Router();
		String pattern = request.getParameter(PARAM_NAME_HEADER);
		if (pattern == null || !TopicValidator.isHeaderValid(pattern)) {
			request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_WRONG_INPUT);
			router.setPage(PagePath.HOME);
			return router;
		}
		List<Topic> topics = new ArrayList<>();
		try {
			topics = topicService.findTopicsByHeader(pattern);
			if (!topics.isEmpty()) {
				request.setAttribute(ATRIBUTE_NAME_TOPICS, topics);
				router.setPage(PagePath.SEARCH);
			} else {
				request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_TOPICS_EMPTY);
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
