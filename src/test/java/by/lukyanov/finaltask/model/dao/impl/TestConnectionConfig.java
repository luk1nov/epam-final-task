package by.lukyanov.finaltask.model.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.h2.tools.RunScript;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

class TestConnectionConfig {
    private static final Logger logger = LogManager.getLogger();

    private static final String SQL_SCHEMA_PATH = "test-db-structure.sql";
    private static final String SQL_DATA_PATH = "test-db-data.sql";
    private static final Properties properties = new Properties();

    private static final String DB_URL;
    private static final String DB_PASSWORD;
    private static final String DB_USER;
    private static final String DB_DRIVER;

    static {
        try (InputStream inputStream = TestConnectionConfig.class.getClassLoader()
                .getResourceAsStream("test-db.properties")) {
            properties.load(inputStream);
            DB_URL = properties.getProperty("url");
            DB_USER = properties.getProperty("user");
            DB_PASSWORD = properties.getProperty("password");
            DB_DRIVER = properties.getProperty("driver");
            Class.forName(DB_DRIVER);
        } catch (IOException e) {
            logger.fatal("Error reading properties for db", e);
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            logger.fatal("Driver register error", e);
            throw new RuntimeException(e);
        }
    }

    Connection getConnection(){
        Connection connection;
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            ClassLoader classLoader = TestConnectionConfig.class.getClassLoader();
            URL url = classLoader.getResource(SQL_SCHEMA_PATH);
            RunScript.execute(connection, new FileReader(url.getPath()));
        } catch (SQLException | FileNotFoundException e) {
            logger.fatal("can not get connection t odb", e);
            throw new RuntimeException(e);
        }
        return connection;
    }

    void updateDatabase(Connection connection){
        try {
            ClassLoader classLoader = TestConnectionConfig.class.getClassLoader();
            URL url = classLoader.getResource(SQL_DATA_PATH);
            RunScript.execute(connection, new FileReader(url.getPath()));
        } catch (SQLException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
