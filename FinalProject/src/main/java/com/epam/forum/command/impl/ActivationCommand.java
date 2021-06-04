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
import com.epam.forum.model.entity.ActivationCode;
import com.epam.forum.model.entity.Role;
import com.epam.forum.model.entity.User;
import com.epam.forum.model.service.ActivationSenderService;
import com.epam.forum.model.service.UserService;
import com.epam.forum.validator.ActivationCodeValidator;
import com.epam.forum.validator.UserValidator;

/**
 * The {@code ActivationCommand} class represents activation of a user command
 * 
 * @author Andrey Shcherbin
 * @version 1.0
 * @since 2021-05-30
 *
 */
public class ActivationCommand implements Command {
	
	private static Logger logger = LogManager.getLogger();
	
	private static final String PARAM_NAME_USERNAME = "username";
	private static final String PARAM_NAME_CODE = "code";
	
	private static final String ATTRIBUTE_NAME_MESSAGE = "message";
	private static final String ATTRIBUTE_VALUE_WRONG_INPUT = "message.wrong.input";
	private static final String ATTRIBUTE_VALUE_FAILED_ACTIVATION = "message.error.activation";
	private static final String ATTRIBUTE_VALUE_SUCCESS_ACTIVATION = "message.success.activation";
	private static final String ATTRIBUTE_VALUE_ACTIVATION_CODE_EMPTY = "message.activation_code.empty";	

	private UserService userService;
	private ActivationSenderService activationSenderService;

	public ActivationCommand(UserService userService, ActivationSenderService activationSenderService) {
		this.userService = userService;
		this.activationSenderService = activationSenderService;
	}

	@Override
	public Router execute(HttpServletRequest request, HttpServletResponse response) {
		Router router = new Router();
		String username = request.getParameter(PARAM_NAME_USERNAME);
		String activationCodeId = request.getParameter(PARAM_NAME_CODE);
		if (username == null || activationCodeId == null || !UserValidator.isUserNameValid(username)
				|| !ActivationCodeValidator.isActivationCodeValid(activationCodeId)) {
			request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_WRONG_INPUT);
			router.setPage(PagePath.HOME);
			return router;
		}
		Optional<ActivationCode> activationCode;
		try {
			activationCode = activationSenderService.findActivationCodeById(activationCodeId);
			if (!activationCode.isEmpty()) {
				ActivationCode code = activationCode.get();
				if (!isActivationRequestInvalid(code, username)) {
					activateUser(code);
					deleteActivationCode(code);
					request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_SUCCESS_ACTIVATION);
				} else {
					request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_FAILED_ACTIVATION);
				}
			} else {
				request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_ACTIVATION_CODE_EMPTY);
			}
			router.setPage(PagePath.HOME);
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

	private boolean isActivationRequestInvalid(ActivationCode activationCode, String username) {
		return activationCode.getUser() == null || !activationCode.getUser().getUserName().equals(username);
	}

	private void activateUser(ActivationCode activationCode) throws ServiceException {
		User user = activationCode.getUser();
		user.setRole(Role.USER);
		user.setEmailVerifed(true);
		userService.save(user);
	}

	private void deleteActivationCode(ActivationCode activationCode) throws ServiceException {
		activationSenderService.delete(activationCode);
	}
}
