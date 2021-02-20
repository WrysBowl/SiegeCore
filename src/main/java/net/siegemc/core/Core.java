package net.siegemc.core;

import lombok.Getter;
import net.siegemc.core.dungeons.DungeonConfig;
import net.siegemc.core.items.CustomItem;
import net.siegemc.core.items.ItemConfig;
import net.siegemc.core.items.SpawnItemCommand;
import net.siegemc.core.listeners.*;
import net.siegemc.core.party.Party;
import net.siegemc.core.party.PartyCommand;
import net.siegemc.core.party.PartyConfig;
import net.siegemc.core.utils.DbManager;
import net.siegemc.core.utils.VaultHook;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import redis.clients.util.IOUtils;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.HashMap;
import java.util.UUID;

public final class Core extends JavaPlugin {
    @Getter private static final HashMap<UUID, Party> parties = new HashMap<>();
    @Getter private static final HashMap<String, CustomItem> items = new HashMap<>();
    public static Location spawnLocation;
    
    @SuppressWarnings({"ResultOfMethodCallIgnored", "ConstantConditions"})
    @Override
    public void onEnable() {
        // Initialize
        spawnLocation = new Location(Bukkit.getWorld("SiegeHub"), 70.5, 71, 3.5, 90, 0);
        
        // Create Configs
        if (!getDataFolder().exists()) getDataFolder().mkdir();
        PartyConfig.createConfig();
        DungeonConfig.createConfig();
        ItemConfig.createConfigs();
        
        // Fetch items from config
        ItemConfig.fetchItems();
        
        // Create Hooks / Connections
        (new VaultHook()).createHooks(); // Add the hooks to the vault plugin
        DbManager.create(); // Create the initial connections
        
        // Recover any parties from before shutdown
        ConfigurationSection parties = PartyConfig.getConfiguration().getConfigurationSection("party");
        if (parties != null) for (String party : parties.getKeys(false)) {
            Party newParty = new Party(UUID.fromString(party));
            getParties().put(newParty.getLeader().getUniqueId(), newParty);
        }

        // Register Events
        Bukkit.getPluginManager().registerEvents(new JoinEvents(), this);
        Bukkit.getPluginManager().registerEvents(new WorldProtection(), this);
        Bukkit.getPluginManager().registerEvents(new CustomItemListener(), this);
        Bukkit.getPluginManager().registerEvents(new DamageIndicators(), this);
        Bukkit.getPluginManager().registerEvents(new CustomDrops(), this);
        Bukkit.getPluginManager().registerEvents(new PickUpEvents(), this);
        Bukkit.getPluginManager().registerEvents(new ChatEvents(), this);
        Bukkit.getPluginManager().registerEvents(new QuitEvents(), this);
        Bukkit.getPluginManager().registerEvents(new Food(), this);
        Bukkit.getPluginManager().registerEvents(new ToolChangeEvents(), this);
    
        // Register Commands
        PartyCommand partyCommand = new PartyCommand();
        Bukkit.getPluginCommand("party").setExecutor(partyCommand);
        Bukkit.getPluginCommand("party").setTabCompleter(partyCommand);
        Bukkit.getPluginCommand("spawnitem").setExecutor(new SpawnItemCommand());
    }
    
    @Override
    public void onDisable() {
        for (Party party : getParties().values()) party.save(true);
    }
    
    public static Core plugin() {
        return Core.getPlugin(Core.class); // Method to get the plugin from other classes, so you can use Core.plugin() in other classes to get the plugin
    }
    
    public static Party getParty(UUID playerUUID) {
        for (Party party : getParties().values()) if (party.isMember(playerUUID)) return party;
        return null;
    }

}
