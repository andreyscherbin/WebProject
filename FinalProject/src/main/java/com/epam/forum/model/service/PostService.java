package com.epam.forum.model.service;

import java.util.List;
import java.util.Optional;
import com.epam.forum.exception.ServiceException;
import com.epam.forum.model.entity.Post;

public interface PostService {
	List<Post> findAllPosts() throws ServiceException;	
	List<Post> findPostsByTopic(Long topicId) throws ServiceException;
	Optional<Post> findPostById(Long id) throws ServiceException;
	void create(Post post) throws ServiceException;
	void delete(Post post) throws ServiceException;
}
