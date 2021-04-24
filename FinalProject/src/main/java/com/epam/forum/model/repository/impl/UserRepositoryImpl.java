package com.epam.forum.model.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import com.epam.forum.exception.RepositoryException;
import com.epam.forum.model.entity.Role;
import com.epam.forum.model.entity.User;
import com.epam.forum.model.entity.UserTable;
import com.epam.forum.model.repository.Repository;
import com.epam.forum.model.repository.SearchCriterion;
import com.epam.forum.model.repository.Specification;
import com.epam.forum.pool.ConnectionPool;

import static com.epam.forum.model.entity.UserTable.*;

public class UserRepositoryImpl implements Repository<Long, User> {

	private static final String SQL_SELECT_ALL_USERS = "SELECT user_id, username, password, email, register_date, last_login_date, "
			+ "is_email_verifed, is_active, role FROM users";
	private static final String SQL_INSERT_USER = "INSERT INTO users (username, password, email, register_date, "
			+ "is_email_verifed, is_active, role) VALUES(?,?,?,?,?,?,?)";
	private static final String SQL_UPDATE_USER = "UPDATE users SET is_active = ? , role = ? , last_login_date = ? WHERE user_id = ?";

	@Override
	public Optional<User> find(Long id) throws RepositoryException {
		return null;
	}

	@Override
	public void create(User user) throws RepositoryException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		ConnectionPool pool = null;
		try {
			pool = ConnectionPool.getInstance();
			connection = pool.getConnection();
			statement = connection.prepareStatement(SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, user.getUserName());
			statement.setString(2, user.getPassword());
			statement.setString(3, user.getEmail());
			statement.setTimestamp(4, Timestamp.valueOf(user.getRegisterDate()));
			statement.setBoolean(5, user.isEmailVerifed());
			statement.setBoolean(6, user.isActive());
			statement.setString(7, user.getRole().name());
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new RepositoryException("Creating user failed, no rows affected.");
			}
			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					user.setId(generatedKeys.getLong(1));
				} else {
					throw new RepositoryException("Creating user failed, no ID obtained.");
				}
			}
		} catch (SQLException e) {
			throw new RepositoryException(e);
		} finally {
			close(resultSet);
			close(statement);
			close(connection);
		}
	}

	@Override
	public void update(User user) throws RepositoryException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		ConnectionPool pool = null;
		try {
			pool = ConnectionPool.getInstance();
			connection = pool.getConnection();
			statement = connection.prepareStatement(SQL_UPDATE_USER);
			statement.setBoolean(1, user.isActive());
			statement.setString(2, user.getRole().name());
			LocalDateTime lastLoginDate = user.getLastLoginDate();
			if (lastLoginDate == null) {
				statement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
			} else {
				statement.setTimestamp(3, Timestamp.valueOf(user.getLastLoginDate()));
			}
			statement.setLong(4, user.getId());
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new RepositoryException("updated user failed, no rows affected.");
			}
		} catch (SQLException e) {
			throw new RepositoryException(e);
		} finally {
			close(resultSet);
			close(statement);
			close(connection);
		}
	}

	@Override
	public void delete(User entity) throws RepositoryException {

	}

	@Override
	public List<User> query(Specification<User> specification) throws RepositoryException {
		List<SearchCriterion> criterions = specification.getSearchCriterions();
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
			for (SearchCriterion criterion : criterions) {
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
				user.setUserName(resultSet.getString(USERNAME));
				user.setPassword(resultSet.getString(PASSWORD));
				user.setEmail(resultSet.getString(EMAIL));
				user.setRegisterDate(resultSet.getTimestamp(REGISTER_DATE).toLocalDateTime());
				Timestamp lastLoginDate = resultSet.getTimestamp(UserTable.LAST_LOGIN_DATE);
				if (lastLoginDate != null) {
					user.setLastLoginDate(lastLoginDate.toLocalDateTime());
				}
				user.setEmailVerifed(resultSet.getBoolean(IS_EMAIL_VERIFED));
				user.setActive(resultSet.getBoolean(IS_ACTIVE));
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
				user.setUserName(resultSet.getString(USERNAME));
				user.setPassword(resultSet.getString(PASSWORD));
				user.setEmail(resultSet.getString(EMAIL));
				user.setRegisterDate(resultSet.getTimestamp(REGISTER_DATE).toLocalDateTime());
				Timestamp lastLoginDate = resultSet.getTimestamp(LAST_LOGIN_DATE);
				if (lastLoginDate != null) {
					user.setLastLoginDate(lastLoginDate.toLocalDateTime());
				}
				user.setEmailVerifed(resultSet.getBoolean(IS_EMAIL_VERIFED));
				user.setActive(resultSet.getBoolean(IS_ACTIVE));
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
