package com.epam.forum.model.service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import com.epam.forum.exception.ServiceException;
import com.epam.forum.model.entity.User;

public interface UserService {
	List<User> sort(Comparator<User> comparator) throws ServiceException;
	Optional<User> getUserById(Long id) throws ServiceException;
	List<User> getUsers() throws ServiceException;
	List<User> getUsersByUserName(String userName) throws ServiceException;
	List<User> authentication(String userName, String password) throws ServiceException;
}
