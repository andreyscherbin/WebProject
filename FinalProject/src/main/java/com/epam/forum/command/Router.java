package com.epam.forum.command;

/**
 * The {@code Router} class incapsulate page which user will see and redirection 
 * 
 * @author Andrey Shcherbin
 * @version 1.0
 * @since   2021-05-30 
 */
public class Router {
	
	/**
	 * The page is used for represents view for the user 
	 */	
	private String page;
	
	/**
	 * The isRedirect is used for redirection 
	 */
	private Boolean isRedirect = Boolean.FALSE;	

	/**
	 * Returns page for user 
	 * 
	 * @return page for user
	 */
	public String getPage() {
		return page;
	}
	
	/**
	 * Set page for user
	 * 
	 * @param page page to be set
	 */
	public void setPage(String page) {
		this.page = page;
	}

	/**
	 * Return {@code true} if redirection is need
	 * 
	 * @return {@code true} if redirection is need
	 */
	public Boolean isRedirect() {
		return isRedirect;
	}
	
	/**
	 * Set redirect
	 */
	public void setRedirect() {
		isRedirect = Boolean.TRUE;
	}	
}
