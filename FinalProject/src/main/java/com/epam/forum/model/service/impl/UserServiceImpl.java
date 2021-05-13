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
import com.epam.forum.model.repository.impl.EmailUserSpecification;
import com.epam.forum.model.repository.impl.IdUserSpecification;
import com.epam.forum.model.repository.impl.UserNameSpecification;
import com.epam.forum.model.repository.impl.UserRepositoryImpl;
import com.epam.forum.model.service.UserService;
import com.epam.forum.security.PasswordEncoder;
import com.epam.forum.template.builder.UserBuilder;
import com.epam.forum.template.builder.impl.UserBuilderImpl;
import com.epam.forum.validator.UserValidator;

public class UserServiceImpl implements UserService {
	private static Logger logger = LogManager.getLogger();
	private static UserService instance;
	private Repository<Long, User> userRepository;

	private UserServiceImpl() {
		userRepository = new UserRepositoryImpl();
	}

	public static UserService getInstance() {
		if (instance == null) {
			instance = new UserServiceImpl();
		}
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
			IdUserSpecification spec1 = new IdUserSpecification(
					new SearchCriterion(UserTable.USER_ID, Operation.EQUAL, id));
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
		if (!UserValidator.isUserNameValid(userName)) {
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
		if (!UserValidator.isUserNameValid(userName) || !UserValidator.isPasswordValid(password)) {
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
		if (!UserValidator.isUserNameValid(userName) || !UserValidator.isPasswordValid(password)
				|| !UserValidator.isEmailValid(email)) {
			logger.info("not valid username or password or email");
			return user;
		}
		try {
			UserNameSpecification spec1 = new UserNameSpecification(
					new SearchCriterion(UserTable.USERNAME, Operation.EQUAL, userName));
			EmailUserSpecification spec2 = new EmailUserSpecification(
					new SearchCriterion(UserTable.EMAIL, Operation.EQUAL, email));
			users = userRepository.query(spec1.or(spec2));
		} catch (RepositoryException e) {
			throw new ServiceException("query exception with username: " + userName + " and email : " + email, e);
		}
		if (users.isEmpty()) {
			UserBuilder userBuilder = new UserBuilderImpl();
			userBuilder.buildUsername(userName);
			userBuilder.buildPassword(password);
			userBuilder.buildEmail(email);
			userBuilder.buildRegisterDate(LocalDateTime.now());
			userBuilder.buildIsEmailVerifed(false);
			userBuilder.buildIsActive(false);
			userBuilder.buildRole(Role.GUEST);
			User registeredUser = userBuilder.getUser();
			try {
				userRepository.create(registeredUser);
			} catch (RepositoryException e) {
				throw new ServiceException("create user exception with user: " + registeredUser, e);
			}
			user = Optional.of(registeredUser);
			logger.info("success registration: {} {}", userName, email);
		} else {
			logger.info("user with such username: {} or email: {} already exists", userName, email);
			logger.info("failed registration: {}", userName);
		}
		return user;
	}

	@Override
	public void save(User user) throws ServiceException {
		try {
			userRepository.update(user);
		} catch (RepositoryException e) {
			throw new ServiceException("update user exception with user: " + user, e);
		}
	}

	@Override
	public void updateLastLoginDate(User user) throws ServiceException {
		user.setLastLoginDate(LocalDateTime.now());
		try {
			userRepository.update(user);
		} catch (RepositoryException e) {
			throw new ServiceException("update user exception with user: " + user, e);
		}
	}

	@Override
	public void changeRole(User user) throws ServiceException {
		try {
			userRepository.update(user);
		} catch (RepositoryException e) {
			throw new ServiceException("update user exception with user: " + user, e);
		}
	}

	@Override
	public void banUser(User user) throws ServiceException {
		try {
			userRepository.update(user);
		} catch (RepositoryException e) {
			throw new ServiceException("update user exception with user: " + user, e);
		}
	}

	@Override
	public void unbanUser(User user) throws ServiceException {
		try {
			userRepository.update(user);
		} catch (RepositoryException e) {
			throw new ServiceException("update user exception with user: " + user, e);
		}
	}
}
