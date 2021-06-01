package com.epam.forum.command.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.epam.forum.command.Command;
import com.epam.forum.command.CommandName;
import com.epam.forum.command.PagePath;
import com.epam.forum.command.Router;
import com.epam.forum.exception.ErrorAttribute;
import com.epam.forum.exception.ServiceException;
import com.epam.forum.model.entity.Role;
import com.epam.forum.model.entity.User;
import com.epam.forum.model.service.ActivationSenderService;
import com.epam.forum.model.service.UserService;
import com.epam.forum.validator.UserValidator;

/**
 * The {@code RegistrationCommand} class represents registration command
 * 
 * @author Andrey Shcherbin
 * @version 1.0
 * @since 2021-05-30
 *
 */
public class RegistrationCommand implements Command {
	private static Logger logger = LogManager.getLogger();
	private static final String DEFAULT_ROLE = "GUEST";
	private static final String PARAM_NAME_EMAIL = "email";
	private static final String PARAM_NAME_LOGIN = "username";
	private static final String PARAM_NAME_PASSWORD = "password";
	private static final String ATTRIBUTE_NAME_ROLE = "role";
	private static final String ATTRIBUTE_NAME_MESSAGE = "message";
	private static final String ATTRIBUTE_VALUE_WRONG_INPUT = "message.wrong.input";
	private static final String ATTRIBUTE_VALUE_EMAIL_ALREADY_IN_USE = "message.email.already_in_use";
	private static final String ATTRIBUTE_VALUE_USERNAME_ALREADY_IN_USE = "message.username.already_in_use";
	private static final String ATTRIBUTE_VALUE_ALREADY_REGISTRATED = "message.error.already_registered";
	private static final String ATTRIBUTE_VALUE_CONFIRM_EMAIL = "message.email.confirm_email";
	private static final Long SUCCESS_REGISTRATION = 1L;
	private static final Long EMAIL_ALREADY_IN_USE = 2L;
	private static final Long USERNAME_ALREADY_IN_USE = 3L;
	private UserService userService;
	private ActivationSenderService activationSenderService;

	public RegistrationCommand(UserService userService, ActivationSenderService activationSenderService) {
		this.userService = userService;
		this.activationSenderService = activationSenderService;
	}

	@Override
	public Router execute(HttpServletRequest request, HttpServletResponse response) {
		Router router = new Router();
		HttpSession session = request.getSession();
		String stringRole = (String) session.getAttribute(ATTRIBUTE_NAME_ROLE);
		if (stringRole == null) {
			stringRole = DEFAULT_ROLE;
		}
		String userName = request.getParameter(PARAM_NAME_LOGIN);
		String email = request.getParameter(PARAM_NAME_EMAIL);
		String password = request.getParameter(PARAM_NAME_PASSWORD);
		if (userName == null || email == null || password == null || !UserValidator.isUserNameValid(userName)
				|| !UserValidator.isPasswordValid(password) || !UserValidator.isEmailValid(email)) {
			request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_WRONG_INPUT);
			router.setPage(PagePath.HOME);
			return router;
		}
		try {
			if (Role.valueOf(stringRole) == Role.GUEST) {
				Map<Long, List<Optional<User>>> result = userService.registrate(userName, password, email);
				if (result.containsKey(SUCCESS_REGISTRATION)) {
					User user = result.get(SUCCESS_REGISTRATION).get(0).get();
					activationSenderService.sendActivationCode(user);
					session.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_CONFIRM_EMAIL);
					router.setPage(PagePath.HOME_REDIRECT);
					router.setRedirect();
				} else {
					if (!result.get(EMAIL_ALREADY_IN_USE).isEmpty()) {
						request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_EMAIL_ALREADY_IN_USE);
					}
					if (!result.get(USERNAME_ALREADY_IN_USE).isEmpty()) {
						request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_USERNAME_ALREADY_IN_USE);
					}
					router.setPage(PagePath.REGISTRATION);
				}
			} else {
				request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_ALREADY_REGISTRATED);
				router.setPage(PagePath.HOME);
			}
		} catch (ServiceException e) {
			logger.error("service exception ", e);
			request.setAttribute(ErrorAttribute.ERROR_MESSAGE, e.getMessage());
			request.setAttribute(ErrorAttribute.ERROR_CAUSE, e.getCause());
			request.setAttribute(ErrorAttribute.ERROR_LOCATION, request.getRequestURI());
			request.setAttribute(ErrorAttribute.ERROR_CODE, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			router.setPage(PagePath.ERROR);
		} catch (IllegalArgumentException e) {
			logger.error("no such role {}", stringRole);
			throw new EnumConstantNotPresentException(CommandName.class, stringRole);
		}
		return router;
	}
}
