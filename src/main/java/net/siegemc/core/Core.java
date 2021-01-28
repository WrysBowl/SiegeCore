package net.siegemc.core;

import net.siegemc.core.Party.Party;
import net.siegemc.core.Party.PartySaving;
import net.siegemc.core.events.ConnectEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;


public final class Core extends JavaPlugin {
    public static File dataFolder ;
    @Override
    public void onEnable() {
        dataFolder = this.getDataFolder();
        PartySaving.FileExists();
        (new VaultHook()).createHooks(); // Add the hooks to the vault plugin
        //DbManager.create(); // Create the initial connections
        Bukkit.getPluginManager().registerEvents(new ConnectEvent(), this); // Register the connection event
        getCommand("party").setExecutor(new Party());
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
