package net.siegemc.core.utils;

import net.siegemc.core.Core;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;

public class Utils {
    static public String tacc(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    static public String strip(String str) {
        return ChatColor.stripColor(str);
    }

    static public NamespacedKey namespacedKey(String str) {
        return new NamespacedKey(Core.plugin(), str);
    }
}
