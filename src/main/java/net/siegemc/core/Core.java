package net.siegemc.core;

import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.plugin.java.JavaPlugin;


public final class Core extends JavaPlugin {
    public BukkitAudiences audiences;
    @Override
    public void onLoad() {
        DatabaseManager.connectToDB();
        audiences = BukkitAudiences.create(this);
    }

    @Override
    public void onEnable() {
        getLogger().info("Plugin has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin has been disabled!");
    }

    public static Core plugin(){
        return Core.getPlugin(Core.class);
    }
}
