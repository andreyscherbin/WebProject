package com.epam.forum.command.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.epam.forum.command.Command;
import com.epam.forum.command.PagePath;
import com.epam.forum.command.Router;
import com.epam.forum.exception.ErrorAttribute;
import com.epam.forum.exception.ServiceException;
import com.epam.forum.model.entity.User;
import com.epam.forum.model.service.UserService;

/**
 * The {@code ViewUserByUserNameCommand} class represents view user by username command
 * 
 * @author Andrey Shcherbin
 * @version 1.0
 * @since 2021-05-30
 *
 */
public class ViewUserByUserNameCommand implements Command {

	private static Logger logger = LogManager.getLogger();
	private static final String PARAM_NAME_USERNAME = "user_name";
	private static final String ATRIBUTE_NAME_USERS = "users";
	private static final String ATTRIBUTE_NAME_MESSAGE = "message";
	private static final String ATTRIBUTE_VALUE_KEY_WRONG_INPUT = "message.wrong.input";
	private static final String ATTRIBUTE_VALUE_KEY_USERS_EMPTY = "message.users.empty";
	private UserService userService;

	public ViewUserByUserNameCommand(UserService userService) {
		this.userService = userService;
	}

	@Override
	public Router execute(HttpServletRequest request, HttpServletResponse response) {
		Router router = new Router();
		String userName = request.getParameter(PARAM_NAME_USERNAME);
		if (userName == null) {
			request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_KEY_WRONG_INPUT);
			router.setPage(PagePath.HOME);
			return router;
		}
		List<User> users;
		try {
			users = userService.findUsersByUserName(userName);
			if (!users.isEmpty()) {
				request.setAttribute(ATRIBUTE_NAME_USERS, users);
				router.setPage(PagePath.ADMIN_HOME);
			} else {
				request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_KEY_USERS_EMPTY);
				router.setPage(PagePath.ADMIN_HOME);
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