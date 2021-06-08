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
import com.epam.forum.model.repository.spec.SearchCriterion;
import com.epam.forum.model.repository.spec.Specification;
import com.epam.forum.pool.ConnectionPool;
import static com.epam.forum.model.entity.SectionTable.*;

public class SectionRepositoryImpl implements Repository<Long, Section> {

	private static final String SQL_SELECT_ALL_SECTIONS = "SELECT section_id, header, description FROM sections";
	private static final String SQL_INSERT_SECTION = "INSERT INTO sections (header, description) VALUES(?,?)";
	private static final String SQL_DELETE_SECTION = "DELETE FROM sections WHERE section_id = ?";

	private static Repository<Long, Section> instance;

	private SectionRepositoryImpl() {
	}

	public static Repository<Long, Section> getInstance() {
		if (instance == null) {
			instance = new SectionRepositoryImpl();
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
	public Optional<Section> findById(Long id) throws RepositoryException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void create(Section section) throws RepositoryException {

		ConnectionPool pool = ConnectionPool.getInstance();
		try (Connection connection = pool.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_INSERT_SECTION)) {
			statement.setString(1, section.getHeader());
			statement.setString(2, section.getDescription());
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new RepositoryException("Creating section failed, no rows affected.");
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
	public void update(Section entity) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void delete(Section section) throws RepositoryException {
		ConnectionPool pool = ConnectionPool.getInstance();
		try (Connection connection = pool.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_DELETE_SECTION)) {
			statement.setLong(1, section.getId());
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new RepositoryException("Deleting section failed, no rows affected.");
			}
		} catch (SQLException e) {
			throw new RepositoryException(e);
		}
	}

	@Override
	public Iterable<Section> query(Specification<Section> specification) throws RepositoryException {
		List<SearchCriterion> criterions = specification.getSearchCriterions();
		List<Section> sections = new ArrayList<>();
		ConnectionPool pool = ConnectionPool.getInstance();
		try (Connection connection = pool.getConnection();
				PreparedStatement statement = connection.prepareStatement(specification.toSqlQuery())) {
			int parameterIndex = 1;
			for (SearchCriterion criterion : criterions) {
				String key = criterion.getKey();
				Object value = criterion.getValue();
				switch (key) {
				case HEADER:
					statement.setString(parameterIndex, (String) value);
					break;
				case SECTION_ID:
					statement.setLong(parameterIndex, (Long) value);
					break;
				case DESCRIPTION:
					statement.setString(parameterIndex, (String) value);
					break;
				default:
					throw new RepositoryException("no such parameter");
				}
				parameterIndex++;
			}
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					Section section = new Section();
					section.setId(resultSet.getLong(SECTION_ID));
					section.setHeader(resultSet.getString(HEADER));
					section.setDescription(resultSet.getString(DESCRIPTION));
					sections.add(section);
				}
			}
		} catch (SQLException e) {
			throw new RepositoryException(e);
		}
		return sections;
	}

	/**
	 * Not implemented operation.
	 *
	 * @throws UnsupportedOperationException always
	 * @deprecated Unsupported operation.
	 */
	@Deprecated
	@Override
	public Iterable<Section> sort(Comparator<Section> comparator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Iterable<Section> findAll() throws RepositoryException {
		List<Section> sections = new ArrayList<>();
		ConnectionPool pool = ConnectionPool.getInstance();
		try (Connection connection = pool.getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_SECTIONS)) {
			while (resultSet.next()) {
				Section section = new Section();
				section.setId(resultSet.getLong(SECTION_ID));
				section.setHeader(resultSet.getString(HEADER));
				section.setDescription(resultSet.getString(DESCRIPTION));
				sections.add(section);
			}
		} catch (SQLException e) {
			throw new RepositoryException(e);
		}
		return sections;
	}
}
