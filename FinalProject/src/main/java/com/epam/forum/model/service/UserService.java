package com.epam.forum.model.service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import com.epam.forum.exception.ServiceException;
import com.epam.forum.model.entity.User;

public interface UserService {
	List<User> sort(Comparator<User> comparator) throws ServiceException;
	Optional<User> findUserById(Long id) throws ServiceException;
	List<User> findAllUsers() throws ServiceException;
	List<User> findUsersByUserName(String userName) throws ServiceException;
	Optional<User> authenticate(String userName, String password) throws ServiceException;	
	Optional<User> registrate(String userName, String password, String email) throws ServiceException;
}
