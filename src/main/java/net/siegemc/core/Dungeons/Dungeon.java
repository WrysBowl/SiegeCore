package net.siegemc.core.Dungeons;

import io.lumine.xikage.mythicmobs.api.bukkit.BukkitAPIHelper;
import io.lumine.xikage.mythicmobs.api.exceptions.InvalidMobTypeException;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Dungeon {
    public Location dungeonLocation; //(stores coordinate, = new Location(world, x, y, z);)
    public Player[] dungeonPlayers; //(stores players, player input, Need to create a party object to set list)
    private Task[] dungeonTasks; //(store all tasks required to complete before boss spawn, this.dungeonTasks = need to create a task class that generates a specified task using methods)
    private Entity dungeonBoss; //(stores boss mob, this.dungeonBoss = need to figure out how to use dungeon level to set dungeon boss)
    public int dungeonLevel; //(stores dungeon level & reward multiplier, player input, = 1;)
    private String bossName;

    public Dungeon(int level) {
        //this.dungeonPlayers = partyMembers;
        this.dungeonLevel = level;
        this.bossName = DungeonBosses.dungeonBoss(level);
        //Code to figure out how to use the level to set dungeon tasks and boss variables (grab from a yaml plugin file?)
        //Code to search for a vacant location within the world's 2D grid. Sets dungeonLocation, generates blocks of dungeon schematic
    }

    private Location getDungeonLocation() {
        return dungeonLocation;
    }
    private Player[] getDungeonPlayers() {
        return dungeonPlayers;
    }
    private Task[] getDungeonTasks() {
        return dungeonTasks;
    }
    public String getBossName() {
        return bossName;
    }
    private int getDungeonLevel() {
        return dungeonLevel;
    }
    /*public void spawnDungeonBoss() throws InvalidMobTypeException {
        try {
            Entity spawnedMob = new BukkitAPIHelper().spawnMythicMob(bossName, dungeonLocation); //change Dungeon Location to the boss spawn location variable
        } catch (InvalidMobTypeException e) {
            throw new InvalidMobTypeException("MythicMob type " + bossName + " is invalid.");
        }
    }*/


}
