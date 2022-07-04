package by.lukyanov.finaltask.model.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.h2.tools.RunScript;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

class TestConnectionConfig {
    private static final Logger logger = LogManager.getLogger();

    private static final String SQL_SCHEMA_PATH = "test-db-structure.sql";
    private static final String SQL_DATA_PATH = "test-db-data.sql";
    private static final String PROP_DB_PATH = "test-db.properties";
    private static final String PROP_DB_URL = "url";
    private static final String PROP_DB_USER = "user";
    private static final String PROP_DB_PASS = "password";
    private static final String PROP_DB_DRIVER = "driver";
    private static final Properties properties = new Properties();

    private static final String DB_URL;
    private static final String DB_PASSWORD;
    private static final String DB_USER;
    private static final String DB_DRIVER;

    static {
        try (InputStream inputStream = TestConnectionConfig.class.getClassLoader()
                .getResourceAsStream(PROP_DB_PATH)) {
            properties.load(inputStream);
            DB_URL = properties.getProperty(PROP_DB_URL);
            DB_USER = properties.getProperty(PROP_DB_USER);
            DB_PASSWORD = properties.getProperty(PROP_DB_PASS);
            DB_DRIVER = properties.getProperty(PROP_DB_DRIVER);
            Class.forName(DB_DRIVER);
        } catch (IOException e) {
            logger.fatal("Error reading properties for db", e);
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            logger.fatal("Error register driver", e);
            throw new RuntimeException(e);
        }
    }

    Connection getConnection(){
        Connection connection;
        try {
            connection = DriverManager.getConnection(PROP_DB_URL, DB_USER, DB_PASSWORD);
            RunScript.execute(connection, new FileReader(SQL_SCHEMA_PATH));
        } catch (SQLException | FileNotFoundException e) {
            logger.fatal("Error while getting connection to db", e);
            throw new RuntimeException(e);
        }
        return connection;
    }

    void updateDatabase(Connection connection){
        try {
            RunScript.execute(connection, new FileReader(SQL_DATA_PATH));
        } catch (SQLException | FileNotFoundException e) {
            logger.fatal("Error while updating db", e);
            throw new RuntimeException(e);
        }
    }
}
