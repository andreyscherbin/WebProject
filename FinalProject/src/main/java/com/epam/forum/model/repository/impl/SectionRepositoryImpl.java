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
import com.epam.forum.model.entity.Section;
import com.epam.forum.model.repository.Repository;
import com.epam.forum.model.repository.SearchCriterion;
import com.epam.forum.model.repository.Specification;
import com.epam.forum.pool.ConnectionPool;

import static com.epam.forum.model.entity.SectionTable.*;

public class SectionRepositoryImpl implements Repository<Long, Section> {

	private static final String SQL_SELECT_ALL_SECTIONS = "SELECT section_id, header, description FROM sections";
	private static final String SQL_INSERT_SECTION = "INSERT INTO sections (header, description) VALUES(?,?)";
	private static final String SQL_DELETE_SECTION = "DELETE FROM sections WHERE section_id = ?";

	@Override
	public Optional<Section> findById(Long id) throws RepositoryException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void create(Section section) throws RepositoryException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		ConnectionPool pool = null;
		try {
			pool = ConnectionPool.getInstance();
			connection = pool.getConnection();
			statement = connection.prepareStatement(SQL_INSERT_SECTION);
			statement.setString(1, section.getHeader());
			statement.setString(2, section.getDescription());
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new RepositoryException("Creating section failed, no rows affected.");
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
	public void update(Section entity) throws RepositoryException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void delete(Section section) throws RepositoryException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		ConnectionPool pool = null;
		try {
			pool = ConnectionPool.getInstance();
			connection = pool.getConnection();
			statement = connection.prepareStatement(SQL_DELETE_SECTION);
			statement.setLong(1, section.getId());
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new RepositoryException("Deleting section failed, no rows affected.");
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
	public Iterable<Section> query(Specification<Section> specification) throws RepositoryException {
		List<SearchCriterion> criterions = specification.getSearchCriterions();
		List<Section> sections = new ArrayList<>();
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
				if (key.equals(HEADER)) {
					statement.setString(i, (String) value);
				} else if (criterion.getKey().equals(SECTION_ID)) {
					statement.setLong(i, (Long) value);
				} else if (criterion.getKey().equals(DESCRIPTION)) {
					statement.setString(i, (String) value);
				}
				i++;
			}
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Section section = new Section();
				section.setId(resultSet.getLong(SECTION_ID));
				section.setHeader(resultSet.getString(HEADER));
				section.setDescription(resultSet.getString(DESCRIPTION));
				sections.add(section);
			}
		} catch (SQLException e) {
			throw new RepositoryException(e);
		} finally {
			close(resultSet);
			close(statement);
			close(connection);
		}
		return sections;
	}

	@Override
	public Iterable<Section> sort(Comparator<Section> comparator) throws RepositoryException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Iterable<Section> findAll() throws RepositoryException {
		List<Section> sections = new ArrayList<>();
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		ConnectionPool pool = null;
		try {
			pool = ConnectionPool.getInstance();
			connection = pool.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(SQL_SELECT_ALL_SECTIONS);
			while (resultSet.next()) {
				Section section = new Section();
				section.setId(resultSet.getLong(SECTION_ID));
				section.setHeader(resultSet.getString(HEADER));
				section.setDescription(resultSet.getString(DESCRIPTION));
				sections.add(section);
			}
		} catch (SQLException e) {
			throw new RepositoryException(e);
		} finally {
			close(resultSet);
			close(statement);
			close(connection);
		}
		return sections;
	}
}
