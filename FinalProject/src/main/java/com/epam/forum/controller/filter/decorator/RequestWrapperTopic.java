package com.epam.forum.controller.filter.decorator;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RequestWrapperTopic extends HttpServletRequestWrapper {

	private static Logger logger = LogManager.getLogger();
	private static final String ATTRIBUTE_NAME_CURRENT_TOPIC = "current_topic";
	private static final String PARAM_NAME_TOPIC_ID = "topic_id";
	private static final String DEFALUT_TOPIC = "1";

	public RequestWrapperTopic(ServletRequest request) {
		super((HttpServletRequest) request);
	}

	@Override
	public String getParameter(String paramName) {
		logger.info("getParameter RequestWrapperTopic");
		String value = super.getParameter(paramName);
		if (PARAM_NAME_TOPIC_ID.equals(paramName)) {
			HttpSession session = super.getSession();
			String topicId = (String) session.getAttribute(ATTRIBUTE_NAME_CURRENT_TOPIC);
			if (topicId != null) {
				value = topicId;
			} else {
				value = DEFALUT_TOPIC;
			}
		}
		return value;
	}
}
