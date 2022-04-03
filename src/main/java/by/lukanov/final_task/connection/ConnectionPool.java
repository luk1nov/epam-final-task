package by.lukanov.final_task.connection;

import by.lukanov.final_task.command.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static final Logger logger = LogManager.getLogger();
    private static final String URL = "jdbc:mysql://localhost:3306/car_rental";
    private static final String LOGIN = "root";
    private static final String PASS = "root";
    private static final Lock locker = new ReentrantLock();
    private static final int POOL_SIZE = 8;
    /*private static final String DB_URL;
    private static final String DB_PASSWORD;
    private static final String DB_USER;
    private static final String DB_DRIVER;*/
    private static final AtomicBoolean isInstanceInitialized = new AtomicBoolean(false);
    private static ConnectionPool instance;
    private final BlockingQueue<ProxyConnection> freeConnections;
    private final BlockingQueue<ProxyConnection> usedConnections;

    static {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        } catch (SQLException e) {
            logger.error(Message.DRIVER_NOT_REGISTER);
            throw new ExceptionInInitializerError(e.getMessage());
        }
    }

    private ConnectionPool() {
        freeConnections = new LinkedBlockingQueue<>(POOL_SIZE);
        usedConnections = new LinkedBlockingQueue<>(POOL_SIZE);
        for (int i = 0; i < 8; i++) {
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
        try {
            locker.lock();
            if(instance == null){
                if(isInstanceInitialized.compareAndSet(false, true)){
                    instance = new ConnectionPool();
                }
            }
        } finally {
            locker.unlock();
        }
        return instance;
    }

    private Connection createConnection(){
        Connection connection;
        try {
            connection = DriverManager.getConnection(URL, LOGIN, PASS);
        } catch (SQLException e) {
            logger.fatal(Message.POOL_INIT_ERROR, e);
            throw new RuntimeException();
        }
        return connection;
    }

    public ProxyConnection getConnection(){
        ProxyConnection connection = null;
        try {
            connection = freeConnections.take();
            usedConnections.put(connection);
        } catch (InterruptedException e) {
            logger.error(Message.GET_CON_EXCEPT);
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    public void releaseConnection(ProxyConnection connection){
        try {
            usedConnections.remove(connection);
            freeConnections.put(connection);
        } catch (InterruptedException e) {
            logger.error(Message.RELEASE_CON_EXCEPT);
            Thread.currentThread().interrupt();
        }
    }

    public void destroyPool(){
        for (int i = 0; i < POOL_SIZE; i++) {
            try{
                freeConnections.take().close();
            } catch (InterruptedException e) {
                logger.error(Message.DESTROY_POOL_INTERRUPTED);
                Thread.currentThread().interrupt();
            } catch (SQLException e) {
                logger.error(Message.DESTROY_POOL_SQL);
            }
        }
        deregisterDrivers();
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
