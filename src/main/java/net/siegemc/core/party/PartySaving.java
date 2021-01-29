package net.siegemc.core.party;

import lombok.Getter;
import net.siegemc.core.Core;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class PartySaving {
    @Getter private static FileConfiguration configuration = null;
    @Getter private static File PartyData;
    
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void FileExists() {
        PartyData = new File(Core.getDataFolder().getAbsolutePath(), "PartyData.yml");
        configuration = YamlConfiguration.loadConfiguration(PartyData);\
        if (!PartyData.exists()) PartyData.createNewFile();
        
        configuration.createSection("Test");
        configuration.set("test", "hello");
        
        save();
    }
    
    public static void save() {
        try {
            configuration.save(PartyData);;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
