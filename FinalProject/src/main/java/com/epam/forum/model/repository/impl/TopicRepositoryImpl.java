package com.epam.forum.model.repository.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.epam.forum.exception.RepositoryException;
import com.epam.forum.model.entity.Topic;
import com.epam.forum.model.entity.TopicTable;
import com.epam.forum.model.repository.Repository;
import com.epam.forum.model.repository.Specification;
import com.epam.forum.pool.ConnectionPool;

public class TopicRepositoryImpl implements Repository<Long, Topic> {

	private static final String SQL_SELECT_ALL_TOPICS = "SELECT topic_id, header, content, is_pinned, is_closed, creation_date, section_id, user_id "
			+ "FROM topics";

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
		// TODO Auto-generated method stub
		return null;
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
				topic.setTopicId(resultSet.getLong(TopicTable.TOPIC_ID));
				topic.setHeader(resultSet.getString(TopicTable.HEADER));
				// add others columns
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
