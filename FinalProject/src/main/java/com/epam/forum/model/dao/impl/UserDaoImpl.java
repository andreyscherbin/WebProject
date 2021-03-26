package com.epam.forum.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.epam.forum.exception.DaoException;
import com.epam.forum.model.dao.UserDao;
import com.epam.forum.model.entity.User;
import com.epam.forum.resource.ConnectionCreator;

public class UserDaoImpl implements UserDao {
	
	private static final String SQL_SELECT_ALL_USERS = "SELECT idphonebook, lastname,phone FROM phonebook";
	private static final String SQL_SELECT_USER_BY_USERNAME = "SELECT user_id, username, password, email, register_date, last_login_date, "
			                                                + "is_email_verifed, is_active, role FROM users WHERE username=?";

	@Override
	public List<User> findAll() throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> findUserByUserName(String namePattern) throws DaoException {
		List<User> users = new ArrayList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = ConnectionCreator.createConnection();
			statement = connection.prepareStatement(SQL_SELECT_USER_BY_USERNAME);
			statement.setString(1, namePattern);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				User user = new User();
				user.setUserId(resultSet.getLong("user_id"));
				user.setPassword(resultSet.getString("password"));
				user.setEmail(resultSet.getString("email"));
				//user.setRegisterDate(resultSet.getTimestamp("register_date"));
				//нужно дописать остальные поля
				user.setUserName(resultSet.getString("username"));
				users.add(user);
			}
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			close(statement);
			close(connection);
		}
		return users;
	}

	@Override
	public User findEntityById(Long id) throws DaoException {
		throw new UnsupportedOperationException();
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
