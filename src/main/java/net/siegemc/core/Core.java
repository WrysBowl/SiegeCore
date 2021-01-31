package net.siegemc.core;

import lombok.Getter;
import net.siegemc.core.dungeons.DungeonConfig;
import net.siegemc.core.items.SpawnItemCommand;
import net.siegemc.core.items.CustomItem;
import net.siegemc.core.items.items.*;
import net.siegemc.core.listeners.CustomItemListener;
import net.siegemc.core.listeners.DamageIndicators;
import net.siegemc.core.listeners.JoinEvents;
import net.siegemc.core.listeners.WorldProtection;
import net.siegemc.core.party.Party;
import net.siegemc.core.party.PartyCommand;
import net.siegemc.core.party.PartyConfig;
import net.siegemc.core.utils.VaultHook;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

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
        if (!getDataFolder().exists()) getDataFolder().mkdir();
        PartyConfig.createConfig();
        DungeonConfig.createConfig();
        (new VaultHook()).createHooks(); // Add the hooks to the vault plugin
        //DbManager.create(); // Create the initial connections

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
    
        // Register Commands
        PartyCommand partyCommand = new PartyCommand();
        Bukkit.getPluginCommand("party").setExecutor(partyCommand);
        Bukkit.getPluginCommand("party").setTabCompleter(partyCommand);
        Bukkit.getPluginCommand("spawnitem").setExecutor(new SpawnItemCommand());
    }
    
    @Override
    public void onDisable() {
        PartyConfig.reset();
        for (Party party : getParties().values()) party.save(true);
    }
    
    public static Core plugin() {
        return Core.getPlugin(Core.class); // Method to get the plugin from other classes, so you can use Core.plugin() in other classes to get the plugin
    }
    
    private void registerItems() {
        new TestStick();
        new TestBow();
        new TestHelmet();
        new TestWand();
        new TestAxe();
    }
    
    public static Party getParty(UUID playerUUID) {
        for (Party party : getParties().values()) if (party.isMember(playerUUID)) return party;
        return null;
    }
}
