package net.siegemc.dungeons;

import net.siegemc.core.Core;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class DungeonConfig { //How to delete schematics?
    private static File configFile;
    private static FileConfiguration configuration;


    /**
     * Creates the dungeons.yml config
     */
    public static void createConfig() {
        configFile = new File(Core.plugin().getDataFolder().getAbsolutePath(), "dungeons.yml");
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
        }
        configuration = new YamlConfiguration();
        try {
            configFile.createNewFile();
            configuration.load(configFile);
            deserializeDungeonTypes();
        } catch (IOException e) {
            e.printStackTrace();
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
            configuration.save(configFile);
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
        configFile.delete();
        createConfig();
    }

    /**
     * @param dungeonName The dungeon type's name
     * @return The configuration section
     */
    public static ConfigurationSection getDungeonType(String dungeonName) {
        ConfigurationSection section = configuration.getConfigurationSection(dungeonName);
        if (section == null) section = configuration.createSection(dungeonName);
        return section;
    }


    /**
     * @param dungeonType The type of the dungeon
     * @return ConfigurationSection The config's data on the list of dungeons of that type
     */
    public static ConfigurationSection getDungeons(DungeonType dungeonType) { // Get the dungeons of a specific type from the file
        ConfigurationSection section = getDungeonType(dungeonType.name);
        ConfigurationSection dungeonSection = section.getConfigurationSection("dungeons");
        if (dungeonSection == null) dungeonSection = section.createSection("dungeons");
        return dungeonSection;
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

    public static void deserializeDungeonTypes() {
        configuration.getKeys(false).forEach(key -> {
            DungeonType.dungeonTypes.add(DungeonType.deserialize(configuration.getConfigurationSection(key), key));
        });
    }

}