package com.epam.forum.command.impl;

import java.util.Optional;
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
import com.epam.forum.validator.DigitValidator;

public class DeleteSectionCommand implements Command {

	private static Logger logger = LogManager.getLogger();
	private static final String PARAM_NAME_SECTION_ID = "section_id";
	private static final String ATTRIBUTE_NAME_MESSAGE = "message";
	private static final String ATTRIBUTE_VALUE_KEY_WRONG_INPUT = "message.wrong.input";
	private static final String ATTRIBUTE_VALUE_KEY_SECTION_EMPTY = "message.section.empty";

	private SectionService sectionService;

	public DeleteSectionCommand(SectionService sectionService) {
		this.sectionService = sectionService;
	}

	@Override
	public Router execute(HttpServletRequest request, HttpServletResponse response) {
		Router router = new Router();
		String id = request.getParameter(PARAM_NAME_SECTION_ID);
		if (id == null || !DigitValidator.isValid(id)) {
			request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_KEY_WRONG_INPUT);
			router.setPage(PagePath.HOME);
			return router;
		}
		Long sectionId = Long.parseLong(id);
		Optional<Section> section = Optional.empty();
		try {
			section = sectionService.findSectionById(sectionId);
			if (!section.isEmpty()) {
				sectionService.delete(section.get());
			} else {
				request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_KEY_SECTION_EMPTY);
			}
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
