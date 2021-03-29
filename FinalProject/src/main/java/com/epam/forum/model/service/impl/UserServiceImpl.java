package com.epam.forum.model.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.epam.forum.exception.RepositoryException;
import com.epam.forum.exception.ServiceException;
import com.epam.forum.model.entity.User;
import com.epam.forum.model.repository.Repository;
import com.epam.forum.model.repository.impl.IdSpecification;
import com.epam.forum.model.repository.impl.UserNameSpecification;
import com.epam.forum.model.repository.impl.UserRepositoryImpl;
import com.epam.forum.model.service.UserService;
import com.epam.forum.security.PasswordEncoder;
import com.epam.forum.validator.DigitLatinValidator;
import com.epam.forum.validator.PasswordValidator;

public class UserServiceImpl implements UserService {
	private static Logger logger = LogManager.getLogger();
	private static final UserService INSTANCE = new UserServiceImpl();
	private Repository<User> userRepository;

	private UserServiceImpl() {
		userRepository = UserRepositoryImpl.getInstance();
	}

	public static UserService getInstance() {
		return INSTANCE;
	}

	@Override
	public List<User> sort(Comparator<User> comparator) throws ServiceException {
		List<User> users = null;
		try {
			users = userRepository.getEntities();
		} catch (RepositoryException e) {
			throw new ServiceException("get entities exception", e);
		}
		return users;
	}

	@Override
	public Optional<User> getUserById(Long id) throws ServiceException {
		List<User> users = null;
		try {
			users = userRepository.query(new IdSpecification(id));
		} catch (RepositoryException e) {
			throw new ServiceException("get user exception with id: " + id, e);
		}
		Optional<User> user;
		if (!users.isEmpty()) {
			user = Optional.of(users.get(0));
		} else {
			user = Optional.empty();
		}
		return user;
	}

	@Override
	public List<User> getUsers() throws ServiceException {
		List<User> users = null;
		try {
			users = userRepository.getEntities();
		} catch (RepositoryException e) {
			throw new ServiceException("get entities exception", e);
		}
		return users;
	}

	@Override
	public List<User> getUsersByUserName(String userName) throws ServiceException {
		List<User> users = null;
		if (!DigitLatinValidator.isDigitLatin(userName)) {
			logger.info("not valid username");
			return users;
		}
		try {
			users = userRepository.query(new UserNameSpecification(userName));
		} catch (RepositoryException e) {
			throw new ServiceException("get user exception with username: " + userName, e);
		}
		return users;
	}

	@Override
	public List<User> authentication(String userName, String password) throws ServiceException {
		List<User> users = null;
		if (!DigitLatinValidator.isDigitLatin(userName) || !PasswordValidator.isPasswordValid(password)) { // situation
																											// #1
			logger.info("not valid username or password");
			return users;
		}
		try {
			users = userRepository.query(new UserNameSpecification(userName));
		} catch (RepositoryException e) { // situation #2
			throw new ServiceException("query exception with username: " + userName, e);
		}
		if (!users.isEmpty()) { // situation #3
			User findUser = users.get(0);
			String findUserPassword = findUser.getPassword();
			//password = PasswordEncoder.encodeString(password); need do registration, then check password encoder and decoder
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
