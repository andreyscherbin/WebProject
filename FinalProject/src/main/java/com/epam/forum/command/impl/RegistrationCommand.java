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
import com.epam.forum.model.entity.User;
import com.epam.forum.model.service.ActivationSenderService;
import com.epam.forum.model.service.UserService;

public class RegistrationCommand implements Command {
	private static Logger logger = LogManager.getLogger();
	private static final String PARAM_NAME_EMAIL = "email";
	private static final String PARAM_NAME_LOGIN = "username";
	private static final String PARAM_NAME_PASSWORD = "password";
	private static final String ATTRIBUTE_NAME_MESSAGE = "message";
	private static final String ATTRIBUTE_VALUE_WRONG_INPUT = "message.wrong.input";
	private static final String ATTRIBUTE_VALUE_REGISTRATION = "message.error.registration";
	private UserService userService;
	private ActivationSenderService activationSenderService;

	public RegistrationCommand(UserService userService, ActivationSenderService activationSenderService) {
		this.userService = userService;
		this.activationSenderService = activationSenderService;
	}

	@Override
	public Router execute(HttpServletRequest request, HttpServletResponse response) {
		Router router = new Router();
		String userName = request.getParameter(PARAM_NAME_LOGIN);
		String email = request.getParameter(PARAM_NAME_EMAIL);
		String password = request.getParameter(PARAM_NAME_PASSWORD);
		if (userName == null || email == null || password == null) {
			request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_WRONG_INPUT);
			router.setPage(PagePath.HOME);
			return router;
		}
		try {			
			Optional<User> registeredUser = userService.registrate(userName, password, email);
			if (!registeredUser.isEmpty()) {
				User user = registeredUser.get();
				activationSenderService.sendActivationCode(user);
				router.setPage(PagePath.HOME_REDIRECT);
				router.setRedirect();
			} else {
				request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_REGISTRATION);
				router.setPage(PagePath.REGISTRATION);
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
