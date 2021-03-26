package com.epam.forum.command.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.epam.forum.command.Command;
import com.epam.forum.comparator.MessageComparator;
import com.epam.forum.model.entity.Message;
import com.epam.forum.model.service.UserService;
import com.epam.forum.resource.ConfigurationManager;
import com.epam.forum.resource.MessageManager;

public class SortByIdCommand implements Command {
	private static Logger logger = LogManager.getLogger();
	private static final String ATRIBUTE_NAME_LIST = "list";
	private static final String ATRIBUTE_NAME_EMPTY_LIST = "emptyList";
	UserService userService;

	public SortByIdCommand(UserService userService) {
		this.userService = userService;
	}

	@Override
	public String execute(HttpServletRequest request) {
		String page = null;
		List<Message> list = userService.sort(MessageComparator.ID);
		if (!list.isEmpty()) {
			request.setAttribute(ATRIBUTE_NAME_LIST, list);
			page = ConfigurationManager.getProperty("path.page.view");
		} else {
			request.setAttribute(ATRIBUTE_NAME_EMPTY_LIST, MessageManager.getProperty("message.emptylist"));
			page = ConfigurationManager.getProperty("path.page.view");
		}
		return page;
	}
}
