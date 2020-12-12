package net.siegemc.core;


import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.PriorityQueue;
import java.util.Queue;

public class DbManager {

    private static final Queue<Connection> poolConnections = new PriorityQueue<>();
    private static final Queue<Connection> usedConnections = new PriorityQueue<>();

    static {
        try {
            poolConnections.add(createConnection());
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private static Connection createConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://paneldatabase.humbleservers.com:3306/s2684_data", "u2684_ES9oodb7pa", "UyS@YL4g!KnhUxt@yM0iV=og");
    }

    public static Connection getConnection() {
        if (poolConnections.size() < 1) {
            try {
                poolConnections.add(createConnection());
            } catch (Exception e) {
                Core.plugin().getLogger().warning("Could not create a new connection!");
                return null;
            }
        }
        Connection con = poolConnections.peek();
        usedConnections.add(con);
        return con;
    }

    @NotNull
    public static boolean releaseConnection(Connection con) {
        if (usedConnections.contains(con)) {
            usedConnections.remove(con);
            return poolConnections.add(con);
        }
        try {
            con.close();
        } catch (Exception ignored) {
        }
        return false;
    }

}
