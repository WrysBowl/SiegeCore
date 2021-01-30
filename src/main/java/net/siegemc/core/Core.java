package net.siegemc.core;

import lombok.Getter;
import net.siegemc.core.events.ConnectEvent;
import net.siegemc.core.listeners.WorldProtection;
import net.siegemc.core.party.*;
import net.siegemc.core.utils.VaultHook;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public final class Core extends JavaPlugin {
    @Getter private final PartySaving partySaving = new PartySaving();
    @Getter private static final HashMap<UUID, Party> parties = new HashMap<>();
    @Getter private static FileConfiguration partyConfig;
    private static File partyFile;
    
    @SuppressWarnings({"ResultOfMethodCallIgnored", "ConstantConditions"})
    @Override
    public void onEnable() {
        if (!getDataFolder().exists()) getDataFolder().mkdir();
        partyFile = new File(Core.plugin().getDataFolder().getAbsolutePath(), "PartyData.yml");
        partyConfig = YamlConfiguration.loadConfiguration(partyFile);
        if (!partyFile.exists()) {
            try { partyFile.createNewFile(); }
            catch (IOException e) { e.printStackTrace(); }
        }
        
        (new VaultHook()).createHooks(); // Add the hooks to the vault plugin
        //DbManager.create(); // Create the initial connections
        
        // Recover any parties from before shutdown
        ConfigurationSection parties = partyConfig.getConfigurationSection("party");
        if (parties != null) for (String party : parties.getKeys(false)) {
            Party newParty = new Party(UUID.fromString(party));
            getParties().put(newParty.getLeader().getUniqueId(), newParty);
        }
        
        // Register Events
        Bukkit.getPluginManager().registerEvents(new ConnectEvent(), this);
        Bukkit.getPluginManager().registerEvents(new WorldProtection(), this);
        
        // Register Commands
        PartyCommand partyCommand = new PartyCommand();
        Bukkit.getPluginCommand("party").setExecutor(partyCommand);
        Bukkit.getPluginCommand("party").setTabCompleter(partyCommand);
    }
    
    @Override
    public void onDisable() {
        partyConfig.set("party", null);
        for (Party party : getParties().values()) party.save(true);
    }
    
    public static Core plugin() {
        return Core.getPlugin(Core.class); // Method to get the plugin from other classes, so you can use Core.plugin() in other classes to get the plugin
    }
    
    public static Party getParty(UUID playerUUID) {
        for (Party party : getParties().values()) if (party.isMember(playerUUID)) return party;
        return null;
    }
    
    public void savePartyData() {
        try {
            partyConfig.save(partyFile);;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
