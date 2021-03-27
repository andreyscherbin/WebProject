package com.epam.forum.model.service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.epam.forum.exception.ServiceException;
import com.epam.forum.model.entity.User;

public interface UserService {
	boolean checkLogin(String username, String password); // fix name of method

	List<User> sort(Comparator<User> comparator);

	Optional<User> getUserById(Long id);

	List<User> getUsers() throws ServiceException;

	List<User> getUsersByUserName(String userName) throws ServiceException;
}
