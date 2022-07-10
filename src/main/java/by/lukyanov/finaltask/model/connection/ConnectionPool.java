package by.lukyanov.finaltask.model.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static final Logger logger = LogManager.getLogger();
    private static final Lock locker = new ReentrantLock();
    private static final int POOL_SIZE = 8;
    private static final AtomicBoolean isInstanceInitialized = new AtomicBoolean(false);
    private static final String PROP_DB_PATH = "database.properties";
    private static final String PROP_DB_URL = "db.url";
    private static final String PROP_DB_USER = "db.user";
    private static final String PROP_DB_PASS = "db.password";
    private static final String PROP_DB_DRIVER = "db.driver";

    private static final String DB_URL;
    private static final String DB_PASSWORD;
    private static final String DB_USER;
    private static final String DB_DRIVER;
    private static final Properties properties = new Properties();
    private static ConnectionPool instance;
    private final BlockingQueue<ProxyConnection> freeConnections;
    private final BlockingQueue<ProxyConnection> usedConnections;

    static {
        try (InputStream inputStream = ConnectionPool.class.getClassLoader()
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
            logger.fatal("Driver register error", e);
            throw new RuntimeException(e);
        }
    }

    private ConnectionPool() {
        freeConnections = new LinkedBlockingQueue<>(POOL_SIZE);
        usedConnections = new LinkedBlockingQueue<>(POOL_SIZE);
        try {
            for (int i = 0; i < POOL_SIZE; i++) {
                createConnection();
            }
            if(freeConnections.size() != POOL_SIZE){
                logger.warn("Connection pool not full filled. Trying again.");
                for (int i = freeConnections.size(); i < POOL_SIZE; i++) {
                    createConnection();
                }
            }
        } catch (InterruptedException | SQLException e) {
            logger.error("connection didn't created again", e);
            throw new RuntimeException(e);
        }
        logger.info("Connection pool init " + freeConnections.size());
    }

    public static ConnectionPool getInstance(){
        if(instance == null){
            try {
                locker.lock();
                if(isInstanceInitialized.compareAndSet(false, true)){
                    instance = new ConnectionPool();
                }
            } finally {
                locker.unlock();
            }
        }
        return instance;
    }

    private Connection createConnection() throws SQLException, InterruptedException {
        Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        ProxyConnection proxyConnection = new ProxyConnection(connection);
        freeConnections.put(proxyConnection);
        return connection;
    }

    public Connection getConnection(){
        ProxyConnection connection = null;
        try {
            connection = freeConnections.take();
            usedConnections.put(connection);
        } catch (InterruptedException e) {
            logger.error("Interrupted exception in get connection method", e);
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    public boolean releaseConnection(Connection connection){
        boolean isReleased = false;
        if(connection instanceof ProxyConnection proxyConnection){
            try {
                usedConnections.remove(proxyConnection);
                freeConnections.put(proxyConnection);
                isReleased = true;
            } catch (InterruptedException e) {
                logger.error("Interrupted exception in release connection method", e);
                Thread.currentThread().interrupt();
            }
        }
        return isReleased;
    }

    public void destroyPool(){
        for (int i = 0; i < POOL_SIZE; i++) {
            try{
                freeConnections.take().reallyClose();
            } catch (InterruptedException e) {
                logger.error("Interrupted exception in destroy pool method", e);
                Thread.currentThread().interrupt();
            } catch (SQLException e) {
                logger.error("SQL exception in get destroy pool method", e);
            }
        }
        deregisterDrivers();
        logger.info("Pool destroyed");
    }

    private void deregisterDrivers() {
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                logger.error("SQL exception in deregister driver method", e);
            }
        });
    }
}
