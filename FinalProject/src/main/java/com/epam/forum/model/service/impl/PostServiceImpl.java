package com.epam.forum.model.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import com.epam.forum.model.service.PostService;

public class PostServiceImpl implements PostService {
	private static Logger logger = LogManager.getLogger();
	private static PostService instance;
	private Repository<Long, Post> postRepository;

	private PostServiceImpl(Repository<Long, Post> postRepository) {
		this.postRepository = postRepository;
	}

	public static PostService getInstance(Repository<Long, Post> postRepository) {
		if (instance == null) {
			instance = new PostServiceImpl(postRepository);
		}
		return instance;
	}

	@Override
	public List<Post> findAllPosts() throws ServiceException {
		List<Post> posts = null;
		try {
			posts = (List<Post>) postRepository.findAll();
		} catch (RepositoryException e) {
			logger.error("findAll posts exception", e);
			throw new ServiceException("findAll posts exception", e);
		}
		return posts;
	}

	@Override
	public List<Post> findPostsByTopic(Long topicId) throws ServiceException {
		List<Post> posts = new ArrayList<>();
		try {
			Specification<Post> topicSpecification = new TopicPostSpecification(
					new SearchCriterion(PostTable.TOPIC_ID, Operation.EQUAL, topicId));
			posts = (List<Post>) postRepository.query(topicSpecification);
		} catch (RepositoryException e) {
			logger.error("find posts exception with topic: " + topicId, e);
			throw new ServiceException("find posts exception with topic: " + topicId, e);
		}
		return posts;
	}

	@Override
	public void create(Post post) throws ServiceException {
		try {
			postRepository.create(post);
		} catch (RepositoryException e) {
			logger.error("create post exception with post: " + post, e);
			throw new ServiceException("create post exception with post: " + post, e);
		}
	}

	@Override
	public void delete(Post post) throws ServiceException {
		try {
			postRepository.delete(post);
		} catch (RepositoryException e) {
			logger.error("delete post exception with post: " + post, e);
			throw new ServiceException("delete post exception with post: " + post, e);
		}
	}

	@Override
	public Optional<Post> findPostById(Long id) throws ServiceException {
		Optional<Post> post;
		List<Post> posts;
		try {
			Specification<Post> idSpec = new IdPostSpecification(
					new SearchCriterion(PostTable.POST_ID, Operation.EQUAL, id));
			posts = (List<Post>) postRepository.query(idSpec);
			if (!posts.isEmpty()) {
				post = Optional.of(posts.get(0));
			} else {
				post = Optional.empty();
			}
		} catch (RepositoryException e) {
			logger.error("find post exception with id: " + id, e);
			throw new ServiceException("find post exception with id: " + id, e);
		}
		return post;
	}

	@Override
	public void edit(Post post) throws ServiceException {
		try {
			postRepository.update(post);
		} catch (RepositoryException e) {
			logger.error("update post exception with post: " + post, e);
			throw new ServiceException("update post exception with post: " + post, e);
		}
	}
}
