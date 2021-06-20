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

	/**
	 * Not implemented operation.
	 *
	 * @throws UnsupportedOperationException always
	 * @deprecated Unsupported operation.
	 */
	@Deprecated
	@Override
	public Optional<ActivationCode> findById(String id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void create(ActivationCode activationCode) throws RepositoryException {
		ConnectionPool pool = ConnectionPool.getInstance();
		try (Connection connection = pool.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_INSERT_ACTIVATION_CODE)) {
			statement.setString(1, activationCode.getId());
			statement.setLong(2, activationCode.getUser().getId());
			statement.setTimestamp(3, Timestamp.valueOf(activationCode.getCreationDate()));
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new RepositoryException("Creating activationCode failed, no rows affected.");
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
	public void update(ActivationCode entity) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void delete(ActivationCode activationCode) throws RepositoryException {
		ConnectionPool pool = ConnectionPool.getInstance();
		try (Connection connection = pool.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_DELETE_ACTIVATION_CODE);) {
			statement.setString(1, activationCode.getId());
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new RepositoryException("Deleting activationCode failed, no rows affected.");
			}
		} catch (SQLException e) {
			throw new RepositoryException(e);
		}
	}

	@Override
	public Iterable<ActivationCode> query(Specification<ActivationCode> specification) throws RepositoryException {
		List<SearchCriterion> criterions = specification.getSearchCriterions();
		List<ActivationCode> codes = new ArrayList<>();
		ConnectionPool pool = ConnectionPool.getInstance();
		try (Connection connection = pool.getConnection();
				PreparedStatement statement = connection.prepareStatement(specification.toSqlQuery())) {
			int parameterIndex = 1;			
			for (SearchCriterion criterion : criterions) {
				String key = criterion.getKey();
				Object value = criterion.getValue();
				switch (key) {
				case ActivationCodeTable.ACTIVATION_CODE_ID:
					statement.setString(parameterIndex, (String) value);
					break;
				default:
					throw new RepositoryException("no such parameter");
				}
				parameterIndex++;
			}
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					ActivationCode activationCode = new ActivationCode();
					activationCode.setId(resultSet.getString(ActivationCodeTable.ACTIVATION_CODE_ID));
					activationCode.setCreationDate(
							resultSet.getTimestamp(ActivationCodeTable.CREATION_DATE).toLocalDateTime());
					User user = new User();
					user.setId(resultSet.getLong(UserTable.USER_ID));
					user.setUserName(resultSet.getString(UserTable.USERNAME));
					user.setPassword(resultSet.getString(UserTable.PASSWORD));
					user.setEmail(resultSet.getString(UserTable.EMAIL));
					user.setRegisterDate(resultSet.getTimestamp(UserTable.REGISTER_DATE).toLocalDateTime());
					user.setLastLoginDate(resultSet.getTimestamp(UserTable.LAST_LOGIN_DATE).toLocalDateTime());
					user.setEmailVerifed(resultSet.getBoolean(UserTable.IS_EMAIL_VERIFED));
					user.setActive(resultSet.getBoolean(UserTable.IS_ACTIVE));
					Role role = Role.valueOf(resultSet.getString(UserTable.ROLE));
					user.setRole(role);
					activationCode.setUser(user);
					codes.add(activationCode);
				}
			}
		} catch (SQLException e) {
			throw new RepositoryException(e);
		}
		return codes;
	}

	/**
	 * Not implemented operation.
	 *
	 * @throws UnsupportedOperationException always
	 * @deprecated Unsupported operation.
	 */
	@Deprecated
	@Override
	public Iterable<ActivationCode> sort(Comparator<ActivationCode> comparator) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Not implemented operation.
	 *
	 * @throws UnsupportedOperationException always
	 * @deprecated Unsupported operation.
	 */
	@Deprecated
	@Override
	public Iterable<ActivationCode> findAll() {
		throw new UnsupportedOperationException();
	}
}
