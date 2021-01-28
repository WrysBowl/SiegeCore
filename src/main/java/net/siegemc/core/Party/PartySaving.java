package net.siegemc.core.Party;

import net.siegemc.core.Core;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class PartySaving {
    public static FileConfiguration yaml = null;
    public static File PartyData;
    public static void FileExists() {
        PartyData = new File(Core.plugin().getDataFolder().getAbsolutePath(), "PartyData.yml");
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(PartyData);
        if (!PartyData.exists()) {
            try {
                PartyData.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        yaml.createSection("Test");
        yaml.set("test", "hello");
        try {
            yaml.save(PartyData);;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
