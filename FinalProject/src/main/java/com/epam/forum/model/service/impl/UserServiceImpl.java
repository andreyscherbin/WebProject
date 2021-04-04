package com.epam.forum.model.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.epam.forum.exception.DaoException;
import com.epam.forum.exception.ServiceException;
import com.epam.forum.model.dao.UserDao;
import com.epam.forum.model.dao.impl.UserDaoImpl;
import com.epam.forum.model.entity.User;
import com.epam.forum.model.service.UserService;
import com.epam.forum.security.PasswordEncoder;
import com.epam.forum.validator.DigitLatinValidator;
import com.epam.forum.validator.PasswordValidator;

public class UserServiceImpl implements UserService {
	private static Logger logger = LogManager.getLogger();
	private static final UserService instance = new UserServiceImpl();
	private UserDao userDao;

	private UserServiceImpl() {
		userDao = new UserDaoImpl();
	}

	public static UserService getInstance() {
		return instance;
	}

	@Override
	public List<User> sort(Comparator<User> comparator) throws ServiceException {
		List<User> users = null;
		try {
			users = userDao.findAll();
			users.sort(comparator);
		} catch (DaoException e) {
			throw new ServiceException("findAll users exception", e);
		}
		return users;
	}

	@Override
	public Optional<User> findUserById(Long id) throws ServiceException {
		Optional<User> user;
		try {
			user = userDao.findEntityById(id);
		} catch (DaoException e) {
			throw new ServiceException("find user exception with id: " + id, e);
		}
		return user;
	}

	@Override
	public List<User> findAllUsers() throws ServiceException {
		List<User> users = null;
		try {
			users = userDao.findAll();
		} catch (DaoException e) {
			throw new ServiceException("findAll users exception", e);
		}
		return users;
	}

	@Override
	public List<User> findUsersByUserName(String userName) throws ServiceException {
		List<User> users = new ArrayList<>();
		if (!DigitLatinValidator.isDigitLatin(userName)) {
			logger.info("not valid username");
			return users;
		}
		try {
			users = userDao.findUsersByUserName(userName);
		} catch (DaoException e) {
			throw new ServiceException("find user exception with username: " + userName, e);
		}
		return users;
	}

	@Override
	public List<User> authenticate(String userName, String password) throws ServiceException {
		List<User> users = new ArrayList<>();
		if (!DigitLatinValidator.isDigitLatin(userName) || !PasswordValidator.isPasswordValid(password)) { // situation
																											// #1
			logger.info("not valid username or password");
			return users;
		}
		try {
			users = userDao.findUsersByUserName(userName);
		} catch (DaoException e) { // situation #2
			throw new ServiceException("query exception with username: " + userName, e);
		}
		if (!users.isEmpty()) { // situation #3
			User findUser = users.get(0);
			String findUserPassword = findUser.getPassword();
			// password = PasswordEncoder.encodeString(password); need do registration, then
			// check password encoder and decoder
			if (findUserPassword.equals(password)) { // situation #4
				logger.info("success authentication: {}", userName);
			} else { // situation #5
				logger.info("failed authentication: {}", userName);
				users.clear();
			}
		} else { // situation #6
			logger.info("no such user: {}", userName);
			logger.info("failed authentication: {}", userName);
		}
		return users;
	}
}
