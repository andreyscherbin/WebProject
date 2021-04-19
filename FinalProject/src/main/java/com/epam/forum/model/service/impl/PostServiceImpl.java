package com.epam.forum.model.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.epam.forum.exception.RepositoryException;
import com.epam.forum.exception.ServiceException;
import com.epam.forum.model.entity.Operation;
import com.epam.forum.model.entity.Post;
import com.epam.forum.model.entity.PostTable;
import com.epam.forum.model.entity.User;
import com.epam.forum.model.entity.UserTable;
import com.epam.forum.model.repository.Repository;
import com.epam.forum.model.repository.SearchCriterion;
import com.epam.forum.model.repository.impl.IdPostSpecification;
import com.epam.forum.model.repository.impl.IdUserSpecification;
import com.epam.forum.model.repository.impl.PostRepositoryImpl;
import com.epam.forum.model.repository.impl.TopicPostSpecification;
import com.epam.forum.model.service.PostService;

public class PostServiceImpl implements PostService {
	private static Logger logger = LogManager.getLogger();
	private static final PostService instance = new PostServiceImpl();
	private Repository<Long, Post> postRepository;

	private PostServiceImpl() {
		postRepository = new PostRepositoryImpl();
	}

	public static PostService getInstance() {
		return instance;
	}

	@Override
	public List<Post> findAllPosts() throws ServiceException {
		List<Post> posts = null;
		try {
			posts = postRepository.findAll();
		} catch (RepositoryException e) {
			throw new ServiceException("findAll posts exception", e);
		}
		return posts;
	}

	@Override
	public List<Post> findPostsByTopic(Long topicId) throws ServiceException {
		List<Post> posts = new ArrayList<>();
		try {
			TopicPostSpecification topicSpecification = new TopicPostSpecification(
					new SearchCriterion(PostTable.TOPIC_ID, Operation.EQUAL, topicId));
			posts = postRepository.query(topicSpecification);
		} catch (RepositoryException e) {
			throw new ServiceException("find posts exception with topic: " + topicId, e);
		}
		return posts;
	}

	@Override
	public void create(Post post) throws ServiceException {
		try {
			postRepository.create(post);
		} catch (RepositoryException e) {
			throw new ServiceException("create post exception with post: " + post, e);
		}
	}

	@Override
	public void delete(Post post) throws ServiceException {
		try {
			postRepository.delete(post);
		} catch (RepositoryException e) {
			throw new ServiceException("delete post exception with post: " + post, e);
		}
	}

	@Override
	public Optional<Post> findPostById(Long id) throws ServiceException {
		Optional<Post> post;
		List<Post> posts;
		try {
			IdPostSpecification spec1 = new IdPostSpecification(
					new SearchCriterion(PostTable.POST_ID, Operation.EQUAL, id));
			posts = postRepository.query(spec1);
			if (!posts.isEmpty()) {
				post = Optional.of(posts.get(0));
			} else {
				post = Optional.empty();
			}
		} catch (RepositoryException e) {
			throw new ServiceException("find post exception with id: " + id, e);
		}
		return post;
	}
}
