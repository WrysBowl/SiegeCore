package net.siegemc.core.dungeons;

import net.siegemc.core.Core;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class DungeonConfig { //How to delete schematics?
    private File dungeonConfigFile;
    private FileConfiguration configuration;

    public void createConfig() { // Creates the dungeons.yml config for future use, should be ran when the plugin starts up
        dungeonConfigFile = new File(Core.plugin().getDataFolder(), "dungeons.yml");
        if (!dungeonConfigFile.exists()) {
            dungeonConfigFile.getParentFile().mkdirs();
            Core.plugin().saveResource("dungeons.yml", false);
        }
        configuration = new YamlConfiguration();
        try {
            configuration.load(dungeonConfigFile);
        } catch (IOException ignored) {
        } catch (InvalidConfigurationException e) {
            Core.plugin().getLogger().severe("The dungeons.yml file contains invalid data!");
            e.printStackTrace();
        }
    }


    public void saveConfig() throws IOException { // Saves the configuration to file
        configuration.save(dungeonConfigFile);
    }

    public FileConfiguration getConfig() {
        return this.configuration;
    }

    public ConfigurationSection getDungeons(DungeonType dungeon) { // Get the dungeons of a specific type from the file
        if (configuration.isConfigurationSection(dungeon.name()))
            return configuration.getConfigurationSection(dungeon.name());
        return configuration.createSection(dungeon.name());
    }

    public void getDungeon(DungeonType dungeon, int index){ // Get the dungeon at number index of a specific dungeon, not done yet

    }

}