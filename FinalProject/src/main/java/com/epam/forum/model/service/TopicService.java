package com.epam.forum.model.service;

import java.util.List;
import java.util.Optional;
import com.epam.forum.exception.ServiceException;
import com.epam.forum.model.entity.Topic;

public interface TopicService {
	List<Topic> findAllTopics() throws ServiceException;
	List<Topic> findTopicsByHeader(String pattern) throws ServiceException;
	List<Topic> findTopicsBySection(Long sectionId) throws ServiceException;
	Optional<Topic> findTopicById(Long id) throws ServiceException;
	void create(Topic topic) throws ServiceException;
	void delete(Topic topic) throws ServiceException;
}
