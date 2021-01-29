package net.siegemc.core.party;

import lombok.Getter;
import net.siegemc.core.Core;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class PartySaving {
    @Getter private FileConfiguration configuration = null;
    @Getter private File PartyData;
    
    public PartySaving() {}
    
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void FileExists() {
        PartyData = new File(Core.plugin().getDataFolder().getAbsolutePath(), "PartyData.yml");
        configuration = YamlConfiguration.loadConfiguration(PartyData);
        if (!PartyData.exists()) {
            try { PartyData.createNewFile(); }
            catch (IOException e) { e.printStackTrace(); }
        }
        
        configuration.createSection("Test");
        configuration.set("test", "hello");
        
        save();
    }
    
    public void save() {
        try {
            configuration.save(PartyData);;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
