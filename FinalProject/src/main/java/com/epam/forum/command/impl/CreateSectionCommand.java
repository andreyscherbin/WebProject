package com.epam.forum.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.epam.forum.command.Command;
import com.epam.forum.command.PagePath;
import com.epam.forum.command.Router;
import com.epam.forum.exception.ErrorTable;
import com.epam.forum.exception.ServiceException;
import com.epam.forum.model.entity.Section;
import com.epam.forum.model.service.SectionService;
import com.epam.forum.validator.SectionValidator;

public class CreateSectionCommand implements Command {
	private static Logger logger = LogManager.getLogger();
	private static final String PARAM_NAME_DESCRIPTION = "content";
	private static final String PARAM_NAME_HEADER = "header";	
	private static final String ATTRIBUTE_NAME_MESSAGE = "message";
	private static final String ATTRIBUTE_VALUE_WRONG_INPUT = "message.wrong.input";

	private SectionService sectionService;

	public CreateSectionCommand(SectionService sectionService) {
		this.sectionService = sectionService;
	}

	@Override
	public Router execute(HttpServletRequest request, HttpServletResponse response) {
		Router router = new Router();
		String description = request.getParameter(PARAM_NAME_DESCRIPTION);
		String header = request.getParameter(PARAM_NAME_HEADER);
		if (description == null || header == null || !SectionValidator.isDescriptionValid(description)
				|| !SectionValidator.isHeaderValid(header)) {
			request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_WRONG_INPUT);
			router.setPage(PagePath.HOME);
			return router;
		}
		try {
			Section section = new Section();
			section.setHeader(header);
			section.setDescription(description);
			sectionService.create(section);
			router.setPage(PagePath.HOME);
		} catch (ServiceException e) {
			logger.error("service exception ", e);
			request.setAttribute(ErrorTable.ERROR_MESSAGE, e.getMessage());
			request.setAttribute(ErrorTable.ERROR_CAUSE, e.getCause());
			request.setAttribute(ErrorTable.ERROR_LOCATION, request.getRequestURI());
			request.setAttribute(ErrorTable.ERROR_CODE, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			router.setPage(PagePath.ERROR);
		}
		return router;
	}
}
