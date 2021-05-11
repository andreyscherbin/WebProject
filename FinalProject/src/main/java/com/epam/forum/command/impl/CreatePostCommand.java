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
import com.epam.forum.exception.EntityException;
import com.epam.forum.exception.ErrorTable;
import com.epam.forum.exception.ServiceException;
import com.epam.forum.model.entity.Post;
import com.epam.forum.model.entity.Topic;
import com.epam.forum.model.entity.User;
import com.epam.forum.model.service.PostService;
import com.epam.forum.model.service.TopicService;
import com.epam.forum.model.service.UserService;
import com.epam.forum.template.factorymethod.PostCreator;
import com.epam.forum.validator.DigitValidator;
import com.epam.forum.validator.PostValidator;

public class CreatePostCommand implements Command {

	private static Logger logger = LogManager.getLogger();
	private static final String PARAM_NAME_TOPIC_ID = "topic_id";
	private static final String PARAM_NAME_CONTENT = "content";
	private static final String ATTRIBUTE_NAME_USERNAME = "username";
	private static final String ATTRIBUTE_NAME_MESSAGE = "message";
	private static final String ATTRIBUTE_VALUE_WRONG_INPUT = "message.wrong.input";
	private static final String ATTRIBUTE_VALUE_KEY_TOPIC_EMPTY = "message.topic.empty";
	private static final String ATTRIBUTE_VALUE_KEY_TOPIC_CLOSED = "message.topic.closed";
	private static final String ATTRIBUTE_VALUE_KEY_USER_EMPTY = "message.user.empty";

	private UserService userService;
	private TopicService topicService;
	private PostService postService;

	public CreatePostCommand(UserService userService, TopicService topicService, PostService postService) {
		this.userService = userService;
		this.topicService = topicService;
		this.postService = postService;
	}

	@Override
	public Router execute(HttpServletRequest request, HttpServletResponse response) {
		Router router = new Router();
		String id = request.getParameter(PARAM_NAME_TOPIC_ID);
		String content = request.getParameter(PARAM_NAME_CONTENT);
		String username = (String) request.getSession().getAttribute(ATTRIBUTE_NAME_USERNAME);
		if (content == null || id == null || username == null || !DigitValidator.isValid(id)
				|| !PostValidator.isContentValid(content)) {
			request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_WRONG_INPUT);
			router.setPage(PagePath.TOPIC);
			return router;
		}
		long topicId = Integer.parseInt(id);
		List<User> users;
		Optional<Topic> topic = Optional.empty();
		try {
			users = userService.findUsersByUserName(username);
			topic = topicService.findTopicById(topicId);
			if (!users.isEmpty()) {
				if (!topic.isEmpty()) {
					if (!topic.get().isClosed()) {
						User user = users.get(0);
						Post post = PostCreator.getPostFromFactoryMethod(LocalDateTime.now(), content, user,
								topic.get());
						postService.create(post);
						router.setPage(PagePath.TOPIC);
					} else {
						request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_KEY_TOPIC_CLOSED);
						router.setPage(PagePath.TOPIC);
					}
				} else {
					request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_KEY_TOPIC_EMPTY);
					router.setPage(PagePath.TOPIC);
				}
			} else {
				request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_KEY_USER_EMPTY);
				router.setPage(PagePath.TOPIC);
			}
		} catch (ServiceException | EntityException e) {
			logger.error("exception ", e);
			request.setAttribute(ErrorTable.ERROR_MESSAGE, e.getMessage());
			request.setAttribute(ErrorTable.ERROR_CAUSE, e.getCause());
			request.setAttribute(ErrorTable.ERROR_LOCATION, request.getRequestURI());
			request.setAttribute(ErrorTable.ERROR_CODE, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			router.setPage(PagePath.ERROR);
		}
		return router;
	}
}
