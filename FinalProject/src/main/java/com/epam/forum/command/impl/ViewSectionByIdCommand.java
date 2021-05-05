package com.epam.forum.command.impl;

import java.util.ArrayList;
import java.util.List;
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
import com.epam.forum.model.entity.Section;
import com.epam.forum.model.entity.Topic;
import com.epam.forum.model.service.SectionService;
import com.epam.forum.model.service.TopicService;
import com.epam.forum.validator.DigitValidator;

public class ViewSectionByIdCommand implements Command {
	private static Logger logger = LogManager.getLogger();
	private static final String PARAM_ID_SECTION = "section_id";
	private static final String ATRIBUTE_NAME_TOPICS = "topics";
	private static final String ATRIBUTE_NAME_SECTION = "section";
	private static final String ATTRIBUTE_NAME_MESSAGE = "message";
	private static final String ATTRIBUTE_VALUE_EMPTY_TOPICS = "message.empty.topics";
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
		List<Topic> topics = new ArrayList<>();
		Optional<Section> section = Optional.empty();
		try {
			section = sectionService.findSectionById(sectionIdLong);
			topics = topicService.findTopicsBySection(sectionIdLong);
			if (!section.isEmpty()) {
				if (!topics.isEmpty()) {
					request.setAttribute(ATRIBUTE_NAME_TOPICS, topics);
				} else {
					request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_EMPTY_TOPICS);
				}
				request.setAttribute(ATRIBUTE_NAME_SECTION, section.get());
				router.setPage(PagePath.SECTION);
			} else {
				request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_WRONG_INPUT);
				router.setPage(PagePath.HOME);
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
