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
import com.epam.forum.exception.ServiceException;
import com.epam.forum.model.entity.Post;
import com.epam.forum.model.entity.Topic;
import com.epam.forum.model.service.PostService;
import com.epam.forum.model.service.TopicService;
import com.epam.forum.validator.DigitValidator;

public class ViewTopicByIdCommand implements Command {

	private static Logger logger = LogManager.getLogger();
	private static final String PARAM_NAME_ID = "topic_id";
	private static final String ATRIBUTE_NAME_TOPIC = "topic";
	private static final String ATRIBUTE_NAME_POSTS = "posts";
	private static final String ATTRIBUTE_NAME_MESSAGE = "message";
	private static final String ATTRIBUTE_VALUE_KEY_WRONG_INPUT = "message.wrong.input";
	private static final String ATTRIBUTE_VALUE_KEY_EMPTY_TOPIC = "message.empty.topic";
	private TopicService topicService;
	private PostService postService;

	public ViewTopicByIdCommand(TopicService topicService, PostService postService) {
		this.topicService = topicService;
		this.postService = postService;
	}

	@Override
	public Router execute(HttpServletRequest request, HttpServletResponse response) {
		Router router = new Router();
		String idString = request.getParameter(PARAM_NAME_ID);
		if (!DigitValidator.isValid(idString)) {
			request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_KEY_WRONG_INPUT);
			router.setPage(PagePath.SECTION);
			return router;
		}
		long id = Integer.parseInt(idString);
		Optional<Topic> topic = Optional.empty();
		List<Post> posts = new ArrayList<>();
		try {
			topic = topicService.findTopicById(id);
			posts = postService.findPostsByTopic(id);
			if (!topic.isEmpty()) {
				request.setAttribute(ATRIBUTE_NAME_TOPIC, topic.get());
				request.setAttribute(ATRIBUTE_NAME_POSTS, posts);
				router.setPage(PagePath.TOPIC);
			} else {
				request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_KEY_EMPTY_TOPIC);
				router.setPage(PagePath.SECTION);
			}
		} catch (ServiceException e) {
			logger.error("service exception {}", e);
			router.setPage(PagePath.ERROR);
			router.setRedirect();
		}
		return router;
	}
}
