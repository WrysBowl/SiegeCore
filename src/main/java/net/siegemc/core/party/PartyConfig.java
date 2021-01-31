package net.siegemc.core.party;

import lombok.Getter;
import net.siegemc.core.Core;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class PartyConfig {
    @Getter
    private static FileConfiguration configuration = null;
    @Getter
    private static File configFile;


    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void createConfig() {
        configFile = new File(Core.plugin().getDataFolder().getAbsolutePath(), "parties.yml");
        configuration = YamlConfiguration.loadConfiguration(configFile);
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void save() {
        try {
            configuration.save(configFile);
            ;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
