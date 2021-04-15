package com.epam.forum.model.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.epam.forum.exception.RepositoryException;
import com.epam.forum.exception.ServiceException;
import com.epam.forum.model.entity.Operation;
import com.epam.forum.model.entity.Role;
import com.epam.forum.model.entity.User;
import com.epam.forum.model.entity.UserTable;
import com.epam.forum.model.repository.Repository;
import com.epam.forum.model.repository.SearchCriterion;
import com.epam.forum.model.repository.impl.EmailSpecification;
import com.epam.forum.model.repository.impl.IdSpecification;
import com.epam.forum.model.repository.impl.UserNameSpecification;
import com.epam.forum.model.repository.impl.UserRepositoryImpl;
import com.epam.forum.model.service.UserService;
import com.epam.forum.security.PasswordEncoder;
import com.epam.forum.validator.DigitLatinValidator;
import com.epam.forum.validator.EmailValidator;
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
			IdSpecification spec1 = new IdSpecification(new SearchCriterion(UserTable.USER_ID, Operation.EQUAL, id));
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
					new SearchCriterion(UserTable.USERNAME, Operation.EQUAL, userName));
			users = userRepository.query(spec1);
		} catch (RepositoryException e) {
			throw new ServiceException("find user exception with username: " + userName, e);
		}
		return users;
	}

	@Override
	public Optional<User> authenticate(String userName, String password) throws ServiceException {
		List<User> users = new ArrayList<>();
		Optional<User> user = Optional.empty();
		if (!DigitLatinValidator.isDigitLatin(userName) || !PasswordValidator.isPasswordValid(password)) {
			logger.info("not valid username or password");
			return user;
		}
		try {
			UserNameSpecification spec1 = new UserNameSpecification(
					new SearchCriterion(UserTable.USERNAME, Operation.EQUAL, userName));
			users = userRepository.query(spec1);
		} catch (RepositoryException e) {
			throw new ServiceException("query exception with username: " + userName, e);
		}
		if (!users.isEmpty()) {
			user = Optional.of(users.get(0));
			String findUserPassword = user.get().getPassword();
			password = PasswordEncoder.encodeString(password);
			if (findUserPassword.equals(password)) {
				logger.info("success authentication: {}", userName);
			} else {
				logger.info("failed authentication: {}", userName);
				user = Optional.empty();
			}
		} else {
			logger.info("no such user: {}", userName);
			logger.info("failed authentication: {}", userName);
		}

		return user;
	}

	@Override
	public Optional<User> registrate(String userName, String password, String email) throws ServiceException {
		List<User> users = new ArrayList<>();
		Optional<User> user = Optional.empty();
		if (!DigitLatinValidator.isDigitLatin(userName) || !PasswordValidator.isPasswordValid(password)
				|| !EmailValidator.isEmailValid(email)) {
			logger.info("not valid username or password or email");
			return user;
		}
		try {
			UserNameSpecification spec1 = new UserNameSpecification(
					new SearchCriterion(UserTable.USERNAME, Operation.EQUAL, userName));
			EmailSpecification spec2 = new EmailSpecification(
					new SearchCriterion(UserTable.EMAIL, Operation.EQUAL, email));
			users = userRepository.query(spec1.or(spec2));
		} catch (RepositoryException e) {
			throw new ServiceException("query exception with username: " + userName + " and email : " + email, e);
		}
		if (users.isEmpty()) {
			User registeredUser = new User();
			registeredUser.setUserName(userName);
			registeredUser.setPassword(PasswordEncoder.encodeString(password));
			registeredUser.setEmail(email);
			registeredUser.setRegisterDate(LocalDateTime.now());
			registeredUser.setEmailVerifed(false);
			registeredUser.setActive(false);
			registeredUser.setRole(Role.GUEST);
			try {
				userRepository.create(registeredUser);
			} catch (RepositoryException e) {
				throw new ServiceException("create user exception with user: " + registeredUser);
			}
			user = Optional.of(registeredUser);
			logger.info("success registration: {} {}", userName, email);
		} else {
			logger.info("user with such username: {} or email: {} already exists: {}", userName, email);
			logger.info("failed registration: {}", userName);
			/*
			 * for (User alreadyExistingUser : users) { if
			 * (alreadyExistingUser.getEmail().equals(email) ||
			 * alreadyExistingUser.getUserName().equals(userName)) { user =
			 * Optional.of(alreadyExistingUser); } }
			 */
			// fix this, придумать что возвращать в случае совпадения почты или имени
		}
		return user;
	}
}
