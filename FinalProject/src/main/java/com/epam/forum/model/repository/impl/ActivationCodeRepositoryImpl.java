package com.epam.forum.model.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import com.epam.forum.exception.RepositoryException;
import com.epam.forum.model.entity.ActivationCode;
import com.epam.forum.model.entity.Role;
import com.epam.forum.model.entity.User;
import com.epam.forum.model.entity.UserTable;
import com.epam.forum.model.repository.Repository;
import com.epam.forum.model.repository.spec.SearchCriterion;
import com.epam.forum.model.repository.spec.Specification;
import com.epam.forum.pool.ConnectionPool;
import com.epam.forum.model.entity.ActivationCodeTable;

public class ActivationCodeRepositoryImpl implements Repository<String, ActivationCode> {

	private static final String SQL_INSERT_ACTIVATION_CODE = "INSERT INTO activationcodes (activationcode_id, user_id, creation_date) VALUES(?,?,?)";
	private static final String SQL_DELETE_ACTIVATION_CODE = "DELETE FROM activationcodes WHERE activationcode_id = ?";

	private static Repository<String, ActivationCode> instance;

	private ActivationCodeRepositoryImpl() {
	}

	public static Repository<String, ActivationCode> getInstance() {
		if (instance == null) {
			instance = new ActivationCodeRepositoryImpl();
		}
		return instance;
	}

	@Override
	public Optional<ActivationCode> findById(String id) throws RepositoryException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void create(ActivationCode activationCode) throws RepositoryException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		ConnectionPool pool = null;
		try {
			pool = ConnectionPool.getInstance();
			connection = pool.getConnection();
			statement = connection.prepareStatement(SQL_INSERT_ACTIVATION_CODE);
			statement.setString(1, activationCode.getId());
			statement.setLong(2, activationCode.getUser().getId());
			statement.setTimestamp(3, Timestamp.valueOf(activationCode.getCreationDate()));
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new RepositoryException("Creating activationCode failed, no rows affected.");
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
	public void update(ActivationCode entity) throws RepositoryException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void delete(ActivationCode activationCode) throws RepositoryException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		ConnectionPool pool = null;
		try {
			pool = ConnectionPool.getInstance();
			connection = pool.getConnection();
			statement = connection.prepareStatement(SQL_DELETE_ACTIVATION_CODE);
			statement.setString(1, activationCode.getId());
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new RepositoryException("Deleting activationCode failed, no rows affected.");
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
	public Iterable<ActivationCode> query(Specification<ActivationCode> specification) throws RepositoryException {
		List<SearchCriterion> criterions = specification.getSearchCriterions();
		List<ActivationCode> codes = new ArrayList<>();
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
				if (key.equals(ActivationCodeTable.ACTIVATION_CODE_ID)) {
					statement.setString(i, (String) value);
				}
			}
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				ActivationCode activationCode = new ActivationCode();
				activationCode.setId(resultSet.getString(ActivationCodeTable.ACTIVATION_CODE_ID));
				activationCode
						.setCreationDate(resultSet.getTimestamp(ActivationCodeTable.CREATION_DATE).toLocalDateTime());
				User user = new User();
				user.setId(resultSet.getLong(UserTable.USER_ID));
				user.setUserName(resultSet.getString(UserTable.USERNAME));
				user.setPassword(resultSet.getString(UserTable.PASSWORD));
				user.setEmail(resultSet.getString(UserTable.EMAIL));
				user.setRegisterDate(resultSet.getTimestamp(UserTable.REGISTER_DATE).toLocalDateTime());
				Timestamp lastLoginDate = resultSet.getTimestamp(UserTable.LAST_LOGIN_DATE);
				if (lastLoginDate != null) {
					user.setLastLoginDate(lastLoginDate.toLocalDateTime());
				}
				user.setEmailVerifed(resultSet.getBoolean(UserTable.IS_EMAIL_VERIFED));
				user.setActive(resultSet.getBoolean(UserTable.IS_ACTIVE));
				Role role = Role.valueOf(resultSet.getString(UserTable.ROLE));
				user.setRole(role);
				activationCode.setUser(user);
				codes.add(activationCode);
			}
		} catch (SQLException e) {
			throw new RepositoryException(e);
		} finally {
			close(resultSet);
			close(statement);
			close(connection);
		}
		return codes;
	}

	@Override
	public Iterable<ActivationCode> sort(Comparator<ActivationCode> comparator) throws RepositoryException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Iterable<ActivationCode> findAll() throws RepositoryException {
		throw new UnsupportedOperationException();
	}
}
