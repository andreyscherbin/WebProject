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
import com.epam.forum.exception.ServiceException;
import com.epam.forum.model.entity.Post;
import com.epam.forum.model.entity.Topic;
import com.epam.forum.model.entity.User;
import com.epam.forum.model.service.PostService;
import com.epam.forum.model.service.TopicService;
import com.epam.forum.model.service.UserService;
import com.epam.forum.validator.DigitValidator;
import com.epam.forum.validator.LatinCyrillicDigitValidator;

public class CreatePostCommand implements Command {

	private static Logger logger = LogManager.getLogger();
	private static final String PARAM_NAME_TOPIC_ID = "topic_id";
	private static final String PARAM_NAME_CONTENT = "content";
	private static final String ATTRIBUTE_NAME_USERNAME = "username";
	private static final String ATTRIBUTE_NAME_MESSAGE = "message";
	private static final String ATTRIBUTE_NAME_TOPIC_ID = "topic_id";
	private static final String ATTRIBUTE_VALUE_WRONG_INPUT = "message.wrong.input";
	private static final String ATTRIBUTE_VALUE_EMPTY_USERS = "message.empty.users";
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
		if (!DigitValidator.isValid(id) || !LatinCyrillicDigitValidator.isValid(content)) {
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
			if (!users.isEmpty() && !topic.isEmpty()) {
				User user = users.get(0);
				Post post = new Post();
				post.setCreationDate(LocalDateTime.now());
				post.setContent(content);
				post.setUser(user);
				post.setTopic(topic.get());
				postService.create(post);
				request.setAttribute(ATTRIBUTE_NAME_TOPIC_ID, id);
				router.setPage(PagePath.TOPIC);
			} else {
				request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_EMPTY_USERS);
				router.setPage(PagePath.TOPIC);
			}
		} catch (ServiceException e) {
			logger.error("service exception {}", e);
			router.setPage(PagePath.ERROR);
			router.setRedirect();
		}
		return router;
	}
}
