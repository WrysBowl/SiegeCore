package net.siegemc.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Queue;
import java.util.concurrent.LinkedTransferQueue;


public class DbManager {

    private static final String url = "jdbc:mysql://paneldatabase.humbleservers.com:3306/s2684_data";
    private static final String user = "u2684_ES9oodb7pa";
    private static final String password = "WCPqgtF7K^8^q59DAHe.NWnx";
    private final static Queue<Connection> connectionPool = new LinkedTransferQueue<>();
    private final static Queue<Connection> usedConnections = new LinkedTransferQueue<>();
    private static final int INITIAL_POOL_SIZE = 10;
    private static final int MAX_TIMEOUT = 30 * 1000;


    public static void create() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            connectionPool.add(createConnection());
        }
    }


    public static Connection getConnection() {
        try {
            if (connectionPool.isEmpty())
                connectionPool.add(createConnection());
            Connection connection = connectionPool.poll();
            if (connection == null || connection.isClosed() || !connection.isValid(MAX_TIMEOUT)) {
                connection = createConnection();
            }

            usedConnections.add(connection);
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean releaseConnection(Connection connection) {
        connectionPool.add(connection);
        return usedConnections.remove(connection);
    }

    private static Connection createConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getSize() {
        return connectionPool.size() + usedConnections.size();
    }

    public Queue<Connection> getConnectionPool() {
        return connectionPool;
    }

}