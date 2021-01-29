package net.siegemc.core;

import lombok.Getter;
import net.siegemc.core.party.Party;
import net.siegemc.core.party.PartySaving;
import net.siegemc.core.events.ConnectEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;


public final class Core extends JavaPlugin {
    @Getter private File dataFolder;
    
    @SuppressWarnings({"ResultOfMethodCallIgnored", "ConstantConditions"})
    @Override
    public void onEnable() {
        dataFolder = getDataFolder();
        
        if (!dataFolder.exists()) dataFolder.mkdir();
        PartySaving.FileExists();
        
        (new VaultHook()).createHooks(); // Add the hooks to the vault plugin
        //DbManager.create(); // Create the initial connections
        
        Bukkit.getPluginManager().registerEvents(new ConnectEvent(), this); // Register the connection event
        Bukkit.getPluginCommand("party").setExecutor(new Party());
    }

    @Override
    public void onDisable() {}

    public static Core plugin() {
        return Core.getPlugin(Core.class); // Method to get the plugin from other classes, so you can use Core.plugin() in other classes to get the plugin
    }
}
