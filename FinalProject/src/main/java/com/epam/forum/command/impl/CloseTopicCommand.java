package com.epam.forum.command.impl;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.forum.command.Command;
import com.epam.forum.command.PagePath;
import com.epam.forum.command.Router;
import com.epam.forum.exception.ErrorTable;
import com.epam.forum.exception.ServiceException;
import com.epam.forum.model.entity.Topic;
import com.epam.forum.model.service.TopicService;
import com.epam.forum.validator.DigitValidator;

public class CloseTopicCommand implements Command {
	private static Logger logger = LogManager.getLogger();
	private static final String PARAM_NAME_TOPIC_ID = "topic_id";
	private static final String ATTRIBUTE_NAME_MESSAGE = "message";
	private static final String ATTRIBUTE_VALUE_KEY_WRONG_INPUT = "message.wrong.input";
	private static final String ATTRIBUTE_VALUE_KEY_TOPIC_EMPTY = "message.empty.topic";

	private TopicService topicService;

	public CloseTopicCommand(TopicService topicService) {
		this.topicService = topicService;
	}

	@Override
	public Router execute(HttpServletRequest request, HttpServletResponse response) {
		Router router = new Router();
		String topicId = request.getParameter(PARAM_NAME_TOPIC_ID);
		if (topicId == null || !DigitValidator.isValid(topicId)) {
			request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_KEY_WRONG_INPUT);
			router.setPage(PagePath.SECTION);
			return router;
		}
		long id = Integer.parseInt(topicId);
		Optional<Topic> topic = Optional.empty();
		try {
			topic = topicService.findTopicById(id);
			if (!topic.isEmpty()) {
				Topic findTopic = topic.get();
				if (!findTopic.isClosed()) {
					findTopic.setClosed(true);
					topicService.closeTopic(findTopic);
				} else {
					findTopic.setClosed(false);
					topicService.uncloseTopic(findTopic);
				}
				router.setPage(PagePath.SECTION);
			} else {
				request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_KEY_TOPIC_EMPTY);
				router.setPage(PagePath.SECTION);
			}
		} catch (ServiceException e) {
			logger.error("service exception ", e);
			request.setAttribute(ErrorTable.ERROR_MESSAGE, e.getMessage());
			request.setAttribute(ErrorTable.ERROR_CAUSE, e.getCause());
			request.setAttribute(ErrorTable.ERROR_LOCATION, request.getRequestURI());
			request.setAttribute(ErrorTable.ERROR_CODE, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			router.setPage(PagePath.ERROR);
		}
		return router;
	}
}
