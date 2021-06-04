package com.epam.forum.command.impl;

import java.time.LocalDateTime;
import java.util.List;
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
import com.epam.forum.model.entity.Section;
import com.epam.forum.model.entity.Topic;
import com.epam.forum.model.entity.User;
import com.epam.forum.model.service.SectionService;
import com.epam.forum.model.service.TopicService;
import com.epam.forum.model.service.UserService;
import com.epam.forum.validator.DigitValidator;
import com.epam.forum.validator.TopicValidator;
import com.epam.forum.validator.UserValidator;

/**
 * The {@code CreateTopicCommand} class represents create topic command
 * 
 * @author Andrey Shcherbin
 * @version 1.0
 * @since 2021-05-30
 *
 */
public class CreateTopicCommand implements Command {
	private static Logger logger = LogManager.getLogger();

	private static final String PARAM_NAME_CONTENT = "content";
	private static final String PARAM_NAME_HEADER = "header";

	private static final String ATTRIBUTE_NAME_CURRENT_SECTION = "current_section";
	private static final String ATTRIBUTE_NAME_STATUS = "status";
	private static final String ATTRIBUTE_NAME_USERNAME = "username";
	private static final String ATTRIBUTE_NAME_MESSAGE = "message";
	private static final String ATTRIBUTE_VALUE_WRONG_INPUT = "message.wrong.input";
	private static final String ATTRIBUTE_VALUE_KEY_USERS_EMPTY = "message.users.empty";
	private static final String ATTRIBUTE_VALUE_SECTION_EMPTY = "message.section.empty";
	private static final String ATTRIBUTE_VALUE_KEY_USER_BANNED = "message.user.banned";

	private UserService userService;
	private TopicService topicService;
	private SectionService sectionService;

	public CreateTopicCommand(UserService userService, TopicService topicService, SectionService sectionService) {
		this.userService = userService;
		this.topicService = topicService;
		this.sectionService = sectionService;
	}

	@Override
	public Router execute(HttpServletRequest request, HttpServletResponse response) {
		Router router = new Router();
		String content = request.getParameter(PARAM_NAME_CONTENT);
		String header = request.getParameter(PARAM_NAME_HEADER);
		String username = (String) request.getSession().getAttribute(ATTRIBUTE_NAME_USERNAME);
		Boolean status = (Boolean) request.getSession().getAttribute(ATTRIBUTE_NAME_STATUS);
		String currentSection = (String) request.getSession().getAttribute(ATTRIBUTE_NAME_CURRENT_SECTION);
		if (content == null || currentSection == null || username == null || header == null || status == null
				|| !DigitValidator.isValid(currentSection) || !TopicValidator.isContentValid(content)
				|| !TopicValidator.isHeaderValid(header) || !UserValidator.isUserNameValid(username)) {
			request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_WRONG_INPUT);
			router.setPage(PagePath.SECTION);
			return router;
		}
		long sectionId = Integer.parseInt(currentSection);
		List<User> users;
		Optional<Section> section = Optional.empty();
		try {
			if (status) {
				users = userService.findUsersByUserName(username);
				section = sectionService.findSectionById(sectionId);
				if (!users.isEmpty()) {
					if (!section.isEmpty()) {
						User user = users.get(0);
						Topic topic = new Topic();
						topic.setHeader(header);
						topic.setContent(content);
						topic.setClosed(false);
						topic.setPinned(false);
						topic.setCreationDate(LocalDateTime.now());
						topic.setUser(user);
						topic.setSection(section.get());
						topicService.create(topic);
						router.setRedirect();
						router.setPage(PagePath.SECTION_REDIRECT);
					} else {
						request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_SECTION_EMPTY);
						router.setPage(PagePath.SECTION);
					}
				} else {
					request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_KEY_USERS_EMPTY);
					router.setPage(PagePath.SECTION);
				}
			} else {
				request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_KEY_USER_BANNED);
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
