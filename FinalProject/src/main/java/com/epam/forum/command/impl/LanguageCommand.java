package com.epam.forum.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.forum.command.Command;
import com.epam.forum.command.PagePath;
import com.epam.forum.command.Router;

public class LanguageCommand implements Command {
	private static Logger logger = LogManager.getLogger();
	private static final String PARAM_NAME_LANGUAGE = "lang";
	private static final String ATRIBUTE_NAME_LANGUAGE = "lang";	

	@Override
	public Router execute(HttpServletRequest request, HttpServletResponse response) {
		Router router = new Router();
		router.setPage(PagePath.INDEX);
		String lang = request.getParameter(PARAM_NAME_LANGUAGE);
		request.getSession().setAttribute(ATRIBUTE_NAME_LANGUAGE, lang);				
		return router;
	}
}