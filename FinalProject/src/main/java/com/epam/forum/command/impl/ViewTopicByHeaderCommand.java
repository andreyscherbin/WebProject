package com.epam.forum.command.impl;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.epam.forum.command.Command;
import com.epam.forum.command.PagePath;
import com.epam.forum.command.Router;
import com.epam.forum.exception.ServiceException;
import com.epam.forum.model.entity.Topic;
import com.epam.forum.model.service.TopicService;
import com.epam.forum.resource.MessageManager;

public class ViewTopicByHeaderCommand implements Command {
	private static Logger logger = LogManager.getLogger();
	private static final String PARAM_NAME_HEADER = "header";
	private static final String ATRIBUTE_NAME_TOPICS = "topics";
	private static final String ATRIBUTE_NAME_EMPTY_TOPICS = "empty_topics";
	private TopicService topicService;

	public ViewTopicByHeaderCommand(TopicService topicService) {
		this.topicService = topicService;
	}

	@Override
	public Router execute(HttpServletRequest request, HttpServletResponse response) {
		Router router = new Router();
		String pattern = request.getParameter(PARAM_NAME_HEADER);		
		List<Topic> topics = new ArrayList<>();
		try {
			topics = topicService.findTopicsByHeader(pattern);
			if (!topics.isEmpty()) {				
				request.setAttribute(ATRIBUTE_NAME_TOPICS, topics);
				router.setPage(PagePath.SEARCH);
			} else {
				request.setAttribute(ATRIBUTE_NAME_EMPTY_TOPICS, MessageManager.getProperty("message.emptytopics"));
				router.setPage(PagePath.SEARCH);
			}
		} catch (ServiceException e) {
			logger.error("service exception {}", e);
			router.setPage(PagePath.ERROR);
			router.setRedirect();
		}
		return router;
	}
}
