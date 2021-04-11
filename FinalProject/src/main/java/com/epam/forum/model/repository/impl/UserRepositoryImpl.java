package com.epam.forum.model.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import com.epam.forum.exception.RepositoryException;
import com.epam.forum.model.entity.Role;
import com.epam.forum.model.entity.User;
import com.epam.forum.model.repository.Repository;
import com.epam.forum.model.repository.SearchCriteria;
import com.epam.forum.model.repository.Specification;
import com.epam.forum.pool.ConnectionPool;

import static com.epam.forum.model.entity.UserTable.*;

public class UserRepositoryImpl implements Repository<Long, User> {

	private static final String SQL_SELECT_ALL_USERS = "SELECT user_id, username, password, email, register_date, last_login_date, "
			+ "is_email_verifed, is_active, role FROM users";

	@Override
	public Optional<User> find(Long id) throws RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void create(User entity) throws RepositoryException {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(User entity) throws RepositoryException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(User entity) throws RepositoryException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<User> query(Specification<User> specification) throws RepositoryException {
		List<SearchCriteria> criterias = specification.getSearchCriterias();
		List<User> users = new ArrayList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		ConnectionPool pool = null;
		try {
			pool = ConnectionPool.getInstance();
			connection = pool.getConnection();
			statement = connection.prepareStatement(specification.toSqlQuery());
			int i = 1;
			for (SearchCriteria criterion : criterias) {
				String key = criterion.getKey();
				Object value = criterion.getValue();
				if (key.equals(USERNAME)) {
					statement.setString(i, (String) value);
				} else if (criterion.getKey().equals(USER_ID)) {
					statement.setLong(i, (Long) value);
				} else if (criterion.getKey().equals(EMAIL)) {
					statement.setString(i, (String) value);
				}
				i++;
			}
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				User user = new User();
				user.setId(resultSet.getLong(USER_ID));
				user.setPassword(resultSet.getString(PASSWORD));
				user.setEmail(resultSet.getString(EMAIL));
				// user.setRegisterDate(resultSet.getTimestamp("register_date")); //fix me
				user.setUserName(resultSet.getString(USERNAME));				
				Role role = Role.valueOf(resultSet.getString(ROLE));
				user.setRole(role);
				users.add(user);
			}
		} catch (SQLException e) {
			throw new RepositoryException(e);
		} finally {
			close(resultSet);
			close(statement);
			close(connection);
		}
		return users;
	}

	@Override
	public List<User> sort(Comparator<User> comparator) throws RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> findAll() throws RepositoryException {
		List<User> users = new ArrayList<>();
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		ConnectionPool pool = null;
		try {
			pool = ConnectionPool.getInstance();
			connection = pool.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(SQL_SELECT_ALL_USERS);
			while (resultSet.next()) {
				User user = new User();
				user.setId(resultSet.getLong(USER_ID));
				user.setPassword(resultSet.getString(PASSWORD));
				user.setEmail(resultSet.getString(EMAIL));
				// user.setRegisterDate(resultSet.getTimestamp("register_date")); // fix me
				user.setUserName(resultSet.getString(USERNAME));
				Role role = Role.valueOf(resultSet.getString(ROLE));
				user.setRole(role);
				users.add(user);
			}
		} catch (SQLException e) {
			throw new RepositoryException(e);
		} finally {
			close(resultSet);
			close(statement);
			close(connection);
		}
		return users;
	}
}
