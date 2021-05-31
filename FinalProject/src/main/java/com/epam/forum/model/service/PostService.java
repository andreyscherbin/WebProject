package com.epam.forum.model.service;

import java.util.List;
import java.util.Optional;
import com.epam.forum.exception.ServiceException;
import com.epam.forum.model.entity.Post;

/**
 * Interface for post service. This service is responsible for the work
 * with posts
 * 
 * @author Andrey Shcherbin
 * @version 1.0
 * @since 2021-05-30
 *
 */
public interface PostService {
	
	/**
	 * Returns all posts
	 * 
	 * @return all posts
	 * @throws ServiceException
	 */
	List<Post> findAllPosts() throws ServiceException;
	
	/**
	 * Returns a list of posts that are contained in a topic with specified id
	 * 
	 * @param topicId id of the topic
	 * @return list of posts that are contained in a topic
	 * @throws ServiceException
	 */
	List<Post> findPostsByTopic(Long topicId) throws ServiceException;
	
	/**
	 * Retrieves a post by its id
	 * 
	 * @param id id of the post.
	 * @return the post with the given id or {@link Optional#empty()} if none found
	 * @throws ServiceException
	 */
	Optional<Post> findPostById(Long id) throws ServiceException;
	
	/**
	 * Creates a given post
	 * 
	 * @param post created post
	 * @throws ServiceException
	 */
	void create(Post post) throws ServiceException;
	
	/**
	 * Deletes a given post
	 * 
	 * @param post deleted post
	 * @throws ServiceException
	 */
	void delete(Post post) throws ServiceException;
	
	/**
	 * Edits a given post
	 * 
	 * @param post editable post
	 * @throws ServiceException
	 */
	void edit(Post post) throws ServiceException;
}
