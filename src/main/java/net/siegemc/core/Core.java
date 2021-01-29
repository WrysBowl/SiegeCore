package net.siegemc.core;

import lombok.Getter;
import net.siegemc.core.events.ConnectEvent;
import net.siegemc.core.party.Party;
import net.siegemc.core.party.PartyCommand;
import net.siegemc.core.party.PartySaving;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public final class Core extends JavaPlugin {
    @Getter
    private final PartySaving partySaving = new PartySaving();
    @Getter
    private static final HashMap<UUID, Party> parties = new HashMap<>();
    
    @SuppressWarnings({"ResultOfMethodCallIgnored", "ConstantConditions"})
    @Override
    public void onEnable() {
        if (!getDataFolder().exists()) getDataFolder().mkdir();
        partySaving.FileExists();
        
        (new VaultHook()).createHooks(); // Add the hooks to the vault plugin
        //DbManager.create(); // Create the initial connections
        
        Bukkit.getPluginManager().registerEvents(new ConnectEvent(), this); // Register the connection event
        Bukkit.getPluginCommand("party").setExecutor(new PartyCommand());
    }
    
    @Override
    public void onDisable() {
    }
    
    public static Core plugin() {
        return Core.getPlugin(Core.class); // Method to get the plugin from other classes, so you can use Core.plugin() in other classes to get the plugin
    }
    
    public static Party getParty(UUID playerUUID) {
        for (Party party : parties.values()) if (party.isMember(playerUUID)) return party;
        return null;
    }
}
