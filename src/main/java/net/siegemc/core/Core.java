package net.siegemc.core;

import net.siegemc.core.events.ConnectEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;


public final class Core extends JavaPlugin {
    @Override
    public void onEnable() {
        (new VaultHook()).createHooks(); // Add the hooks to the vault plugin
        DbManager.create(); // Create the initial connections
        Bukkit.getPluginManager().registerEvents(new ConnectEvent(), this); // Register the connection event
        getLogger().info("Plugin has enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin has been disabled!");
    }

    public static Core plugin() {
        return Core.getPlugin(Core.class); // Method to get the plugin from other classes, so you can use Core.plugin() in other classes to get the plugin
    }
}
