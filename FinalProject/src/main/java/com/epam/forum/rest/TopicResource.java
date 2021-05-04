package com.epam.forum.rest;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.epam.forum.exception.ServiceException;
import com.epam.forum.model.entity.Topic;
import com.epam.forum.model.service.TopicService;
import com.epam.forum.model.service.impl.TopicServiceImpl;

@Path("/topics")
public class TopicResource {

	private static Logger logger = LogManager.getLogger();
	private static final String EMPTY_TOPICS = "message.empty.topics";
	private TopicService topicService = TopicServiceImpl.getInstance();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTopics() {
		List<Topic> topics;
		Response response = null;
		GenericEntity<List<Topic>> myEntity = null;
		try {
			topics = topicService.findAllTopics();
			if (!topics.isEmpty()) {
				myEntity = new GenericEntity<List<Topic>>(topics) {
				};
				response = Response.status(HttpServletResponse.SC_OK).entity(myEntity).build();
			} else {
				Response.status(HttpServletResponse.SC_OK).entity(EMPTY_TOPICS).build();
			}
		} catch (ServiceException e) {
			logger.error("service exception ", e);
			response = Response.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
		return response;
	}
}
