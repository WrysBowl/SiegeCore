package net.siegemc.core.dungeons;

import net.siegemc.core.Core;
import net.siegemc.core.utils.ConfigurationBase;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;

import java.io.File;
import java.io.IOException;

public class DungeonConfig extends ConfigurationBase { //How to delete schematics?
    Core plugin;
    public DungeonConfig(Core plugin) {
        super(new File(plugin.getDataFolder(), "dungeons.yml"));
        this.plugin = plugin;
        try {
            createConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createConfig() throws IOException, InvalidConfigurationException {
        super.createConfig();
        deserializeDungeonTypes();
    }


    /**
     * @param dungeonName The dungeon type's name
     * @return The configuration section
     */
    public ConfigurationSection getDungeonType(String dungeonName) {
        ConfigurationSection section = configuration.getConfigurationSection(dungeonName);
        if (section == null) section = configuration.createSection(dungeonName);
        return section;
    }


    /**
     * @param dungeonType The type of the dungeon
     * @return ConfigurationSection The config's data on the list of dungeons of that type
     */
    public ConfigurationSection getDungeons(DungeonType dungeonType) { // Get the dungeons of a specific type from the file
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
    public ConfigurationSection getDungeon(DungeonType dungeonType, int index) { // Get the dungeon at number index of a specific dungeon, not done yet
        ConfigurationSection dungeons = getDungeons(dungeonType);
        if (dungeons.isConfigurationSection(String.valueOf(index)))
            return dungeons.getConfigurationSection(String.valueOf(index));
        return dungeons.createSection(String.valueOf(index));
    }

    public void deserializeDungeonTypes() {
        configuration.getKeys(false).forEach(key -> {
            DungeonType.dungeonTypes.add(DungeonType.deserialize(configuration.getConfigurationSection(key), key, plugin));
        });
    }

}