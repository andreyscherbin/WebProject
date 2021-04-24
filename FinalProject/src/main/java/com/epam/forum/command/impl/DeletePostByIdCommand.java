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
import com.epam.forum.model.entity.User;
import com.epam.forum.model.service.PostService;
import com.epam.forum.validator.DigitValidator;

public class DeletePostByIdCommand implements Command {
	private static Logger logger = LogManager.getLogger();
	private static final String PARAM_NAME_POST_ID = "post_id";
	private static final String ATTRIBUTE_NAME_MESSAGE = "message";
	private static final String ATTRIBUTE_VALUE_KEY_WRONG_INPUT = "message.wrong.input";
	private static final String ATTRIBUTE_VALUE_KEY_EMPTY_POST = "message.empty.post";
	private static final String ATTRIBUTE_NAME_USERNAME = "username";

	private PostService postService;

	public DeletePostByIdCommand(PostService postService) {
		this.postService = postService;
	}

	@Override
	public Router execute(HttpServletRequest request, HttpServletResponse response) {
		Router router = new Router();
		String id = request.getParameter(PARAM_NAME_POST_ID);
		String username = (String) request.getSession().getAttribute(ATTRIBUTE_NAME_USERNAME);
		if (username == null || id == null || !DigitValidator.isValid(id)) {
			request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_KEY_WRONG_INPUT);
			router.setPage(PagePath.TOPIC);
			return router;
		}
		Long postId = Long.parseLong(id);
		Optional<Post> post = Optional.empty();
		try {
			post = postService.findPostById(postId);
			if (!post.isEmpty()) {
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
				request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_KEY_EMPTY_POST);
				router.setPage(PagePath.TOPIC);
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
