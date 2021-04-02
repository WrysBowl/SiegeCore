package net.siegemc.core.dungeons;

import com.sk89q.worldedit.WorldEditException;
import net.siegemc.core.Core;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.HashSet;
import java.util.UUID;
/*
       1: # index of the dungeon
         players: []

 */


/**
 * The Dungeon class.
 * It represents a single dungeon of a specific type, can be used to add players to it, remove players from, reset, etc.
 */
public class Dungeon {
    /**
     * The type of the dungeon.
     */
    public final DungeonType type;
    public final int index;
    public final Location location;
    private HashSet<OfflinePlayer> currentPlayers = new HashSet<>();

    /**
     * Creates a dungeon instance with a specific type and index
     *
     * @param dungeonType
     * @param index
     */
    Dungeon(DungeonType dungeonType, int index) {
        this.type = dungeonType;
        this.index = index;
        location = new Location(DungeonType.world, type.dungeonDistance * index /*This is for each dungeon's distance*/, 128, 500 * type.index /* This is for each dungeon type's distance */);
    }

    /**
     * Adds a player to the dungeon
     *
     * @param player
     */
    public void addPlayer(Player player) {
        if (!currentPlayers.contains(player)) {
            currentPlayers.add(player);
            ConfigurationSection dungeon = DungeonConfig.getDungeon(type, index);
            if (dungeon.contains("players"))
                dungeon.getStringList("players").add(player.getUniqueId().toString());
            else
                dungeon.set("players", new String[]{player.getUniqueId().toString()});
        } else
            player.teleport(location.clone().add(type.spawnLocation));
    }

    /**
     * Removes a player from the dungeon
     *
     * @param player The player to remove
     */
    public void removePlayer(OfflinePlayer player) {
        if (currentPlayers.contains(player)) {
            currentPlayers.remove(player);
            ConfigurationSection dungeon = DungeonConfig.getDungeon(type, index);
            if (dungeon.contains("players"))
                dungeon.getStringList("players").remove(player.getUniqueId().toString());
        }
        if (player.isOnline()) {
            Player p = (Player) player;
            p.teleport(Core.spawnLocation);
        }
    }

    /**
     * @return Returns the list of players in the dungeon
     */
    public HashSet<OfflinePlayer> listPlayers() {
        return currentPlayers;
    }

    /**
     * Deletes the dungeon and its config
     */
    protected void delete() {
        type.dungeons.remove(this);
        for (OfflinePlayer currentPlayer : currentPlayers)
            removePlayer(currentPlayer);
        DungeonConfig.getDungeons(type).set(String.valueOf(index), null);
    }

    /**
     * Reset the dungeon (remove all players and revert to the original schematic)
     */
    protected void reset() {
        type.dungeons.remove(this);
        for (OfflinePlayer currentPlayer : currentPlayers)
            removePlayer(currentPlayer);
        try {
            SchematicPaster.pasteSchematic(type.schematic, location, "SPONGE", false);
        } catch (IOException | WorldEditException e) {
            e.printStackTrace();
        }
        type.dungeons.add(this);
       // this.spawnBoss();
    }
    /*
    public void spawnBoss() {
        if (type.bossLocation == null || type.boss == null) {
            Core.plugin().getLogger().warning("The boss can't be spawned in a dungeon with no boss! Dungeon type: " + type.name);
            return;
        }
        Location bossLoc = location.clone().add(type.bossLocation);
        type.boss.spawn(new AbstractLocation(new BukkitWorld(DungeonType.world), bossLoc.getX(), bossLoc.getY(), bossLoc.getZ()), 1 /*TODO Wrys what is the level*//*);
    }
    */
    /**
     * Convert the selected configuration, index and type into a dungeon
     *
     * @param section
     * @param index
     */
    protected static Dungeon deserialize(ConfigurationSection section, int index, DungeonType type) {
        Dungeon dungeon = new Dungeon(type, index);
        section.getStringList("players").forEach(uuid -> {
            OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(uuid));
            dungeon.currentPlayers.add(player);
        });
        return dungeon;
    }


}










    /* Wrys code

    public void spawnDungeonBoss() throws InvalidMobTypeException {
        try {
            Entity spawnedMob = new BukkitAPIHelper().spawnMythicMob(bossName, dungeonLocation); //change Dungeon Location to the boss spawn location variable
        } catch (InvalidMobTypeException e) {
            throw new InvalidMobTypeException("MythicMob type " + bossName + " is invalid.");
        }
    }*/