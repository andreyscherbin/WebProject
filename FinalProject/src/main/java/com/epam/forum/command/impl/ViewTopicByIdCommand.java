package com.epam.forum.command.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.forum.command.Command;
import com.epam.forum.command.PagePath;
import com.epam.forum.command.Router;
import com.epam.forum.exception.ErrorAttribute;
import com.epam.forum.exception.ServiceException;
import com.epam.forum.model.entity.Post;
import com.epam.forum.model.entity.Topic;
import com.epam.forum.model.service.PostService;
import com.epam.forum.model.service.TopicService;
import com.epam.forum.validator.DigitValidator;

/**
 * The {@code ViewTopicByIdCommand} class represents view topic by id command
 * 
 * @author Andrey Shcherbin
 * @version 1.0
 * @since 2021-05-30
 *
 */
public class ViewTopicByIdCommand implements Command {

	private static Logger logger = LogManager.getLogger();

	private static final String PARAM_NAME_TOPIC_ID = "topic_id";

	private static final String ATTRIBUTE_NAME_CURRENT_TOPIC = "current_topic";
	private static final String ATRIBUTE_NAME_TOPIC = "topic";
	private static final String ATRIBUTE_NAME_POSTS = "posts";
	private static final String ATTRIBUTE_NAME_MESSAGE = "message";
	private static final String ATTRIBUTE_VALUE_KEY_WRONG_INPUT = "message.wrong.input";
	private static final String ATTRIBUTE_VALUE_KEY_TOPIC_EMPTY = "message.topic.empty";

	private TopicService topicService;
	private PostService postService;

	public ViewTopicByIdCommand(TopicService topicService, PostService postService) {
		this.topicService = topicService;
		this.postService = postService;
	}

	@Override
	public Router execute(HttpServletRequest request, HttpServletResponse response) {
		Router router = new Router();
		HttpSession session = request.getSession();
		String topicId = request.getParameter(PARAM_NAME_TOPIC_ID);
		if (topicId == null || !DigitValidator.isValid(topicId)) {
			request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_KEY_WRONG_INPUT);
			router.setPage(PagePath.SECTION);
			return router;
		}
		long id = Integer.parseInt(topicId);
		Optional<Topic> topic = Optional.empty();
		List<Post> posts = new ArrayList<>();
		try {
			topic = topicService.findTopicById(id);
			posts = postService.findPostsByTopic(id);
			if (!topic.isEmpty()) {
				if (!posts.isEmpty()) {					
					request.setAttribute(ATRIBUTE_NAME_TOPIC, topic.get());
					request.setAttribute(ATRIBUTE_NAME_POSTS, posts);
					session.setAttribute(ATTRIBUTE_NAME_CURRENT_TOPIC, topicId);
					router.setPage(PagePath.TOPIC);
				} else {
					request.setAttribute(ATRIBUTE_NAME_TOPIC, topic.get());
					session.setAttribute(ATTRIBUTE_NAME_CURRENT_TOPIC, topicId);
					router.setPage(PagePath.TOPIC);
				}
			} else {
				request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_KEY_TOPIC_EMPTY);
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
