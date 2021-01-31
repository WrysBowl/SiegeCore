package net.siegemc.core;

import net.siegemc.core.dungeons.DungeonConfig;
import net.siegemc.core.events.JoinEvents;
import net.siegemc.core.party.Party;
import net.siegemc.core.party.PartySaving;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;


public final class Core extends JavaPlugin {
    public static Location spawnLocation;

    @Override
    public void onEnable() {
        spawnLocation = new Location(Bukkit.getWorld("SiegeHub"), 70.5, 71, 3.5, 90, 0);
        PartySaving.FileExists();
        DungeonConfig.createConfig();
        (new VaultHook()).createHooks(); // Add the hooks to the vault plugin
        DbManager.create(); // Create the initial connections
        Bukkit.getPluginManager().registerEvents(new JoinEvents(), this); // Register the connection event
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
