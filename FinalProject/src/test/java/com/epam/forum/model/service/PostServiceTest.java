package com.epam.forum.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.verification.VerificationModeFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.epam.forum.exception.RepositoryException;
import com.epam.forum.exception.ServiceException;
import com.epam.forum.model.entity.Post;
import com.epam.forum.model.entity.PostTable;
import com.epam.forum.model.repository.Repository;
import com.epam.forum.model.repository.spec.Operation;
import com.epam.forum.model.repository.spec.SearchCriterion;
import com.epam.forum.model.repository.spec.Specification;
import com.epam.forum.model.repository.spec.impl.IdPostSpecification;
import com.epam.forum.model.repository.spec.impl.TopicPostSpecification;
import com.epam.forum.model.service.impl.PostServiceImpl;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.mockito.Mockito.verify;

public class PostServiceTest {

	PostService postService;

	@Mock
	private Repository<Long, Post> postRepository;

	private Post mockedPost;
	private List<Post> mockedPosts;
	private static final Long TOPIC_ID = 1L;
	private static final Long POST_ID = 1L;
	Specification<Post> topicSpec;
	Specification<Post> idSpec;

	@BeforeClass
	public void setUp() throws RepositoryException {
		MockitoAnnotations.openMocks(this);
		idSpec = new IdPostSpecification(new SearchCriterion(PostTable.POST_ID, Operation.EQUAL, POST_ID));
		topicSpec = new TopicPostSpecification(new SearchCriterion(PostTable.TOPIC_ID, Operation.EQUAL, TOPIC_ID));
		createMockedPost();
		createMockedPosts();
		setupMockedRepository();
		postService = PostServiceImpl.getInstance(postRepository);
	}

	@Test
	public void findAllPostsTest() throws RepositoryException, ServiceException {
		List<Post> resultPosts = postService.findAllPosts();
		verify(postRepository, VerificationModeFactory.times(1)).findAll();
		assertEquals(resultPosts, mockedPosts);
	}

	@Test
	public void findPostsByTopicTest() throws RepositoryException, ServiceException {
		List<Post> resultPosts = postService.findPostsByTopic(TOPIC_ID);
		verify(postRepository, VerificationModeFactory.times(1)).query(topicSpec);
		assertEquals(resultPosts, mockedPosts);
	}

	@Test
	public void findPostByIdTest() throws RepositoryException, ServiceException {
		Optional<Post> actual = postService.findPostById(POST_ID);
		verify(postRepository, VerificationModeFactory.times(1)).query(idSpec);
		assertEquals(actual.get().getId(), mockedPost.getId());
	}

	private void createMockedPosts() {
		mockedPosts = new ArrayList<>();
		mockedPosts.add(mockedPost);
	}

	private void createMockedPost() {
		mockedPost = new Post();
		mockedPost.setId(1L);
		mockedPost.setContent("content");
	}

	private void setupMockedRepository() throws RepositoryException {
		when(postRepository.findAll()).thenReturn(mockedPosts);
		when(postRepository.query(topicSpec)).thenReturn(mockedPosts);
		when(postRepository.query(idSpec)).thenReturn(List.of(mockedPost));
	}

	@AfterClass
	public void tierDown() {
		postService = null;
		mockedPost = null;
		mockedPosts = null;		
		topicSpec = null;
		idSpec = null;
	}
}
