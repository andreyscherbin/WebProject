package com.epam.forum.command.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.epam.forum.command.Command;
import com.epam.forum.command.PagePath;
import com.epam.forum.comparator.UserComparator;
import com.epam.forum.model.entity.User;
import com.epam.forum.model.service.UserService;
import com.epam.forum.resource.MessageManager;

public class SortUserByIdCommand implements Command {
	private static Logger logger = LogManager.getLogger();
	private static final String ATRIBUTE_NAME_USERS = "users";
	private static final String ATRIBUTE_NAME_EMPTY_USERS = "empty_users";
	UserService userService;

	public SortUserByIdCommand(UserService userService) {
		this.userService = userService;
	}

	@Override
	public String execute(HttpServletRequest request) {
		String page = null;
		List<User> users = userService.sort(UserComparator.ID);
		if (!users.isEmpty()) {
			request.setAttribute(ATRIBUTE_NAME_USERS, users);
			page = PagePath.VIEW;
		} else {
			request.setAttribute(ATRIBUTE_NAME_EMPTY_USERS, MessageManager.getProperty("message.emptyusers"));
			page = PagePath.VIEW;
		}
		return page;
	}
}
