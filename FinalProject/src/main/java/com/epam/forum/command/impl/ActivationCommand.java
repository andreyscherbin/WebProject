package com.epam.forum.command.impl;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.epam.forum.command.Command;
import com.epam.forum.command.PagePath;
import com.epam.forum.command.Router;
import com.epam.forum.exception.ServiceException;
import com.epam.forum.model.entity.ActivationCode;
import com.epam.forum.model.entity.Role;
import com.epam.forum.model.entity.User;
import com.epam.forum.model.service.ActivationSenderService;
import com.epam.forum.model.service.UserService;

public class ActivationCommand implements Command {
	private static Logger logger = LogManager.getLogger();
	private static final String PARAM_NAME_USERNAME = "username";
	private static final String PARAM_NAME_CODE = "code";
	private static final String ATTRIBUTE_NAME_MESSAGE = "message";
	private static final String ATTRIBUTE_VALUE_FAILED_ACTIVATION = "message.error.activation";
	private static final String ATTRIBUTE_VALUE_SUCCESS_ACTIVATION = "message.success.activation";
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
		Optional<ActivationCode> activationCode;
		try {
			activationCode = findActivationCode(activationCodeId);
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
				request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_FAILED_ACTIVATION);
			}
			router.setPage(PagePath.HOME);
		} catch (ServiceException e) {
			logger.error("service exception {}", e);
			router.setPage(PagePath.ERROR);
			router.setRedirect();
		}
		return router;
	}

	private Optional<ActivationCode> findActivationCode(String id) throws ServiceException {
		Optional<ActivationCode> activationCode = activationSenderService.findActivationCodeById(id);
		return activationCode;
	}

	private boolean isActivationRequestInvalid(ActivationCode activationCode, String username) {
		return activationCode.getUser() == null || !activationCode.getUser().getUserName().equals(username);
	}

	private void activateUser(ActivationCode activationCode) throws ServiceException {
		User user = activationCode.getUser();
		user.setRole(Role.USER);
		user.setActive(true);
		userService.save(user);
	}

	private void deleteActivationCode(ActivationCode activationCode) throws ServiceException {
		activationSenderService.delete(activationCode);
	}
}
