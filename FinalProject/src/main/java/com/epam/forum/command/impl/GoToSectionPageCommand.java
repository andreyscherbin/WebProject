package com.epam.forum.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.forum.command.Command;
import com.epam.forum.command.PagePath;
import com.epam.forum.command.Router;

/**
 * The {@code GoToSectionPageCommand} class represents go to section page
 * command
 * 
 * @author Andrey Shcherbin
 * @version 1.0
 * @since 2021-05-30
 *
 */
public class GoToSectionPageCommand implements Command {

	@Override
	public Router execute(HttpServletRequest request, HttpServletResponse response) {
		Router router = new Router();
		router.setPage(PagePath.SECTION);
		return router;
	}
}