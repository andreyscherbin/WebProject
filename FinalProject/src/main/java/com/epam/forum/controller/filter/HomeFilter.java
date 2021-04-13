package com.epam.forum.controller.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.DispatcherType;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.epam.forum.command.Command;
import com.epam.forum.command.CommandProvider;

@WebFilter(filterName = "/HomeFilter", urlPatterns = { "/jsp/home.jsp" }, dispatcherTypes = { DispatcherType.REQUEST,
		DispatcherType.FORWARD })
public class HomeFilter implements Filter {

	private static Logger logger = LogManager.getLogger();
	private static final String COMMAND_VIEW_SECTION = "view_section";

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		logger.info("home filter");
		CommandProvider commandProvider = CommandProvider.getInstance();
		Command command = commandProvider.getCommand(COMMAND_VIEW_SECTION);
		command.execute((HttpServletRequest) request, (HttpServletResponse) response);
		chain.doFilter(request, response);
	}

	public void destroy() {
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}
}
