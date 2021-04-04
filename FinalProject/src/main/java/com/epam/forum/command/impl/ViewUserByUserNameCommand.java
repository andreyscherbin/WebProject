package com.epam.forum.command.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.epam.forum.command.Command;
import com.epam.forum.command.PagePath;
import com.epam.forum.command.Router;
import com.epam.forum.exception.ServiceException;
import com.epam.forum.model.entity.User;
import com.epam.forum.model.service.UserService;
import com.epam.forum.resource.MessageManager;

public class ViewUserByUserNameCommand implements Command {

	private static Logger logger = LogManager.getLogger();
	private static final String PARAM_NAME_USERNAME = "user_name";
	private static final String ATRIBUTE_NAME_USERS = "users";
	private static final String ATRIBUTE_NAME_EMPTY_USERS = "empty_users";
	private UserService userService;

	public ViewUserByUserNameCommand(UserService userService) {
		this.userService = userService;
	}

	@Override
	public Router execute(HttpServletRequest request) {
		Router router = new Router();
		String userName = request.getParameter(PARAM_NAME_USERNAME);
		List<User> users;
		try {
			users = userService.findUsersByUserName(userName);
			if (!users.isEmpty()) {
				request.setAttribute(ATRIBUTE_NAME_USERS, users);
				router.setPage(PagePath.VIEW);
			} else {
				request.setAttribute(ATRIBUTE_NAME_EMPTY_USERS, MessageManager.getProperty("message.emptyusers"));
				router.setPage(PagePath.VIEW);
			}
		} catch (ServiceException e) {
			logger.error("service exception {}", e);
			router.setPage(PagePath.ERROR);
			router.setRedirect();
		}
		return router;
	}
}
