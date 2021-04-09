package com.epam.forum.model.service.impl;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.forum.exception.RepositoryException;
import com.epam.forum.exception.ServiceException;
import com.epam.forum.model.entity.Topic;
import com.epam.forum.model.repository.Repository;
import com.epam.forum.model.repository.impl.TopicRepositoryImpl;
import com.epam.forum.model.service.TopicService;

public class TopicServiceImpl implements TopicService {
	private static Logger logger = LogManager.getLogger();
	private static final TopicService instance = new TopicServiceImpl();
	private Repository<Long, Topic> topicRepository;

	private TopicServiceImpl() {
		topicRepository = new TopicRepositoryImpl();
	}

	public static TopicService getInstance() {
		return instance;
	}

	@Override
	public List<Topic> findAllTopics() throws ServiceException {
		List<Topic> topics = null;
		try {
			topics = topicRepository.findAll();
		} catch (RepositoryException e) {
			throw new ServiceException("findAll topics exception", e);
		}
		return topics;
	}
}
