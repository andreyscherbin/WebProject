package com.epam.forum.model.repository.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.epam.forum.exception.DaoException;
import com.epam.forum.exception.RepositoryException;
import com.epam.forum.model.dao.UserDao;
import com.epam.forum.model.dao.impl.UserDaoImpl;
import com.epam.forum.model.entity.User;
import com.epam.forum.model.repository.Specification;
import com.epam.forum.model.repository.Repository;

public class UserRepositoryImpl implements Repository<User> {
	private static Logger logger = LogManager.getLogger();
	private static final Repository<User> INSTANCE = new UserRepositoryImpl();
	private UserDao userDao;

	private UserRepositoryImpl() {
		userDao = new UserDaoImpl();
	}

	public static Repository<User> getInstance() {
		return INSTANCE;
	}

	@Override
	public List<User> getEntities() throws RepositoryException {
		List<User> users = null;
		try {
			users = userDao.findAll();
		} catch (DaoException e) {
			throw new RepositoryException("getEntities exception", e);
		}
		return users;
	}

	@Override
	public Optional<User> get(Long id) {
		Optional<User> user = Optional.empty();
		return user;
	}

	@Override
	public void add(User user) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void update(User user) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void remove(User ugtser) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<User> query(Specification<User> specification) throws RepositoryException { // need more think and
																							// decide finally
		List<User> users = null;
		if (specification instanceof UserNameSpecification) {
			UserNameSpecification userNameSpecification = (UserNameSpecification) specification;
			String userName = userNameSpecification.getUserName();
			try {
				users = userDao.findUserByUserName(userName);
			} catch (DaoException e) {
				throw new RepositoryException("findUserByUserName exception with username: " + userName, e);
			}
		} else if (specification instanceof IdSpecification) {
			IdSpecification idSpecification = (IdSpecification) specification;
			Long id = idSpecification.getId();
			try {
				Optional<User> user = userDao.findEntityById(id);
				users = new ArrayList<>();
				if (!user.isEmpty()) {
					users.add(user.get());
				}
			} catch (DaoException e) {
				throw new RepositoryException("findEntityById exception with id: " + id, e);
			}
		} else {
			throw new RepositoryException("no specification" + specification);
		}
		return users;
	}

	@Override
	public List<User> sort(Comparator<User> comparator) throws RepositoryException {
		List<User> users = null;
		try {
			users = userDao.findAll();
		} catch (DaoException e) {
			throw new RepositoryException("sort exception", e);
		}
		users.sort(comparator);
		return users;
	}
}
