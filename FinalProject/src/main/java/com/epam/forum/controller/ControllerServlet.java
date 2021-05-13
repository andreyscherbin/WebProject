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
import com.epam.forum.command.Router;
import com.epam.forum.pool.ConnectionPool;

@WebServlet(name = "/ControllerServlet", urlPatterns = { "/controller" })
public class ControllerServlet extends HttpServlet {

	private static final long serialVersionUID = -8638304278665235496L;

	@Override
	public void init() throws ServletException {
		super.init();
		ConnectionPool.getInstance();
	}

	@Override
	public void destroy() {	
		super.destroy();
		ConnectionPool.getInstance().shutdown();				
	}

	private static Logger logger = LogManager.getLogger();
	private static final String PARAM_NAME_COMMAND = "command";

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
		logger.info("session id: {}", request.getSession().getId());
		CommandProvider commandProvider = CommandProvider.getInstance();
		String commandName = request.getParameter(PARAM_NAME_COMMAND);
		Command command = commandProvider.getCommand(commandName);
		Router router = command.execute(request, response);
		Boolean isRedirect = router.isRedirect();
		String page = router.getPage();
		if (Boolean.FALSE == isRedirect) {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
			dispatcher.forward(request, response);
		} else {
			response.sendRedirect(request.getContextPath() + page);
		}
	}
}