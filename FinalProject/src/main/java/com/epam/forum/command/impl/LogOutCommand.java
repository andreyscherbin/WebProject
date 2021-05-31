package com.epam.forum.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.epam.forum.command.Command;
import com.epam.forum.command.PagePath;
import com.epam.forum.command.Router;

/**
 * The {@code LogOutCommand} class represents log out command
 * 
 * @author Andrey Shcherbin
 * @version 1.0
 * @since 2021-05-30
 *
 */
public class LogOutCommand implements Command {
	private static Logger logger = LogManager.getLogger();
	private static final String ATRIBUTE_NAME_LANGUAGE = "lang";
	private static final String ATRIBUTE_VALUE_LANGUAGE = "en_US";
	private static final String ATRIBUTE_NAME_ROLE = "role";
	private static final String ATRIBUTE_VALUE_ROLE = "GUEST";

	@Override
	public Router execute(HttpServletRequest request, HttpServletResponse response) {
		Router router = new Router();
		router.setPage(PagePath.HOME);
		request.getSession().invalidate();
		request.getSession().setAttribute(ATRIBUTE_NAME_LANGUAGE, ATRIBUTE_VALUE_LANGUAGE);
		request.getSession().setAttribute(ATRIBUTE_NAME_ROLE, ATRIBUTE_VALUE_ROLE);
		return router;
	}
}