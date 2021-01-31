package net.siegemc.core.dungeons;

import net.siegemc.core.Core;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class DungeonConfig { //How to delete schematics?
    private static File dungeonConfigFile;
    private static FileConfiguration configuration;


    /**
     * Creates the dungeons.yml config
     */
    public static void createConfig() {
        dungeonConfigFile = new File(Core.plugin().getDataFolder().getAbsolutePath(), "dungeons.yml");
        if (!dungeonConfigFile.exists()) {
            dungeonConfigFile.getParentFile().mkdirs();
        }
        configuration = new YamlConfiguration();
        try {
            dungeonConfigFile.createNewFile();
            configuration.load(dungeonConfigFile);
        } catch (IOException ignored) {
        } catch (InvalidConfigurationException e) {
            Core.plugin().getLogger().severe("The dungeons.yml file contains invalid data!");
            e.printStackTrace();
        }
    }

    /**
     * Saves the configuration to a file
     *
     * @throws IOException when the file can't be written to
     */
    public static void save() { // Saves the configuration to file
        try {
            configuration.save(dungeonConfigFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static FileConfiguration getConfiguration() {
        return configuration;
    }

    /**
     * Resets the dungeon configuration, deleting ALL dungeon data.
     * Beware as this can't be rolled back!
     */
    public static void reset() {
        dungeonConfigFile.delete();
        createConfig();
    }

    /**
     * @param dungeonType The type of the dungeon
     * @return ConfigurationSection The config's data on the list of dungeons of that type
     */
    public static ConfigurationSection getDungeons(DungeonType dungeonType) { // Get the dungeons of a specific type from the file
        if (configuration.isConfigurationSection(dungeonType.name()))
            return configuration.getConfigurationSection(dungeonType.name());
        return configuration.createSection(dungeonType.name());
    }

    /**
     * @param dungeonType The type of the dungeon
     * @param index       The index the dungeon is at.
     * @return The config's data on the dungeon
     */
    public static ConfigurationSection getDungeon(DungeonType dungeonType, int index) { // Get the dungeon at number index of a specific dungeon, not done yet
        ConfigurationSection dungeons = getDungeons(dungeonType);
        if (dungeons.isConfigurationSection(String.valueOf(index)))
            return dungeons.getConfigurationSection(String.valueOf(index));
        return dungeons.createSection(String.valueOf(index));
    }

}