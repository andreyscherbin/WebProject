package com.epam.forum.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.forum.command.Command;
import com.epam.forum.command.PagePath;
import com.epam.forum.command.Router;

public class GoToNewSectionPageCommand implements Command {
	@Override
	public Router execute(HttpServletRequest request, HttpServletResponse response) {
		Router router = new Router();
		router.setPage(PagePath.NEW_SECTION);
		return router;
	}
}
