package com.epam.forum.command.impl;

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
import com.epam.forum.validator.DigitValidator;

public class DeletePostByIdCommand implements Command {
	private static Logger logger = LogManager.getLogger();
	private static final String PARAM_NAME_POST_ID = "post_id";
	private static final String ATTRIBUTE_NAME_MESSAGE = "message";
	private static final String ATTRIBUTE_VALUE_KEY_WRONG_INPUT = "message.wrong.input";
	private static final String ATTRIBUTE_VALUE_KEY_EMPTY_POST = "message.empty.post";
	private static final String ATTRIBUTE_VALUE_TOPIC_ID = "topic_id";
	private PostService postService;

	public DeletePostByIdCommand(PostService postService) {
		this.postService = postService;
	}

	@Override
	public Router execute(HttpServletRequest request, HttpServletResponse response) {
		Router router = new Router();
		String id = request.getParameter(PARAM_NAME_POST_ID);
		if (!DigitValidator.isValid(id)) {
			request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_KEY_WRONG_INPUT);
			router.setPage(PagePath.TOPIC);
			return router;
		}
		Long postId = Long.parseLong(id);
		Optional<Post> post = Optional.empty();
		try {
			post = postService.findPostById(postId);
			if (!post.isEmpty()) {
				Post findPost = post.get();
				Topic findTopic = findPost.getTopic();
				Long topicId = findTopic.getId();
				postService.delete(post.get());
				request.setAttribute(ATTRIBUTE_VALUE_TOPIC_ID, topicId.toString());
			} else {
				request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_KEY_EMPTY_POST);
			}
		} catch (ServiceException e) {
			logger.error("service exception {}", e);
			router.setPage(PagePath.ERROR);
			router.setRedirect();
		}
		router.setPage(PagePath.TOPIC);
		return router;
	}
}
