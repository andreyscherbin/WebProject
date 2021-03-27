package com.epam.forum.command.impl;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.epam.forum.command.Command;
import com.epam.forum.command.PagePath;
import com.epam.forum.model.entity.User;
import com.epam.forum.model.service.UserService;
import com.epam.forum.resource.MessageManager;
import com.epam.forum.validator.DigitValidator;

public class ViewUserByIdCommand implements Command {
	private static Logger logger = LogManager.getLogger();
	private static final String PARAM_NAME_ID = "id";
	private static final String ATRIBUTE_NAME_USER = "user";
	private static final String ATRIBUTE_NAME_EMPTY_USER = "empty_user";
	UserService userService;

	public ViewUserByIdCommand(UserService userService) {
		this.userService = userService;
	}

	@Override
	public String execute(HttpServletRequest request) {
		String page = null;
		String idString = request.getParameter(PARAM_NAME_ID);
		if (!DigitValidator.isDigit(idString)) {
			request.setAttribute("wrongInput", MessageManager.getProperty("message.wronginput"));
			page = PagePath.MAIN;
			return page;
		}
		long id = Integer.parseInt(idString);
		Optional<User> user = userService.getUserById(id);
		if (!user.isEmpty()) {
			request.setAttribute(ATRIBUTE_NAME_USER, user);
			page = PagePath.VIEW;
		} else {
			request.setAttribute(ATRIBUTE_NAME_EMPTY_USER, MessageManager.getProperty("message.emptyuser"));
			page = PagePath.VIEW;
		}
		return page;
	}
}
