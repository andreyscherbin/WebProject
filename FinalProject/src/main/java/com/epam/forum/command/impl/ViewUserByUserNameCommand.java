package com.epam.forum.command.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.epam.forum.command.Command;
import com.epam.forum.command.PagePath;
import com.epam.forum.exception.ServiceException;
import com.epam.forum.model.entity.User;
import com.epam.forum.model.service.UserService;
import com.epam.forum.resource.MessageManager;

public class ViewUserByUserNameCommand implements Command {

	private static Logger logger = LogManager.getLogger();
	private static final String PARAM_NAME_USERNAME = "user_name";
	private static final String ATRIBUTE_NAME_USERS = "users";
	private static final String ATRIBUTE_NAME_EMPTY_USERS = "empty_users";	
	UserService userService;

	public ViewUserByUserNameCommand(UserService userService) {
		this.userService = userService;
	}

	@Override
	public String execute(HttpServletRequest request) {
		String page = null;
		String userName = request.getParameter(PARAM_NAME_USERNAME);
		List<User> users;
		try {
			users = userService.getUsersByUserName(userName);
		} catch (ServiceException e) {  
			logger.error("service exception {}", e);			
			page = PagePath.ERROR;	
			return page; //need more think and decide finally
		}
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
