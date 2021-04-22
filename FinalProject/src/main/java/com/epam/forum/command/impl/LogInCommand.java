package com.epam.forum.command.impl;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.epam.forum.command.Command;
import com.epam.forum.command.PagePath;
import com.epam.forum.command.Router;
import com.epam.forum.exception.ErrorTable;
import com.epam.forum.exception.ServiceException;
import com.epam.forum.model.entity.Role;
import com.epam.forum.model.entity.User;
import com.epam.forum.model.service.UserService;

public class LogInCommand implements Command {
	private static Logger logger = LogManager.getLogger();
	private static final String PARAM_NAME_LOGIN = "username";
	private static final String PARAM_NAME_PASSWORD = "password";
	private static final String ATRIBUTE_NAME_ROLE = "role";
	private static final String ATRIBUTE_NAME_USERNAME = "username";
	private static final String ATTRIBUTE_NAME_MESSAGE = "message";
	private static final String ATTRIBUTE_VALUE_ERROR_AUTHENTICATION = "message.error.authentication";
	private UserService userService;

	public LogInCommand(UserService userService) {
		this.userService = userService;
	}

	@Override
	public Router execute(HttpServletRequest request, HttpServletResponse response) {
		Router router = new Router();
		String userName = request.getParameter(PARAM_NAME_LOGIN);
		String password = request.getParameter(PARAM_NAME_PASSWORD);
		try {
			Optional<User> authenticatedUser = userService.authenticate(userName, password);
			if (!authenticatedUser.isEmpty()) {
				userService.updateLastLoginDate(authenticatedUser.get());
				HttpSession session = request.getSession();
				User user = authenticatedUser.get();
				Role role = user.getRole();
				session.setAttribute(ATRIBUTE_NAME_ROLE, role);
				session.setAttribute(ATRIBUTE_NAME_USERNAME, userName);
				router.setPage(PagePath.HOME_REDIRECT);
				router.setRedirect();
			} else {
				request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_ERROR_AUTHENTICATION);
				router.setPage(PagePath.LOGIN);
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