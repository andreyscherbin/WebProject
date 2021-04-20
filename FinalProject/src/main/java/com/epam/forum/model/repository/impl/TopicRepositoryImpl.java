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
import com.epam.forum.model.entity.Topic;
import com.epam.forum.model.repository.Repository;
import com.epam.forum.model.repository.SearchCriterion;
import com.epam.forum.model.repository.Specification;
import com.epam.forum.pool.ConnectionPool;

import com.epam.forum.model.entity.TopicTable;
import com.epam.forum.model.entity.User;
import com.epam.forum.model.entity.UserTable;
import com.epam.forum.model.entity.Role;
import com.epam.forum.model.entity.Section;
import com.epam.forum.model.entity.SectionTable;

public class TopicRepositoryImpl implements Repository<Long, Topic> {

	private static final String SQL_SELECT_ALL_TOPICS = "SELECT topics.topic_id, topics.header, topics.content, topics.is_pinned, topics.is_closed, topics.creation_date, sections.section_id, sections.header, sections.description, users.user_id, users.username, users.password, users.email, users.register_date, users.last_login_date, "
			+ "users.is_email_verifed, users.is_active, users.role "
			+ "FROM topics JOIN sections ON topics.section_id = sections.section_id JOIN users ON topics.user_id = users.user_id ";

	@Override
	public Optional<Topic> find(Long id) throws RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void create(Topic entity) throws RepositoryException {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Topic entity) throws RepositoryException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Topic entity) throws RepositoryException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Topic> query(Specification<Topic> specification) throws RepositoryException {
		List<SearchCriterion> criterions = specification.getSearchCriterions();
		List<Topic> topics = new ArrayList<>();
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
				if (key.equals(TopicTable.HEADER)) {
					statement.setString(i, (String) value);
				} else if (key.equals(TopicTable.TOPIC_ID)) {
					statement.setLong(i, (Long) value);
				} else if (key.equals(TopicTable.SECTION_ID)) {
					statement.setLong(i, (Long) value);
				}
				i++;
			}
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Topic topic = new Topic();
				Section section = new Section();
				User user = new User();
				topic.setId(resultSet.getLong(TopicTable.TOPIC_ID));
				topic.setHeader(resultSet.getString(TopicTable.HEADER));
				topic.setContent(resultSet.getString(TopicTable.CONTENT));
				topic.setPinned(resultSet.getBoolean(TopicTable.IS_PINNED));
				topic.setClosed(resultSet.getBoolean(TopicTable.IS_CLOSED));
				topic.setCreationDate(resultSet.getTimestamp(TopicTable.CREATION_DATE).toLocalDateTime());
				section.setId(resultSet.getLong(SectionTable.SECTION_ID));
				section.setHeader(resultSet.getString(SectionTable.HEADER));
				section.setDescription(resultSet.getString(SectionTable.DESCRIPTION));
				topic.setSection(section);
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
				topic.setUser(user);
				topics.add(topic);
			}
		} catch (SQLException e) {
			throw new RepositoryException(e);
		} finally {
			close(resultSet);
			close(statement);
			close(connection);
		}
		return topics;
	}

	@Override
	public List<Topic> sort(Comparator<Topic> comparator) throws RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Topic> findAll() throws RepositoryException {
		List<Topic> topics = new ArrayList<>();
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		ConnectionPool pool = null;
		try {
			pool = ConnectionPool.getInstance();
			connection = pool.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(SQL_SELECT_ALL_TOPICS);
			while (resultSet.next()) {
				Topic topic = new Topic();
				Section section = new Section();
				User user = new User();
				topic.setId(resultSet.getLong(TopicTable.TOPIC_ID));
				topic.setHeader(resultSet.getString(TopicTable.HEADER));
				topic.setContent(resultSet.getString(TopicTable.CONTENT));
				topic.setPinned(resultSet.getBoolean(TopicTable.IS_PINNED));
				topic.setClosed(resultSet.getBoolean(TopicTable.IS_CLOSED));
				topic.setCreationDate(resultSet.getTimestamp(TopicTable.CREATION_DATE).toLocalDateTime());
				section.setId(resultSet.getLong(SectionTable.SECTION_ID));
				section.setHeader(resultSet.getString(SectionTable.HEADER));
				section.setDescription(resultSet.getString(SectionTable.DESCRIPTION));
				topic.setSection(section);
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
				topic.setUser(user);
				topics.add(topic);
			}
		} catch (SQLException e) {
			throw new RepositoryException(e);
		} finally {
			close(resultSet);
			close(statement);
			close(connection);
		}
		return topics;
	}
}