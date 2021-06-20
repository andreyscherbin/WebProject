package com.epam.forum.model.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import com.epam.forum.exception.RepositoryException;
import com.epam.forum.model.entity.Role;
import com.epam.forum.model.entity.User;
import com.epam.forum.model.repository.Repository;
import com.epam.forum.model.repository.spec.SearchCriterion;
import com.epam.forum.model.repository.spec.Specification;
import com.epam.forum.pool.ConnectionPool;

import static com.epam.forum.model.entity.UserTable.*;

public class UserRepositoryImpl implements Repository<Long, User> {

	private static final String SQL_SELECT_ALL_USERS = "SELECT user_id, username, password, email, register_date, last_login_date, "
			+ "is_email_verifed, is_active, role FROM users";
	private static final String SQL_INSERT_USER = "INSERT INTO users (username, password, email, register_date, last_login_date, "
			+ "is_email_verifed, is_active, role) VALUES(?,?,?,?,?,?,?,?)";
	private static final String SQL_UPDATE_USER = "UPDATE users SET is_active = ? , is_email_verifed = ? , role = ? , last_login_date = ? WHERE user_id = ?";

	private static Repository<Long, User> instance;

	private UserRepositoryImpl() {
	}

	public static Repository<Long, User> getInstance() {
		if (instance == null) {
			instance = new UserRepositoryImpl();
		}
		return instance;
	}

	/**
	 * Not implemented operation.
	 *
	 * @throws UnsupportedOperationException always
	 * @deprecated Unsupported operation.
	 */
	@Deprecated
	@Override
	public Optional<User> findById(Long id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void create(User user) throws RepositoryException {
		ConnectionPool pool = ConnectionPool.getInstance();
		try (Connection connection = pool.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_INSERT_USER,
						Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, user.getUserName());
			statement.setString(2, user.getPassword());
			statement.setString(3, user.getEmail());
			statement.setTimestamp(4, Timestamp.valueOf(user.getRegisterDate()));
			statement.setTimestamp(5, Timestamp.valueOf(user.getLastLoginDate()));
			statement.setBoolean(6, user.isEmailVerifed());
			statement.setBoolean(7, user.isActive());
			statement.setString(8, user.getRole().name());
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
		}
	}

	@Override
	public void update(User user) throws RepositoryException {
		ConnectionPool pool = ConnectionPool.getInstance();
		try (Connection connection = pool.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER)) {
			statement.setBoolean(1, user.isActive());
			statement.setBoolean(2, user.isEmailVerifed());
			statement.setString(3, user.getRole().name());
			statement.setTimestamp(4, Timestamp.valueOf(user.getLastLoginDate()));
			statement.setLong(5, user.getId());
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new RepositoryException("updated user failed, no rows affected.");
			}
		} catch (SQLException e) {
			throw new RepositoryException(e);
		}
	}

	/**
	 * Not implemented operation.
	 *
	 * @throws UnsupportedOperationException always
	 * @deprecated Unsupported operation.
	 */
	@Deprecated
	@Override
	public void delete(User entity) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Iterable<User> query(Specification<User> specification) throws RepositoryException {
		List<SearchCriterion> criterions = specification.getSearchCriterions();
		List<User> users = new ArrayList<>();
		ConnectionPool pool = ConnectionPool.getInstance();
		try (Connection connection = pool.getConnection();
				PreparedStatement statement = connection.prepareStatement(specification.toSqlQuery())) {
			int parameterIndex = 1;
			for (SearchCriterion criterion : criterions) {
				String key = criterion.getKey();
				Object value = criterion.getValue();
				switch (key) {
				case USERNAME:
					statement.setString(parameterIndex, (String) value);
					break;
				case USER_ID:
					statement.setLong(parameterIndex, (Long) value);
					break;
				case EMAIL:
					statement.setString(parameterIndex, (String) value);
					break;
				default:
					throw new RepositoryException("no such parameter");
				}
				parameterIndex++;
			}
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					User user = new User();
					user.setId(resultSet.getLong(USER_ID));
					user.setUserName(resultSet.getString(USERNAME));
					user.setPassword(resultSet.getString(PASSWORD));
					user.setEmail(resultSet.getString(EMAIL));
					user.setRegisterDate(resultSet.getTimestamp(REGISTER_DATE).toLocalDateTime());
					user.setLastLoginDate(resultSet.getTimestamp(LAST_LOGIN_DATE).toLocalDateTime());
					user.setEmailVerifed(resultSet.getBoolean(IS_EMAIL_VERIFED));
					user.setActive(resultSet.getBoolean(IS_ACTIVE));
					Role role = Role.valueOf(resultSet.getString(ROLE));
					user.setRole(role);
					users.add(user);
				}
			}
		} catch (SQLException e) {
			throw new RepositoryException(e);
		}
		return users;
	}

	/**
	 * Not implemented operation.
	 *
	 * @throws UnsupportedOperationException always
	 * @deprecated Unsupported operation.
	 */
	@Deprecated
	@Override
	public Iterable<User> sort(Comparator<User> comparator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Iterable<User> findAll() throws RepositoryException {
		List<User> users = new ArrayList<>();
		ConnectionPool pool = ConnectionPool.getInstance();
		try (Connection connection = pool.getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_USERS)) {
			while (resultSet.next()) {
				User user = new User();
				user.setId(resultSet.getLong(USER_ID));
				user.setUserName(resultSet.getString(USERNAME));
				user.setPassword(resultSet.getString(PASSWORD));
				user.setEmail(resultSet.getString(EMAIL));
				user.setRegisterDate(resultSet.getTimestamp(REGISTER_DATE).toLocalDateTime());
				user.setLastLoginDate(resultSet.getTimestamp(LAST_LOGIN_DATE).toLocalDateTime());
				user.setEmailVerifed(resultSet.getBoolean(IS_EMAIL_VERIFED));
				user.setActive(resultSet.getBoolean(IS_ACTIVE));
				Role role = Role.valueOf(resultSet.getString(ROLE));
				user.setRole(role);
				users.add(user);
			}
		} catch (SQLException e) {
			throw new RepositoryException(e);
		}
		return users;
	}
}
