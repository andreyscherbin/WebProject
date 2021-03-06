package com.epam.forum.model.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoSession;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.quality.Strictness;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.epam.forum.exception.RepositoryException;
import com.epam.forum.exception.ServiceException;
import com.epam.forum.model.entity.Topic;
import com.epam.forum.model.entity.TopicTable;
import com.epam.forum.model.repository.Repository;
import com.epam.forum.model.repository.spec.Operation;
import com.epam.forum.model.repository.spec.SearchCriterion;
import com.epam.forum.model.repository.spec.Specification;
import com.epam.forum.model.repository.spec.impl.HeaderTopicSpecification;
import com.epam.forum.model.repository.spec.impl.IdTopicSpecification;
import com.epam.forum.model.repository.spec.impl.SectionTopicSpecification;
import com.epam.forum.model.service.impl.TopicServiceImpl;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class TopicServiceTest {

	@InjectMocks
	TopicService topicService;

	@Mock
	private Repository<Long, Topic> topicRepository;

	MockitoSession mockito;

	private Topic mockedTopic;
	private List<Topic> mockedTopics;
	private static final Long TOPIC_ID = 1L;
	private static final Long SECTION_ID = 1L;
	private static final String HEADER_PATTERN = "pattern";
	Specification<Topic> idSpec;
	Specification<Topic> sectionSpec;
	Specification<Topic> headerSpec;

	@BeforeClass
	public void beforeClass() throws RepositoryException {
		idSpec = new IdTopicSpecification(new SearchCriterion(TopicTable.TOPIC_ID, Operation.EQUAL, TOPIC_ID));
		sectionSpec = new SectionTopicSpecification(
				new SearchCriterion(TopicTable.SECTION_ID, Operation.EQUAL, SECTION_ID));
		headerSpec = new HeaderTopicSpecification(new SearchCriterion(TopicTable.HEADER, Operation.LIKE,
				Operation.ANY_SEQUENCE + HEADER_PATTERN + Operation.ANY_SEQUENCE));
		createMockedTopic();
		createMockedTopics();
		topicService = TopicServiceImpl.getInstance(topicRepository);
	}

	@BeforeMethod
	public void beforeMethod() throws RepositoryException, ServiceException {
		mockito = Mockito.mockitoSession().initMocks(this).strictness(Strictness.STRICT_STUBS).startMocking();
	}

	@Test
	public void findAllTopicsTest() throws RepositoryException, ServiceException {
		when(topicRepository.findAll()).thenReturn(mockedTopics);
		List<Topic> resultTopics = topicService.findAllTopics();
		verify(topicRepository, VerificationModeFactory.times(1)).findAll();
		assertEquals(resultTopics, mockedTopics);
	}

	@Test
	public void findTopicByIdTest() throws ServiceException, RepositoryException {
		when(topicRepository.query(idSpec)).thenReturn(List.of(mockedTopic));
		Optional<Topic> actual = topicService.findTopicById(TOPIC_ID);
		verify(topicRepository, VerificationModeFactory.times(1)).query(idSpec);
		assertEquals(actual.get().getId(), mockedTopic.getId());
	}

	@Test
	public void findTopicsByHeaderTest() throws ServiceException, RepositoryException {
		when(topicRepository.query(headerSpec)).thenReturn(List.of(mockedTopic));
		List<Topic> resultTopics = topicService.findTopicsByHeader(HEADER_PATTERN);
		verify(topicRepository, VerificationModeFactory.times(1)).query(headerSpec);
		assertEquals(resultTopics, mockedTopics);
	}

	@Test
	public void findTopicsBySectionTest() throws RepositoryException, ServiceException {
		when(topicRepository.query(sectionSpec)).thenReturn(List.of(mockedTopic));
		Queue<Topic> resultTopics = topicService.findTopicsBySection(SECTION_ID);
		verify(topicRepository, VerificationModeFactory.times(1)).query(sectionSpec);
		Queue<Topic> expected = new LinkedList<>(mockedTopics);
		assertEquals(resultTopics, expected);
	}

	private void createMockedTopics() {
		mockedTopics = new ArrayList<>();
		mockedTopics.add(mockedTopic);
	}

	private void createMockedTopic() {
		mockedTopic = new Topic();
		mockedTopic.setId(1L);
		mockedTopic.setHeader(HEADER_PATTERN);
	}

	@AfterMethod
	public void afterMethod() {
		mockito.finishMocking();
	}

	@AfterClass
	public void afterClass() {
		topicService = null;
		mockedTopic = null;
		mockedTopics = null;
		idSpec = null;
		sectionSpec = null;
		headerSpec = null;
	}
}
