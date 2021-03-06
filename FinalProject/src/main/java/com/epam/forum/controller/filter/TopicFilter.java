package com.epam.forum.controller.filter;

import java.io.IOException;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
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
import com.epam.forum.command.PagePath;
import com.epam.forum.command.Router;
import com.epam.forum.controller.filter.decorator.RequestWrapperTopic;

@WebFilter(filterName = "TopicFilter", urlPatterns = { "/WEB-INF/jsp/topic.jsp" }, dispatcherTypes = {
		DispatcherType.REQUEST, DispatcherType.FORWARD })
public class TopicFilter implements Filter {

	private static Logger logger = LogManager.getLogger();
	private static final String COMMAND_VIEW_TOPIC_BY_ID = "view_topic_by_id";
	private static final String PARAM_NAME_TOPIC_ID = "topic_id";

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		logger.info("topic filter");
		if (request.getParameter(PARAM_NAME_TOPIC_ID) == null) {
			request = new RequestWrapperTopic(request);
		}
		CommandProvider commandProvider = CommandProvider.getInstance();
		Command command = commandProvider.getCommand(COMMAND_VIEW_TOPIC_BY_ID);
		Router router = command.execute((HttpServletRequest) request, (HttpServletResponse) response);
		String page = router.getPage();
		if (page.equals(PagePath.ERROR)) {
			RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(page);
			dispatcher.forward(request, response);
		} else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}
}
