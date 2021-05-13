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
import com.epam.forum.exception.ErrorTable;
import com.epam.forum.exception.ServiceException;
import com.epam.forum.model.entity.Role;
import com.epam.forum.model.entity.User;
import com.epam.forum.model.service.UserService;
import com.epam.forum.validator.DigitValidator;
import com.epam.forum.validator.UserValidator;

public class ChangeRoleCommand implements Command {
	private static Logger logger = LogManager.getLogger();
	private static final String PARAM_NAME_ID = "user_id";
	private static final String PARAM_NAME_ROLES = "role";
	private static final String ATTRIBUTE_NAME_MESSAGE = "message";
	private static final String ATTRIBUTE_VALUE_KEY_WRONG_INPUT = "message.wrong.input";
	private static final String ATTRIBUTE_VALUE_KEY_USER_EMPTY = "message.user.empty";
	private UserService userService;

	public ChangeRoleCommand(UserService userService) {
		this.userService = userService;
	}

	@Override
	public Router execute(HttpServletRequest request, HttpServletResponse response) {
		Router router = new Router();
		String idString = request.getParameter(PARAM_NAME_ID);
		String roleString = request.getParameter(PARAM_NAME_ROLES);
		if (idString == null || roleString == null || !DigitValidator.isValid(idString) || !UserValidator.isRoleValid(roleString)) {
			request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_KEY_WRONG_INPUT);
			router.setPage(PagePath.ADMIN_HOME);
			return router;
		}
		Role role = Role.valueOf(roleString); 
		long id = Integer.parseInt(idString);
		Optional<User> user = Optional.empty();
		try {
			user = userService.findUserById(id);
			if (!user.isEmpty()) {
				user.get().setRole(role);
				userService.changeRole(user.get());
				router.setPage(PagePath.ADMIN_HOME);
			} else {
				request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_KEY_USER_EMPTY);
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
