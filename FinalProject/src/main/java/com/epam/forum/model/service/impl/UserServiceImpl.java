package com.epam.forum.model.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.epam.forum.command.builder.UserBuilder;
import com.epam.forum.command.builder.impl.UserBuilderImpl;
import com.epam.forum.exception.RepositoryException;
import com.epam.forum.exception.ServiceException;
import com.epam.forum.model.entity.Role;
import com.epam.forum.model.entity.User;
import com.epam.forum.model.entity.UserTable;
import com.epam.forum.model.repository.Operation;
import com.epam.forum.model.repository.Repository;
import com.epam.forum.model.repository.SearchCriterion;
import com.epam.forum.model.repository.Specification;
import com.epam.forum.model.repository.impl.EmailUserSpecification;
import com.epam.forum.model.repository.impl.IdUserSpecification;
import com.epam.forum.model.repository.impl.UserNameSpecification;
import com.epam.forum.model.repository.impl.UserRepositoryImpl;
import com.epam.forum.model.service.UserService;
import com.epam.forum.security.PasswordEncoder;
import com.epam.forum.validator.UserValidator;

public class UserServiceImpl implements UserService {
	private static final Long SUCCESS_REGISTRATION = 1L;
	private static final Long EMAIL_ALREADY_IN_USE = 2L;
	private static final Long USERNAME_ALREADY_IN_USE = 3L;
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
	public Optional<User> findUserById(Long id) throws ServiceException {
		Optional<User> user;
		List<User> users;
		try {
			Specification<User> idSpec = new IdUserSpecification(
					new SearchCriterion(UserTable.USER_ID, Operation.EQUAL, id));
			users = (List<User>) userRepository.query(idSpec);
			if (!users.isEmpty()) {
				user = Optional.of(users.get(0));
			} else {
				user = Optional.empty();
			}
		} catch (RepositoryException e) {
			logger.error("find user exception with id: " + id, e);
			throw new ServiceException("find user exception with id: " + id, e);
		}
		return user;
	}

	@Override
	public List<User> findAllUsers() throws ServiceException {
		List<User> users = null;
		try {
			users = (List<User>) userRepository.findAll();
		} catch (RepositoryException e) {
			logger.error("findAll users exception", e);
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
			Specification<User> userNameSpec = new UserNameSpecification(
					new SearchCriterion(UserTable.USERNAME, Operation.EQUAL, userName));
			users = (List<User>) userRepository.query(userNameSpec);
		} catch (RepositoryException e) {
			logger.error("find user exception with username: " + userName, e);
			throw new ServiceException("find user exception with username: " + userName, e);
		}
		return users;
	}

	@Override
	public Optional<User> authenticate(String userName, String password) throws ServiceException {
		List<User> users = new ArrayList<>();
		Optional<User> user = Optional.empty();
		try {
			Specification<User> userNameSpec = new UserNameSpecification(
					new SearchCriterion(UserTable.USERNAME, Operation.EQUAL, userName));
			users = (List<User>) userRepository.query(userNameSpec);
		} catch (RepositoryException e) {
			logger.error("query exception with username: " + userName, e);
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
	public Map<Long, List<Optional<User>>> registrate(String userName, String password, String email)
			throws ServiceException {
		List<User> users = new ArrayList<>();
		Map<Long, List<Optional<User>>> result = new HashMap<>();
		try {
			Specification<User> userNameSpec = new UserNameSpecification(
					new SearchCriterion(UserTable.USERNAME, Operation.EQUAL, userName));
			Specification<User> emailSpec = new EmailUserSpecification(
					new SearchCriterion(UserTable.EMAIL, Operation.EQUAL, email));
			users = (List<User>) userRepository.query(userNameSpec.or(emailSpec));
		} catch (RepositoryException e) {
			logger.error("query exception with username: " + userName + " and email : " + email, e);
			throw new ServiceException("query exception with username: " + userName + " and email : " + email, e);
		}
		if (users.isEmpty()) {
			UserBuilder userBuilder = new UserBuilderImpl();
			userBuilder.buildUsername(userName).buildPassword(password).buildEmail(email)
					.buildRegisterDate(LocalDateTime.now()).buildIsEmailVerifed(false).buildIsActive(false)
					.buildRole(Role.USER);
			User registeredUser = userBuilder.getUser();
			try {
				userRepository.create(registeredUser);
			} catch (RepositoryException e) {
				logger.error("create user exception with user: " + registeredUser, e);
				throw new ServiceException("create user exception with user: " + registeredUser, e);
			}
			Optional<User> user = Optional.of(registeredUser);
			result.put(SUCCESS_REGISTRATION, List.of(user));
			logger.info("success registration: {} {}", userName, email);
		} else {
			List<Optional<User>> userNameAlreadyInUseList = new ArrayList<>();
			List<Optional<User>> emailAlreadyInUseList = new ArrayList<>();
			for (User user : users) {
				if (user.getUserName().equals(userName)) {
					logger.info("user with such username: {} already exists", userName);
					userNameAlreadyInUseList.add(Optional.of(user));
				}
				if (user.getEmail().equals(email)) {
					logger.info("user with such email: {} already exists", email);
					emailAlreadyInUseList.add(Optional.of(user));
				}
			}
			result.put(EMAIL_ALREADY_IN_USE, emailAlreadyInUseList);
			result.put(USERNAME_ALREADY_IN_USE, userNameAlreadyInUseList);			
			logger.info("failed registration: {}", userName);
		}
		return result;
	}

	@Override
	public void save(User user) throws ServiceException {
		try {
			userRepository.update(user);
		} catch (RepositoryException e) {
			logger.error("update user exception with user: " + user, e);
			throw new ServiceException("update user exception with user: " + user, e);
		}
	}

	@Override
	public void updateLastLoginDate(User user) throws ServiceException {
		user.setLastLoginDate(LocalDateTime.now());
		try {
			userRepository.update(user);
		} catch (RepositoryException e) {
			logger.error("update user exception with user: " + user, e);
			throw new ServiceException("update user exception with user: " + user, e);
		}
	}

	@Override
	public void changeRole(User user) throws ServiceException {
		try {
			userRepository.update(user);
		} catch (RepositoryException e) {
			logger.error("update user exception with user: " + user, e);
			throw new ServiceException("update user exception with user: " + user, e);
		}
	}

	@Override
	public void banUser(User user) throws ServiceException {
		try {
			userRepository.update(user);
		} catch (RepositoryException e) {
			logger.error("update user exception with user: " + user, e);
			throw new ServiceException("update user exception with user: " + user, e);
		}
	}

	@Override
	public void unbanUser(User user) throws ServiceException {
		try {
			userRepository.update(user);
		} catch (RepositoryException e) {
			logger.error("update user exception with user: " + user, e);
			throw new ServiceException("update user exception with user: " + user, e);
		}
	}
}
