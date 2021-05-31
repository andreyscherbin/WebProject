package com.epam.forum.command.impl;

import java.util.Optional;

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
import com.epam.forum.validator.DigitValidator;

/**
 * The {@code PinTopicCommand} class represents pin/unpin topic command
 * 
 * @author Andrey Shcherbin
 * @version 1.0
 * @since 2021-05-30
 *
 */
public class PinTopicCommand implements Command {
	private static Logger logger = LogManager.getLogger();
	private static final String PARAM_NAME_TOPIC_ID = "topic_id";
	private static final String ATTRIBUTE_NAME_MESSAGE = "message";
	private static final String ATTRIBUTE_VALUE_KEY_WRONG_INPUT = "message.wrong.input";
	private static final String ATTRIBUTE_VALUE_KEY_EMPTY_TOPIC = "message.empty.topic";

	private TopicService topicService;

	public PinTopicCommand(TopicService topicService) {
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
				if (!findTopic.isPinned()) {
					findTopic.setPinned(true);
					topicService.pinTopic(findTopic);
				} else {
					findTopic.setPinned(false);
					topicService.unpinTopic(findTopic);
				}
				router.setPage(PagePath.SECTION);
			} else {
				request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_KEY_EMPTY_TOPIC);
				router.setPage(PagePath.SECTION);
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
