package com.epam.forum.controller.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebFilter(filterName = "/SessionLocaleFilter", urlPatterns = { "/controller" })
public class SessionLocaleFilter implements Filter {

	private static Logger logger = LogManager.getLogger();
	private static final String PARAM_NAME_LANGUAGE = "lang";
	private static final String ATRIBUTE_NAME_LANGUAGE = "lang";
	private static final String ATRIBUTE_VALUE_LANGUAGE = "en_US";

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		logger.info("language filter");
		HttpServletRequest req = (HttpServletRequest) request;

		if (req.getParameter(PARAM_NAME_LANGUAGE) != null) {
			req.getSession().setAttribute(ATRIBUTE_NAME_LANGUAGE, req.getParameter(PARAM_NAME_LANGUAGE));
		} else {
			req.getSession().setAttribute(ATRIBUTE_NAME_LANGUAGE, ATRIBUTE_VALUE_LANGUAGE);
		}
		chain.doFilter(request, response);
	}

	public void destroy() {
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}
}
