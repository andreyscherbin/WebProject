package com.epam.forum.command.impl;

import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.epam.forum.command.Command;
import com.epam.forum.command.PagePath;
import com.epam.forum.command.Router;
import com.epam.forum.resource.MessageManager;

public class EmptyCommand implements Command {
	private static Logger logger = LogManager.getLogger();
	private static final String ATTRIBUTE_NAME_EMPTY_COMMAND = "empty_command";

	@Override
	public Router execute(HttpServletRequest request) {
		Router router = new Router();
		router.setPage(PagePath.LOGIN);
		request.setAttribute(ATTRIBUTE_NAME_EMPTY_COMMAND, MessageManager.getProperty("message.emptycommand"));
		return router;
	}
}