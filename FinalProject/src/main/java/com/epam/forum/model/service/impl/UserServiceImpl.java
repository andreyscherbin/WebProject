package com.epam.forum.model.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.epam.forum.exception.RepositoryException;
import com.epam.forum.exception.ServiceException;
import com.epam.forum.model.entity.Operation;
import com.epam.forum.model.entity.User;
import com.epam.forum.model.entity.UserTable;
import com.epam.forum.model.service.UserService;
import com.epam.forum.repository.Repository;
import com.epam.forum.repository.SearchCriteria;
import com.epam.forum.repository.Specification;
import com.epam.forum.repository.impl.IdSpecification;
import com.epam.forum.repository.impl.UserNameSpecification;
import com.epam.forum.repository.impl.UserRepositoryImpl;
import com.epam.forum.validator.DigitLatinValidator;
import com.epam.forum.validator.PasswordValidator;

public class UserServiceImpl implements UserService {
	private static Logger logger = LogManager.getLogger();
	private static final UserService instance = new UserServiceImpl();
	private Repository<Long, User> userRepository;

	private UserServiceImpl() {
		userRepository = new UserRepositoryImpl();
	}

	public static UserService getInstance() {
		return instance;
	}

	@Override
	public List<User> sort(Comparator<User> comparator) throws ServiceException {
		List<User> users = null;
		try {
			users = userRepository.findAll();
			users.sort(comparator);
		} catch (RepositoryException e) {
			throw new ServiceException("findAll users exception", e);
		}
		return users;
	}

	@Override
	public Optional<User> findUserById(Long id) throws ServiceException {
		Optional<User> user;
		List<User> users;
		try {
			IdSpecification spec1 = new IdSpecification(new SearchCriteria(UserTable.USER_ID, Operation.EQUAL, id));
			users = userRepository.query(spec1);
			if (!users.isEmpty()) {
				user = Optional.of(users.get(0));
			} else {
				user = Optional.empty();
			}
		} catch (RepositoryException e) {
			throw new ServiceException("find user exception with id: " + id, e);
		}
		return user;
	}

	@Override
	public List<User> findAllUsers() throws ServiceException {
		List<User> users = null;
		try {
			users = userRepository.findAll();
		} catch (RepositoryException e) {
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
			UserNameSpecification spec1 = new UserNameSpecification(
					new SearchCriteria(UserTable.USERNAME, Operation.EQUAL, userName));
			users = userRepository.query(spec1);
		} catch (RepositoryException e) {
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
			UserNameSpecification spec1 = new UserNameSpecification(
					new SearchCriteria(UserTable.USERNAME, Operation.EQUAL, userName));
			users = userRepository.query(spec1);
		} catch (RepositoryException e) { // situation #2
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
