package com.epam.forum.model.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.epam.forum.exception.RepositoryException;
import com.epam.forum.exception.ServiceException;
import com.epam.forum.model.entity.User;
import com.epam.forum.model.repository.UserRepository;
import com.epam.forum.model.repository.impl.UserNameSpecification;
import com.epam.forum.model.repository.impl.UserRepositoryImpl;
import com.epam.forum.model.service.UserService;
import com.epam.forum.validator.CyrillicLatinValidator;

public class UserServiceImpl implements UserService {
	private static Logger logger = LogManager.getLogger();
	private static final UserService INSTANCE = new UserServiceImpl();
	private UserRepository userRepository;

	private static final String USERNAME = "andrey";
	private static final String PASSWORD = "12345WQESADWQDPDSAP%$^Gfd";

	private UserServiceImpl() {
		userRepository = UserRepositoryImpl.getInstance();
	}

	public static UserService getInstance() {
		return INSTANCE;
	}

	@Override
	public boolean checkLogin(String username, String password) {
		return USERNAME.equals(username) && PASSWORD.equals(password);
	}

	@Override
	public List<User> sort(Comparator<User> comparator) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<User> getUserById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getUsers() throws ServiceException {
		List<User> users;
		try {
			users = userRepository.getUsers();
		} catch (RepositoryException e) {
			throw new ServiceException("repository exception", e);
		}
		return users;
	}

	@Override
	public List<User> getUsersByUserName(String userName) throws ServiceException {
		List<User> users;
		if (!CyrillicLatinValidator.isCyrillicLatin(userName)) {
			logger.info("not valid username");
			users = new ArrayList<>(); // need think and decide finally
			return users;
		}		
		try {
			users = userRepository.query(new UserNameSpecification(userName));
		} catch (RepositoryException e) {
			throw new ServiceException("repository exception", e);
		}
		return users;
	}
}
