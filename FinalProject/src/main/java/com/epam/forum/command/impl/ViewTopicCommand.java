package com.epam.forum.command.impl;

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

public class ViewTopicCommand implements Command {
	private static Logger logger = LogManager.getLogger();
	private static final String ATRIBUTE_NAME_TOPICS = "topics";
	private static final String ATRIBUTE_NAME_EMPTY_TOPICS = "empty_topics";
	private TopicService topicService;

	public ViewTopicCommand(TopicService topicService) {
		this.topicService = topicService;
	}

	@Override
	public Router execute(HttpServletRequest request, HttpServletResponse response) { // FIX JSON
		Router router = new Router();
		List<Topic> topics;
		try {
			topics = topicService.findAllTopics();
			if (!topics.isEmpty()) {
				request.setAttribute(ATRIBUTE_NAME_TOPICS, topics);
				router.setPage(PagePath.HOME);
			} else {
				request.setAttribute(ATRIBUTE_NAME_EMPTY_TOPICS, MessageManager.getProperty("message.emptytopics"));
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
