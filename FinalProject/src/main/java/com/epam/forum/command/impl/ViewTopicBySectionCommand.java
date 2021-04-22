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
import com.epam.forum.exception.ErrorTable;
import com.epam.forum.exception.ServiceException;
import com.epam.forum.model.entity.Topic;
import com.epam.forum.model.service.TopicService;
import com.epam.forum.validator.DigitValidator;

public class ViewTopicBySectionCommand implements Command {
	private static Logger logger = LogManager.getLogger();
	private static final String PARAM_NAME_SECTION = "section";
	private static final String ATRIBUTE_NAME_TOPICS = "topics";
	private static final String ATTRIBUTE_NAME_MESSAGE = "message";
	private static final String ATTRIBUTE_VALUE_TOPICS = "message.empty.topics";
	private static final String ATTRIBUTE_VALUE_WRONG_SECTION = "topic.sectionId.validation";
	private TopicService topicService;

	public ViewTopicBySectionCommand(TopicService topicService) {
		this.topicService = topicService;
	}

	@Override
	public Router execute(HttpServletRequest request, HttpServletResponse response) {
		Router router = new Router();
		String section = request.getParameter(PARAM_NAME_SECTION);
		if (!DigitValidator.isValid(section)) {
			request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_WRONG_SECTION);
			router.setPage(PagePath.HOME);
			return router;
		}
		Long sectionId = Long.parseLong(section);
		List<Topic> topics = new ArrayList<>();
		try {
			topics = topicService.findTopicsBySection(sectionId);
			if (!topics.isEmpty()) {
				request.setAttribute(ATRIBUTE_NAME_TOPICS, topics);
				router.setPage(PagePath.SECTION);
			} else {
				request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_TOPICS);
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
