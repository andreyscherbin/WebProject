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
import com.epam.forum.model.entity.Post;
import com.epam.forum.model.entity.PostTable;
import com.epam.forum.model.entity.Role;
import com.epam.forum.model.entity.Topic;
import com.epam.forum.model.entity.TopicTable;
import com.epam.forum.model.entity.User;
import com.epam.forum.model.entity.UserTable;
import com.epam.forum.model.repository.Repository;
import com.epam.forum.model.repository.SearchCriterion;
import com.epam.forum.model.repository.Specification;
import com.epam.forum.pool.ConnectionPool;

public class PostRepositoryImpl implements Repository<Long, Post> {

	private static final String SQL_SELECT_ALL_POSTS = "SELECT posts.post_id, posts.content, posts.creation_date, topics.topic_id, topics.header, topics.content, topics.is_pinned, topics.is_closed, topics.creation_date, users.user_id, users.username, users.password, users.email, users.register_date, users.last_login_date, "
			+ "users.is_email_verifed, users.is_active, users.role "
			+ "FROM posts JOIN topics ON posts.topic_id = topics.topic_id JOIN users ON posts.user_id = users.user_id ";
	private static final String SQL_INSERT_POST = "INSERT INTO posts (content, creation_date, topic_id, user_id) VALUES(?,?,?,?)";
	private static final String SQL_DELETE_POST = "DELETE FROM posts WHERE post_id = ?";
	private static final String SQL_UPDATE_POST = "UPDATE posts SET content = ? WHERE post_id = ?";

	@Override
	public Optional<Post> findById(Long id) throws RepositoryException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void create(Post post) throws RepositoryException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		ConnectionPool pool = null;
		try {
			pool = ConnectionPool.getInstance();
			connection = pool.getConnection();
			statement = connection.prepareStatement(SQL_INSERT_POST);
			statement.setString(1, post.getContent());
			statement.setTimestamp(2, Timestamp.valueOf(post.getCreationDate()));
			statement.setLong(3, post.getTopic().getId());
			statement.setLong(4, post.getUser().getId());
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new RepositoryException("Creating post failed, no rows affected.");
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
	public void update(Post post) throws RepositoryException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		ConnectionPool pool = null;
		try {
			pool = ConnectionPool.getInstance();
			connection = pool.getConnection();
			statement = connection.prepareStatement(SQL_UPDATE_POST);
			statement.setString(1, post.getContent());
			statement.setLong(2, post.getId());
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new RepositoryException("updated post failed, no rows affected.");
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
	public void delete(Post post) throws RepositoryException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		ConnectionPool pool = null;
		try {
			pool = ConnectionPool.getInstance();
			connection = pool.getConnection();
			statement = connection.prepareStatement(SQL_DELETE_POST);
			statement.setLong(1, post.getId());
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new RepositoryException("Deleting post failed, no rows affected.");
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
	public Iterable<Post> query(Specification<Post> specification) throws RepositoryException {
		List<SearchCriterion> criterions = specification.getSearchCriterions();
		List<Post> posts = new ArrayList<>();
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
				if (key.equals(PostTable.POST_ID)) {
					statement.setLong(i, (Long) value);
				} else if (key.equals(PostTable.TOPIC_ID)) {
					statement.setLong(i, (Long) value);
				} else if (key.equals(PostTable.USER_ID)) {
					statement.setLong(i, (Long) value);
				}
				i++;
			}
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Post post = new Post();
				Topic topic = new Topic();
				User user = new User();
				post.setId(resultSet.getLong(PostTable.POST_ID));
				post.setContent(resultSet.getString(PostTable.CONTENT));
				post.setCreationDate(resultSet.getTimestamp(PostTable.CREATION_DATE).toLocalDateTime());
				topic.setId(resultSet.getLong(TopicTable.TOPIC_ID));
				topic.setHeader(resultSet.getString(TopicTable.HEADER));
				topic.setContent(resultSet.getString(TopicTable.CONTENT));
				topic.setPinned(resultSet.getBoolean(TopicTable.IS_PINNED));
				topic.setClosed(resultSet.getBoolean(TopicTable.IS_CLOSED));
				topic.setCreationDate(resultSet.getTimestamp(TopicTable.CREATION_DATE).toLocalDateTime());
				post.setTopic(topic);
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
				post.setUser(user);
				posts.add(post);
			}
		} catch (SQLException e) {
			throw new RepositoryException(e);
		} finally {
			close(resultSet);
			close(statement);
			close(connection);
		}
		return posts;
	}

	@Override
	public Iterable<Post> sort(Comparator<Post> comparator) throws RepositoryException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Iterable<Post> findAll() throws RepositoryException {
		List<Post> posts = new ArrayList<>();
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		ConnectionPool pool = null;
		try {
			pool = ConnectionPool.getInstance();
			connection = pool.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(SQL_SELECT_ALL_POSTS);
			while (resultSet.next()) {
				Post post = new Post();
				Topic topic = new Topic();
				User user = new User();
				post.setId(resultSet.getLong(PostTable.POST_ID));
				post.setContent(resultSet.getString(PostTable.CONTENT));
				post.setCreationDate(resultSet.getTimestamp(PostTable.CREATION_DATE).toLocalDateTime());
				topic.setId(resultSet.getLong(TopicTable.TOPIC_ID));
				topic.setHeader(resultSet.getString(TopicTable.HEADER));
				topic.setContent(resultSet.getString(TopicTable.CONTENT));
				topic.setPinned(resultSet.getBoolean(TopicTable.IS_PINNED));
				topic.setClosed(resultSet.getBoolean(TopicTable.IS_CLOSED));
				topic.setCreationDate(resultSet.getTimestamp(TopicTable.CREATION_DATE).toLocalDateTime());
				post.setTopic(topic);
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
				post.setUser(user);
				posts.add(post);
			}
		} catch (SQLException e) {
			throw new RepositoryException(e);
		} finally {
			close(resultSet);
			close(statement);
			close(connection);
		}
		return posts;
	}
}
