package com.epam.forum.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Command interface, which represents Command pattern 
 * 
 * @author Andrey Shcherbin
 * @version 1.0
 * @see Router
 * @since   2021-05-30 
 */
public interface Command {
	/**
	 * Execute command with specified request and response
	 * <p>
	 * 
	 * Returns an {@link Router} object that can then be used for forward or redirect action 
	 * 
	 * @param request  request from user
	 * @param response response from server
	 * @return return {@link Router} object 
	 */
	Router execute(HttpServletRequest request, HttpServletResponse response);
}
