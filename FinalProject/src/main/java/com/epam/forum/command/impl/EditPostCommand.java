package com.epam.forum.command.impl;

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
import com.epam.forum.model.entity.Post;
import com.epam.forum.model.entity.Topic;
import com.epam.forum.model.entity.User;
import com.epam.forum.model.service.PostService;
import com.epam.forum.validator.DigitValidator;
import com.epam.forum.validator.PostValidator;

public class EditPostCommand implements Command {
	private static Logger logger = LogManager.getLogger();
	private static final String PARAM_NAME_POST_ID = "post_id";
	private static final String PARAM_NAME_CONTENT = "content";
	private static final String ATTRIBUTE_NAME_MESSAGE = "message";
	private static final String ATTRIBUTE_VALUE_KEY_WRONG_INPUT = "message.wrong.input";
	private static final String ATTRIBUTE_VALUE_KEY_EMPTY_POST = "message.empty.post";
	private static final String ATTRIBUTE_VALUE_KEY_TOPIC_CLOSED = "message.topic.closed";
	private static final String ATTRIBUTE_NAME_USERNAME = "username";
	private static final String ATTRIBUTE_NAME_STATUS = "status";
	private static final String ATTRIBUTE_VALUE_KEY_USER_BANNED = "message.user.banned";

	private PostService postService;

	public EditPostCommand(PostService postService) {
		this.postService = postService;
	}

	@Override
	public Router execute(HttpServletRequest request, HttpServletResponse response) {
		Router router = new Router();
		String id = request.getParameter(PARAM_NAME_POST_ID);
		String content = request.getParameter(PARAM_NAME_CONTENT);
		String username = (String) request.getSession().getAttribute(ATTRIBUTE_NAME_USERNAME);
		Boolean status = (Boolean) request.getSession().getAttribute(ATTRIBUTE_NAME_STATUS);
		if (username == null || id == null || content == null || status == null
				|| !DigitValidator.isValid(id) && !PostValidator.isContentValid(content)) {
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
						Post editedPost = post.get();
						User user = editedPost.getUser();
						String usernamePost = user.getUserName();
						if (usernamePost.equals(username)) {
							post.get().setContent(content);
							postService.edit(post.get());
							router.setPage(PagePath.TOPIC);
						} else {
							router.setPage(PagePath.FORBIDDEN_PAGE);
						}
					} else {
						request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_KEY_TOPIC_CLOSED);
						router.setPage(PagePath.TOPIC);
					}
				} else {
					request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_KEY_EMPTY_POST);
					router.setPage(PagePath.TOPIC);
				}
			} else {
				request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_KEY_USER_BANNED);
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
