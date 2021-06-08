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
import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * The {@code SearchAjaxCommand} class represents search ajax command
 * 
 * @author Andrey Shcherbin
 * @version 1.0
 * @since 2021-05-30
 *
 */
public class SearchAjaxCommand implements Command {

	private static Logger logger = LogManager.getLogger();
	
	private static final String EMPTY_TOPICS = "message.empty.topics";
	private static final String CONTENT_TYPE = "application/json";
	private static final String CHARACTER_ENCODING = "UTF-8";
	private static final String HEADER = "header";
	
	private static final String ATRIBUTE_NAME_TOPICS = "topics";
	private static final String ATTRIBUTE_VALUE_TOPICS_EMPTY = "message.topics.empty";
	private static final String ATTRIBUTE_NAME_MESSAGE = "message";
	
	private TopicService topicService;

	public SearchAjaxCommand(TopicService topicService) {
		this.topicService = topicService;
	}

	@Override
	public Router execute(HttpServletRequest request, HttpServletResponse response) {
		Router router = new Router();
		List<Topic> topics;
		List<JsonObject> list = new ArrayList<>();
		Gson gson = new Gson();
		response.setContentType(CONTENT_TYPE);
		response.setCharacterEncoding(CHARACTER_ENCODING);
		try {
			topics = topicService.findAllTopics();
			if (!topics.isEmpty()) {
				for (Topic topic : topics) {
					JsonObject object = new JsonObject();
					object.addProperty(HEADER, topic.getHeader());
					list.add(object);
				}
				request.setAttribute(ATRIBUTE_NAME_TOPICS, topics);
			} else {
				JsonObject object = new JsonObject();
				object.addProperty(HEADER, EMPTY_TOPICS);
				list.add(object);
				request.setAttribute(ATTRIBUTE_NAME_MESSAGE, ATTRIBUTE_VALUE_TOPICS_EMPTY);
			}
			response.getWriter().write(gson.toJson(list));
			router.setPage(PagePath.SEARCH);
		} catch (ServiceException e) {
			logger.error("service exception ", e);
			try {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			} catch (IOException e1) {
				logger.error("io exception", e1);
			}
		} catch (IOException e) {
			logger.error("io exception", e);
			try {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			} catch (IOException e1) {
				logger.error("io exception", e1);
			}
		}
		return router;
	}
}
