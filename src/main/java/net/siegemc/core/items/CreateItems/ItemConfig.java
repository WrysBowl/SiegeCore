package net.siegemc.core.items.CreateItems;

import lombok.Getter;
import net.siegemc.core.Core;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemConfig {
    @Getter private static final List<String> itemTypes = Arrays.asList("swords", "axes", "bows", "armor", "wands", "foods");
    @Getter private static final HashMap<String, File> files = new HashMap<>();
    @Getter private static final HashMap<File, YamlConfiguration> configs = new HashMap<>();
    
    /**
     * Creates the configs in the items/ directory then saves all existing items there
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void createConfigs() {
        try {
            File directory = new File(Core.plugin().getDataFolder().getAbsolutePath(), "items");
            directory.mkdirs();
            for (String type : getItemTypes()) {
                File file = new File(Core.plugin().getDataFolder().getAbsolutePath()+File.separator+"items", type+".yml");
                YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
                if (!file.exists()) file.createNewFile();
                getConfigs().put(file, config);
                getFiles().put(type, file);
            }
            saveAllItems();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void saveAllItems() {
        for (String item : Core.getItems().keySet()) {
            CustomItem customItem = Core.getItems().get(item);
            HashMap<String, Object> data = customItem.serialize();
            
            String type = customItem.getType();
            String name = customItem.getItemName();
            File file = getFiles().get(type);
            YamlConfiguration config = getConfigs().get(file);
            
            for (String key : data.keySet()) {
                Object value = data.get(key);
                config.set(type+"."+name+"."+key, value);
            }
            save(config, file);
        }
    }
    
    /**
     * Fetches the items from the configs and loads them in
     */
    @SuppressWarnings({"ConstantConditions"})
    public static void fetchItems() {
        for (String type : getItemTypes()) {
            File file = new File(Core.plugin().getDataFolder().getAbsolutePath()+File.separator+"items", type+".yml");
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            for (String item : config.getConfigurationSection(type).getKeys(false)) {
                Map<String, Object> data = config.getConfigurationSection(type+"."+item).getValues(true);
                
                switch (type) {
                    case "swords":
                        new SwordItem(data);
                        break;
                    case "bows":
                        new BowItem(data);
                        break;
                    case "wands":
                        new WandItem(data);
                        break;
                    case "axes":
                        new AxeItem(data);
                        break;
                    case "armor":
                        new ArmorItem(data);
                        break;
                    case "foods":
                        new FoodItem(data);
                        break;
                    default:
                        new CustomItem(data);
                        break;
                }
            }
        }
        saveAllItems();
    }
    
    private static void save(YamlConfiguration config, File file) {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
