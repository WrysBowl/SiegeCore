package net.siegemc.core;

import lombok.Getter;
import net.siegemc.core.dungeons.DungeonConfig;
import net.siegemc.core.items.CreateItems.CustomItem;
import net.siegemc.core.items.CreateItems.ItemConfig;
import net.siegemc.core.items.CreateItems.SpawnItemCommand;
import net.siegemc.core.items.Recipes.CustomShapedRecipe;
import net.siegemc.core.items.Recipes.CustomShapelessRecipe;
import net.siegemc.core.items.Recipes.Recipes;
import net.siegemc.core.items.listeners.CustomItemKotlinListener;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public final class Core extends JavaPlugin {
    @Getter private static final HashMap<UUID, Party> parties = new HashMap<>();
    @Getter private static final HashMap<String, CustomItem> items = new HashMap<>();
    public static Location spawnLocation;
    private List<CustomShapelessRecipe> shapelessRecipes = new ArrayList<>();
    private List<CustomShapedRecipe> shapedRecipes = new ArrayList<>();

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
        Bukkit.getPluginManager().registerEvents(new Join(), this);
        Bukkit.getPluginManager().registerEvents(new WorldProtection(), this);
        Bukkit.getPluginManager().registerEvents(new CustomItemListener(), this);
        Bukkit.getPluginManager().registerEvents(new PickUp(), this);
        Bukkit.getPluginManager().registerEvents(new Chat(), this);
        Bukkit.getPluginManager().registerEvents(new Quit(), this);
        Bukkit.getPluginManager().registerEvents(new Food(), this);
        Bukkit.getPluginManager().registerEvents(new ToolChange(), this);
        Bukkit.getPluginManager().registerEvents(new Break(), this);
        Bukkit.getPluginManager().registerEvents(new CustomCraftingEvents(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClose(), this);
        Bukkit.getPluginManager().registerEvents(new StatGems(), this);
        Bukkit.getPluginManager().registerEvents(new CustomItemKotlinListener(), this);

        // Register Commands
        PartyCommand partyCommand = new PartyCommand();
        Bukkit.getPluginCommand("party").setExecutor(partyCommand);
        Bukkit.getPluginCommand("party").setTabCompleter(partyCommand);
        Bukkit.getPluginCommand("spawnitem").setExecutor(new SpawnItemCommand());

        // Register Recipes
        Recipes recipes = new Recipes();
        for (CustomShapelessRecipe recipe : recipes.getShapelessRecipes()) {
            addRecipe(recipe);
        }
        for (CustomShapedRecipe recipe : recipes.getShapedRecipes()) {
            addRecipe(recipe);
        }
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

    public void addRecipe(CustomShapedRecipe recipe) {
        shapedRecipes.add(recipe);
    }

    public void addRecipe(CustomShapelessRecipe recipe) {
        shapelessRecipes.add(recipe);
    }

    public List<CustomShapedRecipe> getShapedRecipes() {
        return shapedRecipes;
    }
    public List<CustomShapelessRecipe> getShapelessRecipes(){
        return shapelessRecipes;
    }

}
