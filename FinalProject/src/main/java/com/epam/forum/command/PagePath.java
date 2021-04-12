package com.epam.forum.command;

public final class PagePath {
	public static final String INDEX = "/index.jsp";
	public static final String LOGIN = "/jsp/login.jsp";
	public static final String HOME = "/jsp/home.jsp";
	public static final String SEARCH = "/jsp/search.jsp";
	public static final String SECTION = "/jsp/section.jsp";
	public static final String VIEW = "/jsp/view.jsp";
	public static final String ERROR = "/jsp/error/error.jsp";	
	
	public static final String HOME_SECTIONS = "/controller?command=view_section";
	
	private PagePath() {
	}
}
