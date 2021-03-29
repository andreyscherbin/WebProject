package com.epam.forum.pool;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionCreator {
	private static Logger logger = LogManager.getLogger();
	private static final Properties properties = new Properties();
	private static final String DATABASE_URL;
	private static final String DB_URL = "db.url";
	private static final String DB_DRIVER = "db.driver";
	private static final String RESOURCE = "database.properties";
	static {
		String driverName = null;
		try (InputStream inputStream = ConnectionCreator.class.getClassLoader().getResourceAsStream(RESOURCE)) {
			properties.load(inputStream);
			driverName = (String) properties.get(DB_DRIVER);
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			logger.fatal("can't registrate driver {}, exception {}", driverName, e);
			throw new RuntimeException("fatal: can't registrate driver: " + driverName, e);
		} catch (IOException e) {
			logger.fatal("can't load properties", e);
			throw new RuntimeException("can't load properties: ", e);
		}
		DATABASE_URL = (String) properties.get(DB_URL);
	}

	private ConnectionCreator() {
	}

	public static Connection createConnection() throws SQLException {
		return DriverManager.getConnection(DATABASE_URL, properties);
	}
}
