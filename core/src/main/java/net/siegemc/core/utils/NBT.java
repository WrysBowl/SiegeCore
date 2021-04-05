package net.siegemc.core.utils;

import net.siegemc.core.Core;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class NBT {
    public static NamespacedKey generateKey(String key) {
        return new NamespacedKey(Core.plugin(), key);
    }
    
    public static void addInt(PersistentDataHolder holder, String key, int value) {
        holder.getPersistentDataContainer().set(generateKey(key), PersistentDataType.INTEGER, value);
    }
    
    public static void addDouble(PersistentDataHolder holder, String key, double value) {
        holder.getPersistentDataContainer().set(generateKey(key), PersistentDataType.DOUBLE, value);
    }
    
    public static void addString(PersistentDataHolder holder, String key, String value) {
        holder.getPersistentDataContainer().set(generateKey(key), PersistentDataType.STRING, value);
    }
    
    public static int getInt(PersistentDataHolder holder, String key) {
        return holder.getPersistentDataContainer().getOrDefault(generateKey(key), PersistentDataType.INTEGER, 0);
    }
    
    public static double getDouble(PersistentDataHolder holder, String key) {
        return holder.getPersistentDataContainer().getOrDefault(generateKey(key), PersistentDataType.DOUBLE, 0D);
    }
    
    @SuppressWarnings("ConstantConditions")
    public static String getString(PersistentDataHolder holder, String key) {
        return holder.getPersistentDataContainer().getOrDefault(generateKey(key), PersistentDataType.STRING, null);
    }
    
    public static String serializePlayer(OfflinePlayer player) {
        return player.getUniqueId().toString();
    }
    
    public static OfflinePlayer deserializePlayer(String player) {
        return Bukkit.getOfflinePlayer(UUID.fromString(player));
    }
}
