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
import com.epam.forum.command.factory.PostFactory;
import com.epam.forum.exception.EntityException;
import com.epam.forum.exception.ErrorAttribute;
import com.epam.forum.exception.ServiceException;
import com.epam.forum.model.entity.Post;
import com.epam.forum.model.entity.Topic;
import com.epam.forum.model.entity.User;
import com.epam.forum.model.service.PostService;
import com.epam.forum.model.service.TopicService;
import com.epam.forum.model.service.UserService;
import com.epam.forum.validator.DigitValidator;
import com.epam.forum.validator.PostValidator;
import com.epam.forum.validator.UserValidator;

/**
 * The {@code CreatePostCommand} class represents create post command
 * 
 * @author Andrey Shcherbin
 * @version 1.0
 * @since 2021-05-30
 *
 */
public class CreatePostCommand implements Command {

	private static Logger logger = LogManager.getLogger();

	private static final String PARAM_NAME_CONTENT = "content";

	private static final String ATTRIBUTE_NAME_CURRENT_TOPIC = "current_topic";
	private static final String ATTRIBUTE_NAME_USERNAME = "username";
	private static final String ATTRIBUTE_NAME_STATUS = "status";
	private static final String ATTRIBUTE_NAME_MESSAGE = "message";
	private static final String ATTRIBUTE_VALUE_WRONG_INPUT = "message.wrong.input";
	private static final String ATTRIBUTE_VALUE_KEY_TOPIC_EMPTY = "message.topic.empty";
	private static final String ATTRIBUTE_VALUE_KEY_TOPIC_CLOSED = "message.topic.closed";
	private static final String ATTRIBUTE_VALUE_KEY_USER_EMPTY = "message.user.empty";
	private static final String ATTRIBUTE_VALUE_KEY_USER_BANNED = "message.user.banned";

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
		String content = request.getParameter(PARAM_NAME_CONTENT);
		String username = (String) request.getSession().getAttribute(ATTRIBUTE_NAME_USERNAME);
		Boolean status = (Boolean) request.getSession().getAttribute(ATTRIBUTE_NAME_STATUS);
		String currentTopic = (String) request.getSession().getAttribute(ATTRIBUTE_NAME_CURRENT_TOPIC);
		if (content == null || username == null || status == null || currentTopic == null
				|| !PostValidator.isContentValid(content) || !UserValidator.isUserNameValid(username)
				|| !DigitValidator.isValid(currentTopic)) {
			request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_WRONG_INPUT);
			router.setPage(PagePath.TOPIC);
			return router;
		}
		long topicId = Integer.parseInt(currentTopic);
		List<User> users;
		Optional<Topic> topic = Optional.empty();
		try {
			if (status) {
				users = userService.findUsersByUserName(username);
				topic = topicService.findTopicById(topicId);
				if (!users.isEmpty()) {
					if (!topic.isEmpty()) {
						if (!topic.get().isClosed()) {
							User user = users.get(0);
							Post post = PostFactory.getPostFromFactoryMethod(LocalDateTime.now(), content, user,
									topic.get());
							postService.create(post);
							router.setRedirect();
							router.setPage(PagePath.TOPIC_REDIRECT);
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
			} else {
				request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_KEY_USER_BANNED);
				router.setPage(PagePath.TOPIC);
			}
		} catch (ServiceException | EntityException e) {
			logger.error("exception ", e);
			request.setAttribute(ErrorAttribute.ERROR_MESSAGE, e.getMessage());
			request.setAttribute(ErrorAttribute.ERROR_CAUSE, e.getCause());
			request.setAttribute(ErrorAttribute.ERROR_LOCATION, request.getRequestURI());
			request.setAttribute(ErrorAttribute.ERROR_CODE, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			router.setPage(PagePath.ERROR);
		}
		return router;
	}
}
