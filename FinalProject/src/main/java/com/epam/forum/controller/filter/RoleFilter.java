package com.epam.forum.controller.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.epam.forum.command.CommandName;
import com.epam.forum.command.PagePath;
import com.epam.forum.model.entity.Role;
import com.epam.forum.security.AccessRules;

@WebFilter(filterName = "RoleFilter", urlPatterns = { "/controller" })
public class RoleFilter implements Filter {

	private static Logger logger = LogManager.getLogger();
	private static final String ATTRIBUTE_NAME_ROLE = "role";
	private static final String PARAM_NAME_COMMAND = "command";

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		logger.info("role filter");
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession();
		String stringRole = (String) session.getAttribute(ATTRIBUTE_NAME_ROLE);
		String stringCommand = httpRequest.getParameter(PARAM_NAME_COMMAND);
		Role role;
		CommandName command;
		AccessRules accessRules = AccessRules.getInstance();
		try {
			role = (stringRole != null) ? Role.valueOf(stringRole) : Role.GUEST;
		} catch (IllegalArgumentException e) {
			logger.error("no such role {}", stringRole);
			throw new EnumConstantNotPresentException(CommandName.class, stringRole);
		}
		try {
			command = (stringCommand != null) ? CommandName.valueOf(stringCommand.toUpperCase())
					: CommandName.EMPTY_COMMAND;
		} catch (IllegalArgumentException e) {
			logger.error("no such command " + stringCommand, e);
			throw new EnumConstantNotPresentException(CommandName.class, stringCommand);
		}
		if (accessRules.isValidCommand(command) && !accessRules.hasAuthority(role, command)) {
			RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(PagePath.FORBIDDEN_PAGE);
			dispatcher.forward(request, response);
		} else {
			chain.doFilter(request, response);
		}
	}	
}
