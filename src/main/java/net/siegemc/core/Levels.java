package net.siegemc.core;

import org.bukkit.OfflinePlayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Levels {
    public static Short getLevel(OfflinePlayer player) {
        Connection connection = DbManager.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT level FROM userData WHERE uuid=?");
            statement.setString(1, player.getUniqueId().toString());
            ResultSet query = statement.executeQuery();
            short level = query.getShort(0);
            DbManager.releaseConnection(connection);
            return level;
        } catch (SQLException e) {
            return null;
        }
    }

    public static boolean setLevel(OfflinePlayer player, short level) {
        Connection connection = DbManager.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE userData SET level=? WHERE uuid=?");
            statement.setShort(1, level);
            statement.setString(2, player.getUniqueId().toString());
            int query = statement.executeUpdate();
            DbManager.releaseConnection(connection);
            return query > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean addLevel(OfflinePlayer player, short level) {
        Connection connection = DbManager.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE userData SET level=level+? WHERE uuid=?");
            statement.setShort(1, level);
            statement.setString(2, player.getUniqueId().toString());
            int query = statement.executeUpdate();
            DbManager.releaseConnection(connection);
            return query > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public static Integer getExp(OfflinePlayer player) {
        Connection connection = DbManager.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT experience FROM userData WHERE uuid=?");
            statement.setString(1, player.getUniqueId().toString());
            ResultSet query = statement.executeQuery();
            int exp = query.getInt(0);
            DbManager.releaseConnection(connection);
            return exp;
        } catch (SQLException e) {
            return null;
        }
    }

    public static boolean setExp(OfflinePlayer player, int exp) {
        Connection connection = DbManager.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE userData SET experience=? WHERE uuid=?");
            statement.setInt(1, exp);
            statement.setString(2, player.getUniqueId().toString());
            int query = statement.executeUpdate();
            DbManager.releaseConnection(connection);
            return query > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean addExp(OfflinePlayer player, int exp) {
        Connection connection = DbManager.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE userData SET experience=experience+? WHERE uuid=?");
            statement.setInt(1, exp);
            statement.setString(2, player.getUniqueId().toString());
            int query = statement.executeUpdate();
            DbManager.releaseConnection(connection);
            return query > 0;
        } catch (SQLException e) {
            return false;
        }
    }
}
