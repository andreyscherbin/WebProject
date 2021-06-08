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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebFilter(filterName = "CharacterEncodLangCacheFilter", urlPatterns = { "/*" })
public class CharacterEncodLangCacheFilter implements Filter {
	private static Logger logger = LogManager.getLogger();

	private static final String CHARACTER_ENCODING = "UTF-8";
	private static final String DEFAULT_LANGUAGE = "en_US";
	private static final String ATTRIBUTE_NAME_LANG = "lang";
	private static final String HEADER_NAME_CACHE_CONTROL = "Cache-Control";
	private static final String HEADER_VALUE_CACHE_CONTROL = "no-cache, no-store, must-revalidate";
	private static final String HEADER_NAME_PRAGMA = "Pragma";
	private static final String HEADER_VALUE_PRAGMA = "no-cache";
	private static final String HEADER_NAME_EXPIRES = "Expires";

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		logger.info("character encod lang cache filter");
		HttpSession session = ((HttpServletRequest) request).getSession();
		request.setCharacterEncoding(CHARACTER_ENCODING);
		if (session.getAttribute(ATTRIBUTE_NAME_LANG) == null) {
			session.setAttribute(ATTRIBUTE_NAME_LANG, DEFAULT_LANGUAGE);
		}
		((HttpServletResponse) response).setHeader(HEADER_NAME_CACHE_CONTROL, HEADER_VALUE_CACHE_CONTROL);
		((HttpServletResponse) response).setHeader(HEADER_NAME_PRAGMA, HEADER_VALUE_PRAGMA);
		((HttpServletResponse) response).setDateHeader(HEADER_NAME_EXPIRES, 0);
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		Filter.super.destroy();
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		Filter.super.init(filterConfig);
	}
}
