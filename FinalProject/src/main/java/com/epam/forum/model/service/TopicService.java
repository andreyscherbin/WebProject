package com.epam.forum.model.service;

import java.util.List;
import java.util.Optional;
import java.util.Queue;

import com.epam.forum.exception.ServiceException;
import com.epam.forum.model.entity.Topic;

public interface TopicService {
	List<Topic> findAllTopics() throws ServiceException;
	List<Topic> findTopicsByHeader(String pattern) throws ServiceException;
	Queue<Topic> findTopicsBySection(Long sectionId) throws ServiceException;
	Optional<Topic> findTopicById(Long id) throws ServiceException;
	void create(Topic topic) throws ServiceException;
	void delete(Topic topic) throws ServiceException;
	void pinTopic(Topic pinTopic) throws ServiceException;
	void unpinTopic(Topic unpinTopic) throws ServiceException;
	void closeTopic(Topic closeTopic) throws ServiceException;
	void uncloseTopic(Topic uncloseTopic) throws ServiceException;
}
