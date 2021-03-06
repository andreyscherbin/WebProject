package com.epam.forum.command.impl;

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
import com.epam.forum.model.entity.Post;
import com.epam.forum.model.entity.Topic;
import com.epam.forum.model.entity.User;
import com.epam.forum.model.service.PostService;
import com.epam.forum.validator.DigitValidator;
import com.epam.forum.validator.UserValidator;

/**
 * The {@code DeletePostCommand} class represents delete post command
 * 
 * @author Andrey Shcherbin
 * @version 1.0
 * @since 2021-05-30
 *
 */
public class DeletePostCommand implements Command {
	
	private static Logger logger = LogManager.getLogger();
	
	private static final String PARAM_NAME_POST_ID = "post_id";
	
	private static final String ATTRIBUTE_NAME_MESSAGE = "message";
	private static final String ATTRIBUTE_VALUE_KEY_WRONG_INPUT = "message.wrong.input";
	private static final String ATTRIBUTE_VALUE_KEY_POST_EMPTY = "message.post.empty";
	private static final String ATTRIBUTE_VALUE_KEY_TOPIC_CLOSED = "message.topic.closed";
	private static final String ATTRIBUTE_NAME_USERNAME = "username";
	private static final String ATTRIBUTE_NAME_STATUS = "status";
	private static final String ATTRIBUTE_VALUE_KEY_USER_BANNED = "message.user.banned";

	private PostService postService;

	public DeletePostCommand(PostService postService) {
		this.postService = postService;
	}

	@Override
	public Router execute(HttpServletRequest request, HttpServletResponse response) {
		Router router = new Router();
		String id = request.getParameter(PARAM_NAME_POST_ID);
		String username = (String) request.getSession().getAttribute(ATTRIBUTE_NAME_USERNAME);
		Boolean status = (Boolean) request.getSession().getAttribute(ATTRIBUTE_NAME_STATUS);
		if (username == null || id == null || status == null || !DigitValidator.isValid(id) || !UserValidator.isUserNameValid(username)) {
			request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_KEY_WRONG_INPUT);
			router.setPage(PagePath.TOPIC);
			return router;
		}
		Long postId = Long.parseLong(id);
		Optional<Post> post = Optional.empty();
		try {
			if (status) {
				post = postService.findPostById(postId);
				if (!post.isEmpty()) {
					Topic topic = post.get().getTopic();
					if (!topic.isClosed()) {
						Post deletedPost = post.get();
						User user = deletedPost.getUser();
						String usernamePost = user.getUserName();
						if (usernamePost.equals(username)) {
							postService.delete(post.get());
							router.setPage(PagePath.TOPIC);
						} else {
							router.setPage(PagePath.FORBIDDEN_PAGE);
						}
					} else {
						request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_KEY_TOPIC_CLOSED);
						router.setPage(PagePath.TOPIC);
					}
				} else {
					request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_KEY_POST_EMPTY);
					router.setPage(PagePath.TOPIC);
				}
			} else {
				request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_KEY_USER_BANNED);
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
