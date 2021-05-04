package com.epam.forum.controller.filter;

import java.io.IOException;
import java.util.Arrays;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.epam.forum.command.PagePath;
import com.epam.forum.model.entity.Role;
import com.epam.forum.security.AccessRules;

@WebFilter(filterName = "/RoleFilter", urlPatterns = { "/controller" })
public class RoleFilter implements Filter {

	private static final String ATTRIBUTE_NAME_ROLE = "role";
	private static final String PARAM_NAME_COMMAND = "command";

	public RoleFilter() {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession();
		String stringRole = (String) session.getAttribute(ATTRIBUTE_NAME_ROLE);
		Role role = Role.valueOf(stringRole);
		String command = httpRequest.getParameter(PARAM_NAME_COMMAND);
		if (command != null && isValidCommand(command) && !hasAuthority(role, command)) {
			RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(PagePath.FORBIDDEN_PAGE);
			dispatcher.forward(request, response);
		} else {
			chain.doFilter(request, response);
		}
	}

	private boolean hasAuthority(Role role, String command) {
		boolean result = false;
		switch (role) {
		case GUEST:
			result = Arrays.asList(AccessRules.FOR_GUESTS).stream().anyMatch(command::equals);
			break;
		case USER:
			result = Arrays.asList(AccessRules.FOR_USERS).stream().anyMatch(command::equals);
			if (result) {
				break;
			}
			result = Arrays.asList(AccessRules.FOR_GUESTS).stream().anyMatch(command::equals);
			break;
		case MODER:
			result = Arrays.asList(AccessRules.FOR_MODERS).stream().anyMatch(command::equals);
			if (result) {
				break;
			}
			result = Arrays.asList(AccessRules.FOR_USERS).stream().anyMatch(command::equals);
			if (result) {
				break;
			}
			result = Arrays.asList(AccessRules.FOR_GUESTS).stream().anyMatch(command::equals);
			break;
		case ADMIN:
			result = true;
			break;
		}
		return result;
	}

	private boolean isValidCommand(String command) {
		boolean result = false;
		result = Arrays.asList(AccessRules.ALL_COMMANDS).stream().anyMatch(command::equals);
		return result;
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}
}
