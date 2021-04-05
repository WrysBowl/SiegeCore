package net.siegemc.dungeons;

import org.bukkit.plugin.java.JavaPlugin;

public final class DungeonsPlugin extends JavaPlugin {


    @Override
    public void onEnable() {
        DungeonConfig.createConfig();

    }


    @Override
    public void onDisable() {

    }


    public static DungeonsPlugin plugin() {
        return DungeonsPlugin.getPlugin(DungeonsPlugin.class); // Method to get the plugin from other classes, so you can use Core.plugin() in other classes to get the plugin
    }
}
