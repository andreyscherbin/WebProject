package com.epam.forum.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.epam.forum.command.Command;
import com.epam.forum.command.PagePath;
import com.epam.forum.command.Router;

/**
 * The {@code EmptyCommand} class represents empty command
 * 
 * @author Andrey Shcherbin
 * @version 1.0
 * @since 2021-05-30
 *
 */
public class EmptyCommand implements Command {	
	
	private static final String ATTRIBUTE_NAME_MESSAGE = "message";
	private static final String ATTRIBUTE_VALUE_KEY = "message.empty.command";

	@Override
	public Router execute(HttpServletRequest request, HttpServletResponse response) {
		Router router = new Router();
		router.setPage(PagePath.HOME);
		request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_KEY);
		return router;
	}
}