package com.epam.forum.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.epam.forum.command.Command;
import com.epam.forum.command.CommandProvider;
import com.epam.forum.command.PagePath;
import com.epam.forum.resource.MessageManager;

@WebServlet(urlPatterns = "/controller")
public class Controller extends HttpServlet {

	private static Logger logger = LogManager.getLogger();
	private static final String PARAM_NAME_COMMAND = "command";
	private static final String ATTRIBUTE_NAME_NULLPAGE = "null_page";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("doGet");
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("doPost");
		processRequest(request, response);
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String page = null;
		CommandProvider commandProvider = CommandProvider.getInstance();
		String commandName = request.getParameter(PARAM_NAME_COMMAND);
		Command command;
		command = commandProvider.getCommand(commandName);
		page = command.execute(request);
		if (page != null) { // fix me
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
			dispatcher.forward(request, response);				
		} else { //fix me
			page = PagePath.INDEX;
			request.getSession().setAttribute(ATTRIBUTE_NAME_NULLPAGE, MessageManager.getProperty("message.nullpage"));
			response.sendRedirect(request.getContextPath() + page);
		}
	}
}