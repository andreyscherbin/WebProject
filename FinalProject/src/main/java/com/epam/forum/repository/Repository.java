package com.epam.forum.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.forum.exception.RepositoryException;
import com.epam.forum.model.entity.Entity;

public interface Repository<K, T extends Entity> {
	public static Logger logger = LogManager.getLogger();

	Optional<T> find(K id) throws RepositoryException;

	void create(T entity) throws RepositoryException;

	void update(T entity) throws RepositoryException;

	void delete(T entity) throws RepositoryException;

	List<T> query(Specification<T> specification) throws RepositoryException;

	List<T> sort(Comparator<T> comparator) throws RepositoryException;

	List<T> findAll() throws RepositoryException;

	default void close(Statement statement) {
		try {
			if (statement != null) {
				statement.close();
			}
		} catch (SQLException e) {
			logger.error("statement close error {}", e);
		}
	}
	
	default void close(ResultSet resultSet) {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
		} catch (SQLException e) {
			logger.error("resultSet close error {}", e);
		}
	}
}