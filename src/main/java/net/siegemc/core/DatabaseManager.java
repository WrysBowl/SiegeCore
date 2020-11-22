package net.siegemc.core;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseManager {
    public static Connection con = null;
    public static void connectToDB() {
        try {
            Class.forName("con.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://paneldatabase.humbleservers.com:3306/s2684_database", "u2684_KkiEFWRhCm", ".xtYIRHnmDx7S72cq+s8ap8k");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
