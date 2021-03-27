package com.epam.forum.model.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.forum.exception.DaoException;
import com.epam.forum.model.entity.Entity;

public interface BaseDao<K, T extends Entity> {	
	public static Logger logger = LogManager.getLogger();
			
	List<T> findAll() throws DaoException;
	T findEntityById(K id) throws DaoException;
	boolean delete(T t) throws DaoException;
	boolean delete(K id) throws DaoException;
	boolean create(T t) throws DaoException;
	T update(T t) throws DaoException;

	default void close(Statement statement) {
		try {
			if (statement != null) {
				statement.close();
			}
		} catch (SQLException e) {
			logger.error("statement close error {}", e);
		}
	}

	default void close(Connection connection) {
		try {
			if (connection != null) {
				connection.close(); // or connection return code to the pool
			}
		} catch (SQLException e) {
			logger.error("statement close error {}", e);
		}
	}
}
