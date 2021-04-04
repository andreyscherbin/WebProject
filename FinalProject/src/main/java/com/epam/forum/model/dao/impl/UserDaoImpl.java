package com.epam.forum.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.epam.forum.exception.DaoException;
import com.epam.forum.model.dao.UserDao;
import com.epam.forum.model.entity.User;
import com.epam.forum.model.entity.UserTable;
import com.epam.forum.pool.ConnectionCreator;

public class UserDaoImpl implements UserDao {

	private static final String SQL_SELECT_ALL_USERS = "SELECT user_id, username, password, email, register_date, last_login_date, "
			+ "is_email_verifed, is_active, role FROM users";
	private static final String SQL_SELECT_USER_BY_USERNAME = "SELECT user_id, username, password, email, register_date, last_login_date, "
			+ "is_email_verifed, is_active, role FROM users WHERE username=?";
	private static final String SQL_SELECT_USER_BY_ID = "SELECT user_id, username, password, email, register_date, last_login_date, "
			+ "is_email_verifed, is_active, role FROM users WHERE user_id=?";

	@Override
	public List<User> findAll() throws DaoException {
		List<User> users = new ArrayList<>();
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionCreator.createConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(SQL_SELECT_ALL_USERS);
			while (resultSet.next()) {
				User user = new User();
				user.setUserId(resultSet.getLong(UserTable.USER_ID));
				user.setPassword(resultSet.getString(UserTable.PASSWORD));
				user.setEmail(resultSet.getString(UserTable.EMAIL));
				// user.setRegisterDate(resultSet.getTimestamp("register_date")); // fix me
				user.setUserName(resultSet.getString(UserTable.USERNAME));
				users.add(user);
			}
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			close(statement); // fix me, order
			close(connection);
			close(resultSet);
		}
		return users;
	}

	@Override
	public List<User> findUsersByUserName(String namePattern) throws DaoException {
		List<User> users = new ArrayList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionCreator.createConnection();
			statement = connection.prepareStatement(SQL_SELECT_USER_BY_USERNAME);
			statement.setString(1, namePattern);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				User user = new User();
				user.setUserId(resultSet.getLong(UserTable.USER_ID));
				user.setPassword(resultSet.getString(UserTable.PASSWORD));
				user.setEmail(resultSet.getString(UserTable.EMAIL));
				// user.setRegisterDate(resultSet.getTimestamp("register_date")); //fix me
				user.setUserName(resultSet.getString(UserTable.USERNAME));
				users.add(user);
			}
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			close(statement);
			close(connection);
			close(resultSet);
		}
		return users;
	}

	@Override
	public Optional<User> findEntityById(Long id) throws DaoException {
		String stringId = id.toString();
		List<User> users = new ArrayList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionCreator.createConnection();
			statement = connection.prepareStatement(SQL_SELECT_USER_BY_ID);
			statement.setString(1, stringId);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				User user = new User();
				user.setUserId(resultSet.getLong(UserTable.USER_ID));
				user.setPassword(resultSet.getString(UserTable.PASSWORD));
				user.setEmail(resultSet.getString(UserTable.EMAIL));
				// user.setRegisterDate(resultSet.getTimestamp("register_date")); //fix me
				user.setUserName(resultSet.getString(UserTable.USERNAME));
				users.add(user);
			}
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			close(statement);
			close(connection);
			close(resultSet);
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
	public boolean delete(User t) throws DaoException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean delete(Long id) throws DaoException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean create(User t) throws DaoException {
		throw new UnsupportedOperationException();
	}

	@Override
	public User update(User t) throws DaoException {
		throw new UnsupportedOperationException();
	}
}
