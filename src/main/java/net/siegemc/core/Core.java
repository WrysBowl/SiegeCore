package net.siegemc.core;

import net.siegemc.core.events.ChatListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;


public final class Core extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("Plugin is enabling!");
        (new VaultHook()).createHooks();
        Bukkit.getPluginManager().registerEvents(new ChatListener(), Core.plugin());
        getLogger().info("Plugin has enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin has been disabled!");
    }

    public static Core plugin() {
        return Core.getPlugin(Core.class);
    }
}
