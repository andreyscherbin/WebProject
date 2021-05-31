package com.epam.forum.command.impl;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.epam.forum.command.Command;
import com.epam.forum.command.PagePath;
import com.epam.forum.command.Router;
import com.epam.forum.exception.ErrorAttribute;
import com.epam.forum.exception.ServiceException;
import com.epam.forum.model.entity.Section;
import com.epam.forum.model.entity.Topic;
import com.epam.forum.model.service.SectionService;
import com.epam.forum.model.service.TopicService;
import com.epam.forum.validator.DigitValidator;

/**
 * The {@code ViewSectionByIdCommand} class represents view section by id command
 * 
 * @author Andrey Shcherbin
 * @version 1.0
 * @since 2021-05-30
 *
 */
public class ViewSectionByIdCommand implements Command {
	private static Logger logger = LogManager.getLogger();
	private static final String PARAM_ID_SECTION = "section_id";
	private static final String ATRIBUTE_NAME_TOPICS = "topics";
	private static final String ATRIBUTE_NAME_SECTION = "section";
	private static final String ATTRIBUTE_NAME_MESSAGE = "message";
	private static final String ATTRIBUTE_VALUE_TOPICS_EMPTY = "message.topics.empty";
	private static final String ATTRIBUTE_VALUE_SECTIONS_EMPTY = "message.sections.empty";
	private static final String ATTRIBUTE_VALUE_WRONG_INPUT = "message.wrong.input";
	private TopicService topicService;
	private SectionService sectionService;

	public ViewSectionByIdCommand(TopicService topicService, SectionService sectionService) {
		this.topicService = topicService;
		this.sectionService = sectionService;
	}

	@Override
	public Router execute(HttpServletRequest request, HttpServletResponse response) {
		Router router = new Router();
		String sectionId = request.getParameter(PARAM_ID_SECTION);
		if (sectionId == null || !DigitValidator.isValid(sectionId)) {
			request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_WRONG_INPUT);
			router.setPage(PagePath.HOME);
			return router;
		}
		Long sectionIdLong = Long.parseLong(sectionId);
		Queue<Topic> topics = new LinkedList<>();
		Optional<Section> section = Optional.empty();
		try {
			section = sectionService.findSectionById(sectionIdLong);
			topics = topicService.findTopicsBySection(sectionIdLong);
			if (!section.isEmpty()) {
				if (!topics.isEmpty()) {
					request.setAttribute(ATRIBUTE_NAME_TOPICS, topics);
				} else {
					request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_TOPICS_EMPTY);
				}
				request.setAttribute(ATRIBUTE_NAME_SECTION, section.get());
				router.setPage(PagePath.SECTION);
			} else {
				request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_SECTIONS_EMPTY);
				router.setPage(PagePath.HOME);
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
