package com.epam.forum.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.epam.forum.command.Command;
import com.epam.forum.command.CommandProvider;

@WebServlet(name = "/AjaxServlet", urlPatterns = "/search")
public class AjaxServlet extends HttpServlet {

	private static final long serialVersionUID = 1288812835276078489L;
	private static Logger logger = LogManager.getLogger();
	private static final String COMMAND_SEARCH = "search";

	public AjaxServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("ajax servlet");
		CommandProvider commandProvider = CommandProvider.getInstance();
		Command command = commandProvider.getCommand(COMMAND_SEARCH);
		command.execute((HttpServletRequest) request, (HttpServletResponse) response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
