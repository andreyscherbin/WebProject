package com.epam.forum.command.impl;

import java.io.IOException;
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
import com.google.gson.Gson;

public class ViewTopicCommand implements Command {
	private static Logger logger = LogManager.getLogger();
	private static final String ATRIBUTE_NAME_TOPICS = "topics";
	private static final String ATRIBUTE_NAME_EMPTY_TOPICS = "empty_topics";
	private TopicService topicService;

	public ViewTopicCommand(TopicService topicService) {
		this.topicService = topicService;
	}

	@Override
	public Router execute(HttpServletRequest request, HttpServletResponse response) {
		Router router = new Router();
		List<Topic> topics;
		try {
			topics = topicService.findAllTopics();
			if (!topics.isEmpty()) {
				List<String> headers = new ArrayList<>();
				for (Topic topic : topics) {
					headers.add(topic.getHeader());
				}
				String json = new Gson().toJson(headers);
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
				router.setWriteResponse();
			} else {
				request.setAttribute(ATRIBUTE_NAME_EMPTY_TOPICS, MessageManager.getProperty("message.emptytopics"));
				router.setPage(PagePath.VIEW);
			}
		} catch (ServiceException e) {
			logger.error("service exception {}", e);
			router.setPage(PagePath.ERROR);
			router.setRedirect();
		} catch (IOException e) {
			logger.error("i/o exception {}", e);
			router.setPage(PagePath.ERROR);
			router.setRedirect();
		}
		return router;
	}
}
