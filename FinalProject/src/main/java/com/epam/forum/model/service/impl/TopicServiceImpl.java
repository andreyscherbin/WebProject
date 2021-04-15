package com.epam.forum.model.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.epam.forum.exception.RepositoryException;
import com.epam.forum.exception.ServiceException;
import com.epam.forum.model.entity.Operation;
import com.epam.forum.model.entity.Topic;
import com.epam.forum.model.entity.TopicTable;
import com.epam.forum.model.repository.Repository;
import com.epam.forum.model.repository.SearchCriterion;
import com.epam.forum.model.repository.impl.HeaderSpecification;
import com.epam.forum.model.repository.impl.TopicRepositoryImpl;
import com.epam.forum.model.service.TopicService;
import com.epam.forum.validator.LatinCyrillicDigitValidator;

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

	@Override
	public List<Topic> findTopicsByHeader(String pattern) throws ServiceException {
		List<Topic> topics = new ArrayList<>();
		if (!LatinCyrillicDigitValidator.isLatinCyrillic(pattern)) {
			logger.info("not valid pattern");
			return topics;
		}
		try {
			HeaderSpecification headerSpecification = new HeaderSpecification(new SearchCriterion(TopicTable.HEADER,
					Operation.LIKE, Operation.ANY_SEQUENCE + pattern + Operation.ANY_SEQUENCE));
			topics = topicRepository.query(headerSpecification);
		} catch (RepositoryException e) {
			throw new ServiceException("find topics exception with pattern: " + pattern, e);
		}
		return topics;
	}
}
