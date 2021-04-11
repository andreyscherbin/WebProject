package com.epam.forum.command.impl;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.epam.forum.command.Command;
import com.epam.forum.command.PagePath;
import com.epam.forum.command.Router;
import com.epam.forum.exception.ServiceException;
import com.epam.forum.model.entity.Section;
import com.epam.forum.model.service.SectionService;
import com.epam.forum.resource.MessageManager;

public class ViewSectionCommand implements Command {
	private static Logger logger = LogManager.getLogger();
	private static final String ATRIBUTE_NAME_SECTIONS = "sections";
	private static final String ATRIBUTE_NAME_EMPTY_SECTIONS = "empty_sections";
	private SectionService sectionService;

	public ViewSectionCommand(SectionService sectionService) {
		this.sectionService = sectionService;
	}

	@Override
	public Router execute(HttpServletRequest request, HttpServletResponse response) {
		Router router = new Router();
		List<Section> sections;
		try {
			sections = sectionService.findAllSections();
			if (!sections.isEmpty()) {
				request.setAttribute(ATRIBUTE_NAME_SECTIONS, sections);
				router.setPage(PagePath.HOME);
			} else {
				request.setAttribute(ATRIBUTE_NAME_EMPTY_SECTIONS, MessageManager.getProperty("message.emptysections"));
				router.setPage(PagePath.HOME);
			}
		} catch (ServiceException e) {
			logger.error("service exception {}", e);
			router.setPage(PagePath.ERROR);
			router.setRedirect();
		}
		return router;
	}
}
