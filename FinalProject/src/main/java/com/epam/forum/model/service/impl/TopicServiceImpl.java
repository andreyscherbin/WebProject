package com.epam.forum.model.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.epam.forum.exception.RepositoryException;
import com.epam.forum.exception.ServiceException;
import com.epam.forum.model.entity.Operation;
import com.epam.forum.model.entity.Topic;
import com.epam.forum.model.entity.TopicTable;
import com.epam.forum.model.repository.Repository;
import com.epam.forum.model.repository.SearchCriterion;
import com.epam.forum.model.repository.impl.HeaderTopicSpecification;
import com.epam.forum.model.repository.impl.IdTopicSpecification;
import com.epam.forum.model.repository.impl.SectionTopicSpecification;
import com.epam.forum.model.repository.impl.TopicRepositoryImpl;
import com.epam.forum.model.service.TopicService;
import com.epam.forum.validator.TopicValidator;

public class TopicServiceImpl implements TopicService {
	private static Logger logger = LogManager.getLogger();
	private static TopicService instance;
	private Repository<Long, Topic> topicRepository;

	private TopicServiceImpl() {
		topicRepository = new TopicRepositoryImpl();
	}

	public static TopicService getInstance() {
		if (instance == null) {
			instance = new TopicServiceImpl();
		}
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
		if (!TopicValidator.isHeaderValid(pattern)) {
			logger.info("not valid header");
			return topics;
		}
		try {
			HeaderTopicSpecification headerSpecification = new HeaderTopicSpecification(new SearchCriterion(
					TopicTable.HEADER, Operation.LIKE, Operation.ANY_SEQUENCE + pattern + Operation.ANY_SEQUENCE));
			topics = topicRepository.query(headerSpecification);
		} catch (RepositoryException e) {
			throw new ServiceException("find topics exception with pattern: " + pattern, e);
		}
		return topics;
	}

	@Override
	public Queue<Topic> findTopicsBySection(Long sectionId) throws ServiceException {
		List<Topic> topics = new ArrayList<>();
		try {
			SectionTopicSpecification sectionSpecification = new SectionTopicSpecification(
					new SearchCriterion(TopicTable.SECTION_ID, Operation.EQUAL, sectionId));
			topics = topicRepository.query(sectionSpecification);
		} catch (RepositoryException e) {
			throw new ServiceException("find topics exception with section: " + sectionId, e);
		}
		Queue<Topic> queueTopics = new LinkedList<>();
		Iterator<Topic> itr = topics.iterator();
		while (itr.hasNext()) {
			Topic topic = itr.next();
			if (topic.isPinned()) {
				queueTopics.add(topic);
				itr.remove();
			}
		}
		if (!topics.isEmpty()) {
			for (Topic topic : topics) {
				queueTopics.add(topic);
			}
		}
		return queueTopics;
	}

	@Override
	public Optional<Topic> findTopicById(Long id) throws ServiceException {
		Optional<Topic> topic;
		List<Topic> topics;
		try {
			IdTopicSpecification spec1 = new IdTopicSpecification(
					new SearchCriterion(TopicTable.TOPIC_ID, Operation.EQUAL, id));
			topics = topicRepository.query(spec1);
			if (!topics.isEmpty()) {
				topic = Optional.of(topics.get(0));
			} else {
				topic = Optional.empty();
			}
		} catch (RepositoryException e) {
			throw new ServiceException("find topic exception with id: " + id, e);
		}
		return topic;
	}

	@Override
	public void create(Topic topic) throws ServiceException {
		try {
			topicRepository.create(topic);
		} catch (RepositoryException e) {
			throw new ServiceException("create topic exception with topic: " + topic, e);
		}
	}

	@Override
	public void delete(Topic topic) throws ServiceException {
		try {
			topicRepository.delete(topic);
		} catch (RepositoryException e) {
			throw new ServiceException("delete topic exception with topic: " + topic, e);
		}
	}

	@Override
	public void pinTopic(Topic pinTopic) throws ServiceException {
		try {
			topicRepository.update(pinTopic);
		} catch (RepositoryException e) {
			throw new ServiceException("pin topic exception with topic: " + pinTopic, e);
		}
	}

	@Override
	public void unpinTopic(Topic unpinTopic) throws ServiceException {
		try {
			topicRepository.update(unpinTopic);
		} catch (RepositoryException e) {
			throw new ServiceException("unpin topic exception with topic: " + unpinTopic, e);
		}
	}

	@Override
	public void closeTopic(Topic closeTopic) throws ServiceException {
		try {
			topicRepository.update(closeTopic);
		} catch (RepositoryException e) {
			throw new ServiceException("close topic exception with topic: " + closeTopic, e);
		}
	}

	@Override
	public void uncloseTopic(Topic uncloseTopic) throws ServiceException {
		try {
			topicRepository.update(uncloseTopic);
		} catch (RepositoryException e) {
			throw new ServiceException("unclose topic exception with topic: " + uncloseTopic, e);
		}
	}
}
