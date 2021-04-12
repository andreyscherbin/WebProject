package com.epam.forum.command.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.epam.forum.command.Command;
import com.epam.forum.command.PagePath;
import com.epam.forum.command.Router;
import com.epam.forum.exception.ServiceException;
import com.epam.forum.model.entity.Role;
import com.epam.forum.model.entity.User;
import com.epam.forum.model.service.UserService;
import com.epam.forum.resource.MessageManager;

public class LogInCommand implements Command {
	private static Logger logger = LogManager.getLogger();
	private static final String PARAM_NAME_LOGIN = "username";
	private static final String PARAM_NAME_PASSWORD = "password";
	private static final String ATRIBUTE_NAME_ROLE = "role";
	private static final String ATRIBUTE_NAME_ERROR_AUTHENTICATION = "error_authentication";
	private UserService userService;

	public LogInCommand(UserService userService) {
		this.userService = userService;
	}

	@Override
	public Router execute(HttpServletRequest request, HttpServletResponse response) {
		Router router = new Router();
		String userName = request.getParameter(PARAM_NAME_LOGIN);
		String pass = request.getParameter(PARAM_NAME_PASSWORD);
		try {
			List<User> users = userService.authenticate(userName, pass);
			if (!users.isEmpty()) {
				User user = users.get(0);
				Role role = user.getRole();				
				request.getSession().setAttribute(ATRIBUTE_NAME_ROLE, role);
				router.setPage(PagePath.HOME);
				router.setRedirect();
			} else {
				request.setAttribute(ATRIBUTE_NAME_ERROR_AUTHENTICATION,
						MessageManager.getProperty("message.error_authentication"));
				router.setPage(PagePath.LOGIN);
			}
		} catch (ServiceException e) {
			logger.error("service exception {}", e);
			router.setPage(PagePath.ERROR);
			router.setRedirect();
		}
		return router;
	}
}