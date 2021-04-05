package com.epam.forum.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionPool {

	private static Logger logger = LogManager.getLogger();
	private BlockingQueue<ProxyConnection> freeConnections;
	private Queue<ProxyConnection> usedConnections;
	private static final int INITIAL_POOL_SIZE = 10;
	private static ConnectionPool instance = null;

	private ConnectionPool() throws SQLException {
		usedConnections = new ArrayDeque<>();
		freeConnections = new LinkedBlockingDeque<>(INITIAL_POOL_SIZE);
		for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
			ProxyConnection proxyConnection = new ProxyConnection(ConnectionCreator.createConnection());
			freeConnections.offer(proxyConnection);
		}
	}

	public static ConnectionPool getInstance() throws SQLException {
		if (instance == null) {
			instance = new ConnectionPool();
		}
		return instance;
	}

	public Connection getConnection() {
		Connection connection = null; // fix me, why return null?
		try {
			connection = freeConnections.take();
			usedConnections.add((ProxyConnection) connection);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		logger.info("in use connections: {}, free connections: {}, summary: {}", usedConnections.size(),
				freeConnections.size(), getSize());
		return connection;
	}

	public boolean releaseConnection(Connection connection) {
		/*
		 * Class c; try { c = Class.forName("com.epam.forum.pool.ProxyConnection"); } //
		 * fix me, catch (ClassNotFoundException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } if (c != connection.getClass()) {
		 * 
		 * }
		 */
		usedConnections.remove(connection);
		return freeConnections.offer((ProxyConnection) connection);
	}

	public int getSize() {
		return freeConnections.size() + usedConnections.size();
	}

	public void shutdown() { // fix this, who closing connection pool?
		for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
			try {
				freeConnections.take().reallyClose();
			} catch (SQLException e) {
				e.printStackTrace(); // fix me
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		deregisterDrivers();
	}

	private void deregisterDrivers() {
		DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
			try {
				DriverManager.deregisterDriver(driver);
			} catch (SQLException e) {
				logger.error("error deregisterDriver with driver: {}", driver);
			}
		});
	}
}