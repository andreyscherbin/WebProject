package com.epam.forum.model.service;

import java.util.List;
import com.epam.forum.exception.ServiceException;
import com.epam.forum.model.entity.Topic;

public interface TopicService {
	List<Topic> findAllTopics() throws ServiceException;
	List<Topic> findTopicsByHeader(String pattern) throws ServiceException;
}
