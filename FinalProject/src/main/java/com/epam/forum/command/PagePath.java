package com.epam.forum.command;

public final class PagePath {
	public static final String INDEX = "/WEB-INF/index.jsp";
	public static final String LOGIN = "/WEB-INF/jsp/login.jsp";
	public static final String REGISTRATION = "/WEB-INF/jsp/registration.jsp";
	public static final String NEW_TOPIC = "/WEB-INF/jsp/new_topic.jsp";
	public static final String NEW_SECTION = "/WEB-INF/jsp/new_section.jsp";
	public static final String ERROR = "/WEB-INF/jsp/error/service_error.jsp";
	public static final String SEARCH = "/WEB-INF/jsp/search.jsp";
	public static final String SECTION = "/WEB-INF/jsp/section.jsp";
	public static final String TOPIC = "/WEB-INF/jsp/topic.jsp";
	public static final String ADMIN_HOME = "/WEB-INF/jsp/admin/admin_home.jsp";
	public static final String HOME = "/WEB-INF/jsp/home.jsp";	
	public static final String FORBIDDEN_PAGE = "/WEB-INF/jsp/error/forbidden_page.jsp";
	
	public static final String HOME_REDIRECT = "/controller?command=go_to_home_page";
	public static final String ADMIN_HOME_REDIRECT = "/controller?command=go_to_admin_home_page";

	private PagePath() {
	}
}
