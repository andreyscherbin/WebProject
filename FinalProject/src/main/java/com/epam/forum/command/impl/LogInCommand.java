package com.epam.forum.command.impl;

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
import com.epam.forum.model.service.UserService;
import com.epam.forum.validator.UserValidator;

/**
 * The {@code LogInCommand} class represents authentication command
 * 
 * @author Andrey Shcherbin
 * @version 1.0
 * @since 2021-05-30
 *
 */
public class LogInCommand implements Command {
	private static Logger logger = LogManager.getLogger();
	private static final String DEFAULT_ROLE = "GUEST";
	private static final String PARAM_NAME_LOGIN = "username";
	private static final String PARAM_NAME_PASSWORD = "password";
	private static final String ATTRIBUTE_NAME_ROLE = "role";
	private static final String ATTRIBUTE_NAME_STATUS = "status";
	private static final String ATTRIBUTE_NAME_USERNAME = "username";
	private static final String ATTRIBUTE_NAME_MESSAGE = "message";
	private static final String ATTRIBUTE_VALUE_WRONG_INPUT = "message.wrong.input";
	private static final String ATTRIBUTE_VALUE_ERROR_AUTHENTICATION = "message.error.authentication";
	private static final String ATTRIBUTE_VALUE_ERROR_ALREADY_AUTHENTICATED = "message.error.already_authenticated";
	private UserService userService;

	public LogInCommand(UserService userService) {
		this.userService = userService;
	}

	@Override
	public Router execute(HttpServletRequest request, HttpServletResponse response) {
		Router router = new Router();
		HttpSession session = request.getSession();
		String userName = request.getParameter(PARAM_NAME_LOGIN);
		String password = request.getParameter(PARAM_NAME_PASSWORD);
		String stringRole = (String) session.getAttribute(ATTRIBUTE_NAME_ROLE);
		if (stringRole == null) {
			stringRole = DEFAULT_ROLE;
		}
		if (userName == null || password == null || !UserValidator.isUserNameValid(userName)
				|| !UserValidator.isPasswordValid(password)) {
			request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_WRONG_INPUT);
			router.setPage(PagePath.HOME);
			return router;
		}
		try {
			if (Role.valueOf(stringRole) == Role.GUEST) {
				Optional<User> authenticatedUser = userService.authenticate(userName, password);
				if (!authenticatedUser.isEmpty()) {
					userService.updateLastLoginDate(authenticatedUser.get());
					User user = authenticatedUser.get();
					Role role = user.getRole();
					Boolean status = user.isActive();
					session.setAttribute(ATTRIBUTE_NAME_ROLE, role.toString());
					session.setAttribute(ATTRIBUTE_NAME_USERNAME, userName);
					session.setAttribute(ATTRIBUTE_NAME_STATUS, status);
					if (role == Role.ADMIN) {
						router.setPage(PagePath.ADMIN_HOME_REDIRECT);
					} else {
						router.setPage(PagePath.HOME_REDIRECT);
					}
					router.setRedirect();
				} else {
					request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_ERROR_AUTHENTICATION);
					router.setPage(PagePath.LOGIN);
				}
			} else {
				request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_ERROR_ALREADY_AUTHENTICATED);
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