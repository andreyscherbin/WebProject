package com.epam.forum.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;

public class ConnectionPool {

	private static Logger logger = LogManager.getLogger();
	private static final int DEFAULT_POOL_SIZE = 8;
	private static ConnectionPool instance;
	private static ReentrantLock lock = new ReentrantLock();
	private static AtomicBoolean isPoolCreated = new AtomicBoolean(false);
	private BlockingDeque<ProxyConnection> idleConnections;
	private BlockingDeque<ProxyConnection> usedConnections;

	private ConnectionPool() {
		usedConnections = new LinkedBlockingDeque<>(DEFAULT_POOL_SIZE);
		idleConnections = new LinkedBlockingDeque<>(DEFAULT_POOL_SIZE);
		for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
			try {
				Connection connection = ConnectionFactory.createConnection();
				ProxyConnection proxyConnection = new ProxyConnection(connection);
				idleConnections.offer(proxyConnection);
			} catch (SQLException e) {
				logger.error("can't create connection with exception: {}", e);
			}
		}
		if (idleConnections.isEmpty()) {
			logger.fatal("can't create connections, empty pool");
			throw new RuntimeException("can't create connections, empty pool");
		} else if (idleConnections.size() == DEFAULT_POOL_SIZE) {
			logger.info("pool successfully created");
		} else if (idleConnections.size() > 0 && idleConnections.size() < DEFAULT_POOL_SIZE) {
			logger.info("not full pool, need create others connections");
		}
	}

	public static ConnectionPool getInstance() {
		if (!isPoolCreated.get()) {
			lock.lock();
			if (!isPoolCreated.get()) {
				instance = new ConnectionPool();
				isPoolCreated.getAndSet(true);
			}
			lock.unlock();
		}
		return instance;
	}

	public Connection getConnection() {
		ProxyConnection proxyConnection = null;
		try {
			proxyConnection = idleConnections.take();
			usedConnections.put(proxyConnection);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		return proxyConnection;
	}

	public void releaseConnection(Connection connection) {
		if (!(connection instanceof ProxyConnection)) {
			logger.error("wild connection is detected: {}", connection);
			throw new RuntimeException("wild connection is detected : " + connection);
		}
		usedConnections.remove(connection);
		try {
			idleConnections.put((ProxyConnection) connection);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	public boolean isLeakConnections() {
		return idleConnections.size() + usedConnections.size() != DEFAULT_POOL_SIZE;
	}

	public void shutdown() {
		ProxyConnection proxyConnection = null;
		for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
			try {
				proxyConnection = idleConnections.take();
				proxyConnection.reallyClose();
			} catch (SQLException e) {
				logger.fatal("can't close connection {} with exception {}", proxyConnection, e);
				throw new RuntimeException("can't close connection " + proxyConnection + " with exception " + e);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		logger.info("pool successfully destroyed");
		deregisterDrivers();
	}

	private void deregisterDrivers() {
		DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
			try {
				DriverManager.deregisterDriver(driver);
			} catch (SQLException e) {
				logger.error("error deregisterDriver with driver: {} with exception {}", driver, e);
			}
		});
		AbandonedConnectionCleanupThread.checkedShutdown();
	}
}