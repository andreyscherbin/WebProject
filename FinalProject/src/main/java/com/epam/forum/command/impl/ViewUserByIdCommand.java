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
import com.epam.forum.exception.ServiceException;
import com.epam.forum.model.entity.User;
import com.epam.forum.model.service.UserService;
import com.epam.forum.resource.MessageManager;
import com.epam.forum.validator.DigitValidator;

public class ViewUserByIdCommand implements Command {
	private static Logger logger = LogManager.getLogger();
	private static final String PARAM_NAME_ID = "id";
	private static final String ATRIBUTE_NAME_USERS = "users";
	private static final String ATRIBUTE_NAME_EMPTY_USER = "empty_user";
	private UserService userService;

	public ViewUserByIdCommand(UserService userService) {
		this.userService = userService;
	}

	@Override
	public Router execute(HttpServletRequest request, HttpServletResponse response) {
		Router router = new Router();
		String idString = request.getParameter(PARAM_NAME_ID);
		if (!DigitValidator.isDigit(idString)) {
			request.setAttribute("wrongInput", MessageManager.getProperty("message.wronginput"));
			router.setPage(PagePath.MAIN);
			return router;
		}
		long id = Integer.parseInt(idString);
		Optional<User> user = Optional.empty();
		try {
			user = userService.findUserById(id);
			if (!user.isEmpty()) {
				List<User> users = new ArrayList<>();
				users.add(user.get());
				request.setAttribute(ATRIBUTE_NAME_USERS, users);
				router.setPage(PagePath.VIEW);
			} else {
				request.setAttribute(ATRIBUTE_NAME_EMPTY_USER, MessageManager.getProperty("message.emptyuser"));
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
