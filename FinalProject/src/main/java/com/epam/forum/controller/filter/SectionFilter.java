package com.epam.forum.controller.filter;

import java.io.IOException;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
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

@WebFilter(filterName = "SectionFilter", urlPatterns = { "/WEB-INF/jsp/section.jsp" }, dispatcherTypes = {
		DispatcherType.REQUEST, DispatcherType.FORWARD })
public class SectionFilter implements Filter {

	private static Logger logger = LogManager.getLogger();
	private static final String COMMAND_VIEW_SECTION_BY_ID = "view_section_by_id";

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		logger.info("section filter");
		CommandProvider commandProvider = CommandProvider.getInstance();
		Command command = commandProvider.getCommand(COMMAND_VIEW_SECTION_BY_ID);
		Router router = command.execute((HttpServletRequest) request, (HttpServletResponse) response);
		String page = router.getPage();
		if (page.equals(PagePath.ERROR)) {
			RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(page);
			dispatcher.forward(request, response);
		} else {
			chain.doFilter(request, response);
		}
	}
}
