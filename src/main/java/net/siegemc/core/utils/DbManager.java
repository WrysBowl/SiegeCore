package net.siegemc.core.utils;

import net.siegemc.core.Core;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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
        File configFile = new File(Core.plugin().getDataFolder().getAbsolutePath(), "privKeys.yml");
        if (!configFile.exists()) {
            Core.plugin().getLogger().severe("You need a privKeys.yml file for the plugin to work! Get the most updated values in the #dev-stuff discord channel!");
        }
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(configFile);
        url = String.format("jdbc:mysql://%s/%s",
                configuration.getString("db.endpoint"),
                configuration.getString("db.db name"));
        user = String.valueOf(configuration.getString("db.username"));
        password = String.valueOf(configuration.getString("db.password"));
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
        if (connectionPool.isEmpty())
            connectionPool.add(createConnection());
        Connection connection = connectionPool.poll();
        try {
            if (connection == null || connection.isClosed() || !connection.isValid(MAX_TIMEOUT)) {
                connection = createConnection();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    
        usedConnections.add(connection);
        return connection;
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