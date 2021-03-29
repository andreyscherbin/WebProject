package com.epam.forum.command;

import javax.servlet.http.HttpServletRequest;

public interface Command {
	Router execute(HttpServletRequest request);
}
