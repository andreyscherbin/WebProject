package com.epam.forum.command.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.epam.forum.command.Command;
import com.epam.forum.command.PagePath;
import com.epam.forum.command.Router;
import com.epam.forum.exception.ErrorTable;
import com.epam.forum.exception.ServiceException;
import com.epam.forum.model.entity.User;
import com.epam.forum.model.entity.comparator.UserComparator;
import com.epam.forum.model.service.UserService;

public class SortUserByIdCommand implements Command {
	private static Logger logger = LogManager.getLogger();
	private static final String ATRIBUTE_NAME_USERS = "users";
	private static final String ATTRIBUTE_NAME_MESSAGE = "message";
	private static final String ATTRIBUTE_VALUE_KEY = "message.empty_users";
	private UserService userService;

	public SortUserByIdCommand(UserService userService) {
		this.userService = userService;
	}

	@Override
	public Router execute(HttpServletRequest request, HttpServletResponse response) {
		Router router = new Router();
		List<User> users;
		try {
			users = userService.sort(UserComparator.ID);
			if (!users.isEmpty()) {
				request.setAttribute(ATRIBUTE_NAME_USERS, users);
				router.setPage(PagePath.ADMIN_HOME);
			} else {
				request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_KEY);
				router.setPage(PagePath.ADMIN_HOME);
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
