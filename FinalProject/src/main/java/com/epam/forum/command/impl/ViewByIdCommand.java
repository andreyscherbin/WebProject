package com.epam.forum.command.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.epam.forum.command.Command;
import com.epam.forum.model.entity.Message;
import com.epam.forum.model.entity.User;
import com.epam.forum.model.repository.impl.UserRepositoryImpl;
import com.epam.forum.model.service.UserService;
import com.epam.forum.resource.ConfigurationManager;
import com.epam.forum.resource.MessageManager;
import com.epam.forum.validator.DigitValidator;

public class ViewByIdCommand implements Command {
	private static Logger logger = LogManager.getLogger();
	private static final String PARAM_NAME_ID = "id";
	private static final String ATRIBUTE_NAME_LIST = "list";
	private static final String ATRIBUTE_NAME_EMPTY_LIST = "emptyList";
	UserService userService;

	public ViewByIdCommand(UserService userService) {
		this.userService = userService;
	}

	@Override
	public String execute(HttpServletRequest request) {
		String page = null;
		String idString = request.getParameter(PARAM_NAME_ID);
		if (!DigitValidator.isDigit(idString)) {
			request.setAttribute("wrongInput", MessageManager.getProperty("message.wronginput"));
			page = ConfigurationManager.getProperty("path.page.main");
			return page;
		}
		int id = Integer.parseInt(idString);
		List<User> list = UserRepositoryImpl.getInstance().getUserByUserName("andrey");
		//List<Message> list = userService.findById(id);
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
