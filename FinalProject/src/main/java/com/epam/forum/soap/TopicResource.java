package com.epam.forum.soap;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.epam.forum.exception.ServiceException;
import com.epam.forum.model.entity.Topic;
import com.epam.forum.model.service.TopicService;
import com.epam.forum.model.service.impl.TopicServiceImpl;

@WebService
@SOAPBinding(style = Style.RPC)
public class TopicResource {

	private static Logger logger = LogManager.getLogger();
	private static final String EMPTY_TOPICS = "message.empty.topics";
	private static final String SUCCESS = "get topics success";
	private TopicService topicService = TopicServiceImpl.getInstance();

	@WebMethod
	public String getTopics() {

		List<Topic> topics = null;
		try {
			topics = topicService.findAllTopics();
			if (!topics.isEmpty()) {
				logger.info(SUCCESS);
			} else {
				logger.info(EMPTY_TOPICS);
			}
		} catch (ServiceException e) {
			logger.error("service exception ", e);
		}
		return topics.toString();
	}
}