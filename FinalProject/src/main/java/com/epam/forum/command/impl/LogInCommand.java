package com.epam.forum.command.impl;

import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.epam.forum.command.Command;
import com.epam.forum.command.PagePath;
import com.epam.forum.model.service.UserService;
import com.epam.forum.resource.MessageManager;

public class LogInCommand implements Command {
	private static Logger logger = LogManager.getLogger();
	private static final String PARAM_NAME_LOGIN = "login";
	private static final String PARAM_NAME_PASSWORD = "password";
	private static final String ATRIBUTE_NAME_USER = "user";
	private static final String ATRIBUTE_NAME_ERROR_LOGIN = "errorLogin";
	UserService userService;

	public LogInCommand(UserService userService) {
		this.userService = userService;
	}

	@Override
	public String execute(HttpServletRequest request) {
		String page = null;
		String login = request.getParameter(PARAM_NAME_LOGIN);
		String pass = request.getParameter(PARAM_NAME_PASSWORD);
		if (userService.checkLogin(login, pass)) {
			request.setAttribute(ATRIBUTE_NAME_USER, login);
			page = PagePath.MAIN;
		} else {
			request.setAttribute(ATRIBUTE_NAME_ERROR_LOGIN, MessageManager.getProperty("message.errorlogin"));
			page = PagePath.LOGIN;
		}
		return page;
	}
}