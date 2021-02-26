package net.siegemc.core.listeners;

import lombok.Getter;
import net.siegemc.core.Core;
import net.siegemc.core.items.CreateItems.CustomItem;
import net.siegemc.core.utils.Levels;
import net.siegemc.core.utils.NBT;
import net.siegemc.core.utils.Utils;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class CustomDrops implements Listener {
    
    @Getter private static File configFile;
    @Getter private static YamlConfiguration configuration;
    @Getter private static final List<String> mobs = new ArrayList<>();
    
    public CustomDrops() {
        createConfig();
    }
    
    /**
     * Creates a configuration file for custom item drops
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void createConfig() {
        configFile = new File(Core.plugin().getDataFolder().getAbsolutePath(), "drops.yml");
        configuration = YamlConfiguration.loadConfiguration(configFile);
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
                Map<String, Double> drops = new HashMap<>();
                drops.put("Debug Bow", 100D);
                drops.put("Test Stick", 100D);
                configuration.set("mobs.Moo Moo.items", drops);
                configuration.set("mobs.Moo Moo.gold.min", 10);
                configuration.set("mobs.Moo Moo.gold.max", 15);
                configuration.set("mobs.Moo Moo.xp", 20);
                configuration.save(configFile);
            }
            catch (IOException e) { e.printStackTrace(); }
        }
        ConfigurationSection section = configuration.getConfigurationSection("mobs");
        if (section == null) return;
        section.getKeys(false).forEach(getMobs()::add);
    }
    
    @SuppressWarnings("ConstantConditions")
    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity instanceof Player) return;
        String name = Utils.strip(entity.getCustomName());
        if (name == null) return;
        event.getDrops().clear();
        
        Location loc = entity.getLocation();
        
        for (String mobName : getMobs()) {
            if (name.toLowerCase().contains(mobName.toLowerCase())) {
                ConfigurationSection itemSection = configuration.getConfigurationSection("mobs."+mobName+".items");
                int xp = configuration.getInt("mobs."+mobName+".xp");
                
                OfflinePlayer killer = null;
                if (entity.getKiller() == null || entity.getKiller() instanceof Player) {
                    killer = entity.getKiller();;
                } else if (NBT.getString(entity, "attacker") != null) {
                    killer = NBT.deserializePlayer(NBT.getString(entity, "attacker"));
                }
                
                if (killer != null) {
                    Levels.addExp(killer, xp);
                    if (killer.isOnline()) ((Player) killer).sendMessage(Utils.tacc("&a+"+xp+" EXP"));
                }
                
                if (itemSection != null) {
                    for (String item : itemSection.getKeys(false)) {
                        if (itemSection.getString(item) == null) continue;
                        double choose = Math.random() * 100;
                        double percent = itemSection.getDouble(item);
                        if (choose <= percent) {
                            CustomItem customItem = Core.getItems().get(item);
                            if (customItem != null) loc.getWorld().dropItem(loc, customItem.get());
                        }
                    }
                }
                
            }
        }
    }
    
}
