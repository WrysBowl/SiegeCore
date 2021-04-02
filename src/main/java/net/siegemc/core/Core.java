package net.siegemc.core;

import co.aikar.commands.PaperCommandManager;
import lombok.Getter;
import net.siegemc.core.dungeons.DungeonConfig;
import net.siegemc.core.listeners.*;
import net.siegemc.core.olditems.recipes.CustomShapedRecipe;
import net.siegemc.core.olditems.recipes.CustomShapelessRecipe;
import net.siegemc.core.party.Party;
import net.siegemc.core.party.PartyCommand;
import net.siegemc.core.party.PartyConfig;
import net.siegemc.core.utils.DbManager;
import net.siegemc.core.utils.VaultHook;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

public final class Core extends JavaPlugin {
    @Getter private static final HashMap<UUID, Party> parties = new HashMap<>();
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
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new WorldProtectionListener(), this);
        Bukkit.getPluginManager().registerEvents(new ItemPickupListener(), this);
        Bukkit.getPluginManager().registerEvents(new ChatListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), this);
        Bukkit.getPluginManager().registerEvents(new FoodListener(), this);
        Bukkit.getPluginManager().registerEvents(new ToolChangeListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockBreakListener(), this);
        Bukkit.getPluginManager().registerEvents(new CustomCraftingEvents(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryCloseListener(), this);
        Bukkit.getPluginManager().registerEvents(new StatGemListener(), this);
        Bukkit.getPluginManager().registerEvents(new CustomItemKotlinListener(), this);
        new RegenerationTask().startRegenTask();

        // Register Commands
//        PaperCommandManager manager = new PaperCommandManager(this);
//        //manager.registerCommand(new PartyCommand()); TODO(GET RID OF OTHER PARTY CMD CLASS)
//        manager.getCommandCompletions().registerCompletion("partyMembers", c -> {
//            Party party = getParty(c.getPlayer().getUniqueId());
//            List<UUID> members = party.getMembersRaw();
//            List<String> names = new ArrayList<String>();
//            for (UUID member : members) {
//                names.add(Bukkit.getPlayer(member).getName());
//            }
//            return names;
//        });
//
//        manager.getCommandCompletions().registerCompletion("partyMembersExcludingSelf", c -> {
//            Party party = getParty(c.getPlayer().getUniqueId());
//            List<UUID> members = party.getMembersRaw();
//            List<String> names = new ArrayList<String>();
//            for (UUID member : members) {
//                if (member != c.getPlayer().getUniqueId()) names.add(Bukkit.getPlayer(member).getName());
//            }
//            return names;
//        });
//
//        manager.getCommandCompletions().registerCompletion("nonPartyMembers", c -> {
//            Party party = getParty(c.getPlayer().getUniqueId());
//            List<UUID> members = party.getMembersRaw();
//            List<String> names = new ArrayList<String>();
//            for (UUID member : members) {
//                names.add(Bukkit.getPlayer(member).getName());
//            }
//            List<String> allPlayerNames = Arrays.stream(Bukkit.getOfflinePlayers()).map(OfflinePlayer::getName)
//                    .collect(Collectors.toList());
//
//            return allPlayerNames.stream().filter(p -> !(names.contains(p))).collect(Collectors.toList());
//        });
//
//        manager.getCommandCompletions().registerCompletion("openToInvite", c -> {
//            Party party = getParty(c.getPlayer().getUniqueId());
//            List<UUID> members = party.getMembersRaw();
//            List<String> names = new ArrayList<String>();
//            for (UUID member : members) {
//                names.add(Bukkit.getPlayer(member).getName());
//            }
//            List<OfflinePlayer> notInParty = Bukkit.getOnlinePlayers().stream().filter(p -> getParty(p.getUniqueId()) == null).collect(Collectors.toList());
//            List<String> allPlayerNames = notInParty.stream().map(OfflinePlayer::getName)
//                    .collect(Collectors.toList());
//
//            return allPlayerNames;
//        });

        PartyCommand partyCommand = new PartyCommand();
        Bukkit.getPluginCommand("party").setExecutor(partyCommand);
        Bukkit.getPluginCommand("party").setTabCompleter(partyCommand);

        // Register Recipes
        /*
        Recipes recipes = new Recipes();
        for (CustomShapelessRecipe recipe : recipes.getShapelessRecipes()) {
            addRecipe(recipe);
        }
        for (CustomShapedRecipe recipe : recipes.getShapedRecipes()) {
            addRecipe(recipe);
        }
         */
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
