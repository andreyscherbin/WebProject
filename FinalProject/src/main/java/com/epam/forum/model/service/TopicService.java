package com.epam.forum.model.service;

import java.util.List;
import java.util.Optional;
import java.util.Queue;

import com.epam.forum.exception.ServiceException;
import com.epam.forum.model.entity.Topic;

/**
 * Interface for topic service. This service is responsible for the work
 * with topics
 * 
 * @author Andrey Shcherbin
 * @version 1.0
 * @since 2021-05-30
 *
 */
public interface TopicService {
	
	/**
	 * Returns all topics
	 * 
	 * @return all topics
	 * @throws ServiceException
	 */
	List<Topic> findAllTopics() throws ServiceException;
	
	/**
	 * Returns all topics which matches pattern
	 * 
	 * @param pattern header pattern of the topic
	 * @return all topics which matches pattern
	 * @throws ServiceException
	 */
	List<Topic> findTopicsByHeader(String pattern) throws ServiceException;
	
	/**
	 * Returns all topics which matches specified section
	 * 
	 * @param sectionId id of the section 
	 * @return all topics which matches specified section
	 * @throws ServiceException
	 */
	Queue<Topic> findTopicsBySection(Long sectionId) throws ServiceException;
	
	/**
	 * Retrieves a topic by its id
	 * 
	 * @param id id of the topic
	 * @return the topic with the given id or {@link Optional#empty()} if none found
	 * @throws ServiceException
	 */
	Optional<Topic> findTopicById(Long id) throws ServiceException;
	
	/**
	 * Creates a given topic
	 * 
	 * @param topic created topic
	 * @throws ServiceException
	 */
	void create(Topic topic) throws ServiceException;
	
	/**
	 * Deletes a given topic
	 * 
	 * @param topic deleted topic
	 * @throws ServiceException
	 */
	void delete(Topic topic) throws ServiceException;
	
	/**
	 * Pinns a given topic
	 * 
	 * @param pinTopic pinned topic
	 * @throws ServiceException
	 */
	void pinTopic(Topic pinTopic) throws ServiceException;
	
	/**
	 * Unpinns a given topic
	 * 
	 * @param unpinTopic unpinned topic
	 * @throws ServiceException
	 */
	void unpinTopic(Topic unpinTopic) throws ServiceException;
	
	/**
	 * Closes a given topic
	 * 
	 * @param closeTopic closed topic
	 * @throws ServiceException
	 */
	void closeTopic(Topic closeTopic) throws ServiceException;
	
	/**
	 * Uncloses a given topic
	 * 
	 * @param uncloseTopic unclosed topic
	 * @throws ServiceException
	 */
	void uncloseTopic(Topic uncloseTopic) throws ServiceException;
}
