package com.epam.forum.model.repository.impl;

import java.util.Comparator;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.epam.forum.exception.DaoException;
import com.epam.forum.exception.RepositoryException;
import com.epam.forum.model.dao.UserDao;
import com.epam.forum.model.dao.impl.UserDaoImpl;
import com.epam.forum.model.entity.User;
import com.epam.forum.model.repository.Specification;
import com.epam.forum.model.repository.UserRepository;

public class UserRepositoryImpl implements UserRepository {
	private static Logger logger = LogManager.getLogger();
	private static final UserRepository INSTANCE = new UserRepositoryImpl();
	private UserDao userDao;

	private UserRepositoryImpl() {
		userDao = new UserDaoImpl();
	}

	public static UserRepository getInstance() {
		return INSTANCE;
	}

	@Override
	public List<User> getUsers() throws RepositoryException {
		List<User> users;
		try {
			users = userDao.findAll();
		} catch (DaoException e) {
			throw new RepositoryException("dao exception", e);
		}
		return users;
	}

	@Override
	public User get(Long id) {
		return null;
	}

	@Override
	public void add(User user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(User user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(User ugtser) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<User> query(Specification specification) throws RepositoryException { // need more think and decide
																						// finally
		List<User> users;
		if (specification instanceof UserNameSpecification) {
			UserNameSpecification userNameSpecification = (UserNameSpecification) specification;
			String userName = userNameSpecification.getUserName();
			try {
				users = userDao.findUserByUserName(userName);
			} catch (DaoException e) {
				throw new RepositoryException("dao exception", e);
			}
			return users;
		} else {
			throw new RepositoryException("no specification" + specification);
		}
	}

	@Override
	public List<User> sort(Comparator<User> comparator) {
		// TODO Auto-generated method stub
		return null;
	}
}
