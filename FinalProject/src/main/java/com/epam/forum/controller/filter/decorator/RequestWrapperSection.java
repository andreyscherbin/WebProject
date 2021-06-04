package com.epam.forum.controller.filter.decorator;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RequestWrapperSection extends HttpServletRequestWrapper {

	private static Logger logger = LogManager.getLogger();
	private static final String ATTRIBUTE_NAME_CURRENT_SECTION = "current_section";
	private static final String PARAM_NAME_SECTION_ID = "section_id";
	private static final String DEFALUT_SECTION = "1";

	public RequestWrapperSection(ServletRequest request) {
		super((HttpServletRequest) request);
	}

	@Override
	public String getParameter(String paramName) {
		logger.info("getParameter RequestWrapperSection");
		String value = super.getParameter(paramName);
		if (PARAM_NAME_SECTION_ID.equals(paramName)) {
			HttpSession session = super.getSession();
			String sectionId = (String) session.getAttribute(ATTRIBUTE_NAME_CURRENT_SECTION);
			if (sectionId != null) {
				value = sectionId;
			} else {
				value = DEFALUT_SECTION;
			}
		}
		return value;
	}
}