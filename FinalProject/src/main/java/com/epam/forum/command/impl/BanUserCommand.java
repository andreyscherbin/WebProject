package com.epam.forum.command.impl;

import java.util.Optional;
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
import com.epam.forum.validator.DigitValidator;

/**
 * The {@code BanUserCommand} class represents ban/unban of a user command
 * 
 * @author Andrey Shcherbin
 * @version 1.0
 * @since 2021-05-30
 *
 */
public class BanUserCommand implements Command {
	private static Logger logger = LogManager.getLogger();
	private static final String PARAM_NAME_ID = "user_id";
	private static final String ATTRIBUTE_NAME_MESSAGE = "message";
	private static final String ATTRIBUTE_VALUE_KEY_WRONG_INPUT = "message.wrong.input";
	private static final String ATTRIBUTE_VALUE_KEY_USER_EMPTY = "message.user.empty";
	private UserService userService;

	public BanUserCommand(UserService userService) {
		this.userService = userService;
	}

	@Override
	public Router execute(HttpServletRequest request, HttpServletResponse response) {
		Router router = new Router();
		String idString = request.getParameter(PARAM_NAME_ID);
		if (idString == null || !DigitValidator.isValid(idString)) {
			request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_KEY_WRONG_INPUT);
			router.setPage(PagePath.ADMIN_HOME);
			return router;
		}
		long id = Integer.parseInt(idString);
		Optional<User> user = Optional.empty();
		try {
			user = userService.findUserById(id);
			if (!user.isEmpty()) {
				if (!user.get().isActive()) {
					user.get().setActive(true);
					userService.banUser(user.get());
				} else {
					user.get().setActive(false);
					userService.unbanUser(user.get());
				}
				router.setPage(PagePath.ADMIN_HOME);
			} else {
				request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_KEY_USER_EMPTY);
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
