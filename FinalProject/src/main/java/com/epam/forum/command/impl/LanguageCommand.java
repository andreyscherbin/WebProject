package com.epam.forum.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.forum.command.Command;
import com.epam.forum.command.PagePath;
import com.epam.forum.command.Router;

/**
 * The {@code LanguageCommand} class represents switch language command
 * 
 * @author Andrey Shcherbin
 * @version 1.0
 * @since 2021-05-30
 *
 */
public class LanguageCommand implements Command {
	private static Logger logger = LogManager.getLogger();
	private static final String LANGUAGE = "lang";	

	@Override
	public Router execute(HttpServletRequest request, HttpServletResponse response) {
		Router router = new Router();
		router.setPage(PagePath.HOME);		
		String lang = request.getParameter(LANGUAGE);
		request.getSession().setAttribute(LANGUAGE, lang);				
		return router;
	}
}