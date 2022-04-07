package by.lukyanov.finaltask.model.connection;

import by.lukyanov.finaltask.command.Message;
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
    private static final String DB_URL;
    private static final String DB_PASSWORD;
    private static final String DB_USER;
    private static final String DB_DRIVER;
    private static final Properties properties = new Properties();
    private static final AtomicBoolean isInstanceInitialized = new AtomicBoolean(false);
    private static ConnectionPool instance;
    private final BlockingQueue<ProxyConnection> freeConnections;
    private final BlockingQueue<ProxyConnection> usedConnections;

    static {
        try (InputStream inputStream = ConnectionPool.class.getClassLoader()
                .getResourceAsStream("database.properties")) {
            properties.load(inputStream);
            DB_URL = properties.getProperty("db.url");
            DB_USER = properties.getProperty("db.user");
            DB_PASSWORD = properties.getProperty("db.password");
            DB_DRIVER = properties.getProperty("db.driver");
            Class.forName(DB_DRIVER);
        } catch (IOException e) {
            logger.fatal(Message.READ_PROPERTIES_ERROR, e);
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            logger.fatal(Message.DRIVER_NOT_REGISTER, e);
            throw new RuntimeException(e);
        }
    }

    private ConnectionPool() {
        freeConnections = new LinkedBlockingQueue<>(POOL_SIZE);
        usedConnections = new LinkedBlockingQueue<>(POOL_SIZE);
        for (int i = 0; i < POOL_SIZE; i++) {
            try {
                Connection connection = createConnection();
                ProxyConnection proxyConnection = new ProxyConnection(connection);
                freeConnections.put(proxyConnection);
            } catch (InterruptedException e) {
                logger.error("connection didn't created");
            }
        }
        if(freeConnections.size() != POOL_SIZE){
            logger.error(Message.POOL_NOT_FULL);
            for (int i = freeConnections.size(); i < POOL_SIZE; i++) {
                try {
                    Connection connection = createConnection();
                    ProxyConnection proxyConnection = new ProxyConnection(connection);
                    freeConnections.put(proxyConnection);
                } catch (InterruptedException e) {
                    logger.error("connection didn't created again");
                }
            }
        }
        if(freeConnections.size() != POOL_SIZE){
            throw new ExceptionInInitializerError(Message.POOL_FILL_ERROR);
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

    private Connection createConnection(){
        Connection connection;
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            logger.fatal(Message.POOL_INIT_ERROR, e);
            throw new RuntimeException();
        }
        return connection;
    }

    public Connection getConnection(){
        ProxyConnection connection = null;
        try {
            connection = freeConnections.take();
            usedConnections.put(connection);
        } catch (InterruptedException e) {
            logger.error(Message.GET_CON_EXCEPT);
            Thread.currentThread().interrupt();
        }
        logger.debug("availible " + freeConnections.size());
        return connection;
    }

    public boolean releaseConnection(Connection connection){
        if(connection instanceof ProxyConnection proxyConnection){
            try {
                usedConnections.remove(proxyConnection);
                freeConnections.put(proxyConnection);
            } catch (InterruptedException e) {
                logger.error(Message.RELEASE_CON_EXCEPT);
                Thread.currentThread().interrupt();
            }
            logger.debug("availible " + freeConnections.size());
            return true;
        } else {
            return false;
        }
    }

    public void destroyPool(){
        logger.info("free connections " + freeConnections.size());
        logger.info("used connections " + usedConnections.size());
        for (int i = 0; i < POOL_SIZE; i++) {
            try{
                freeConnections.take().reallyClose();
            } catch (InterruptedException e) {
                logger.error(Message.DESTROY_POOL_INTERRUPTED);
                Thread.currentThread().interrupt();
            } catch (SQLException e) {
                logger.error(Message.DESTROY_POOL_SQL);
            }
        }
        deregisterDrivers();
        logger.info("Pool destroyed successfully");
    }

    private void deregisterDrivers() {
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                logger.error(Message.DEREGISTER_DRIVER_SQL);
            }
        });
    }

}
