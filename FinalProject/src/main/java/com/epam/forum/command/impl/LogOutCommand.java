package com.epam.forum.command.impl;

import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.epam.forum.command.Command;
import com.epam.forum.command.PagePath;

public class LogOutCommand implements Command {
	private static Logger logger = LogManager.getLogger();
	
	@Override
	public String execute(HttpServletRequest request) {
		String page = PagePath.INDEX;
		request.getSession().invalidate();
		return page;
	}
}