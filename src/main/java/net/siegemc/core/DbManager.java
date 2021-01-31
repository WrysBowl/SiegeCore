package net.siegemc.core;

import org.bukkit.scheduler.BukkitRunnable;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedTransferQueue;


public class DbManager {

    private static final String url;
    private static final String user;
    private static final String password;
    private final static Queue<Connection> connectionPool = new LinkedTransferQueue<>();
    private final static Queue<Connection> usedConnections = new LinkedTransferQueue<>();
    private static final int INITIAL_POOL_SIZE = 10;
    private static final int MAX_TIMEOUT = 30 * 1000;
    static {
        Yaml yaml = new Yaml();
        InputStream stream = Core.plugin().getResource("privKeys.yml");
        if (stream == null) {
            Core.plugin().getLogger().severe("You need a privKeys.yml file for the plugin to work! Get the most updated values in the #dev-stuff discord channel!");
        }
        Map<String, Object> obj = yaml.load(stream);
        @SuppressWarnings("unchecked")
        Map<String, Object> dbObj = (Map<String, Object>) obj.get("db");
        url = String.format("jdbc:mysql://%s/%s", dbObj.get("endpoint"), dbObj.get("db name"));
        user = String.valueOf(dbObj.get("username"));
        password = String.valueOf(dbObj.get("password"));
    }

    public static void create() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
                    connectionPool.add(createConnection());
                }
            }
        }.runTaskAsynchronously(Core.plugin());
    }


    public static synchronized Connection getConnection() {
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

    public static synchronized void releaseConnection(Connection connection) {
        connectionPool.add(connection);
        usedConnections.remove(connection);
    }

    private static synchronized Connection createConnection() {
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