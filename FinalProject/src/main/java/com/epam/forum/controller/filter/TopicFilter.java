package com.epam.forum.controller.filter;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
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

@WebFilter(filterName = "/TopicFilter", urlPatterns = { "/jsp/topic.jsp" }, dispatcherTypes = { DispatcherType.REQUEST,
		DispatcherType.FORWARD })
public class TopicFilter implements Filter {

	private static Logger logger = LogManager.getLogger();
	private static final String COMMAND_VIEW_TOPIC_BY_ID = "view_topic_by_id";	
	
	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		logger.info("topic filter");
		CommandProvider commandProvider = CommandProvider.getInstance();
		Command command = commandProvider.getCommand(COMMAND_VIEW_TOPIC_BY_ID);
		command.execute((HttpServletRequest) request, (HttpServletResponse) response);
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}
}
