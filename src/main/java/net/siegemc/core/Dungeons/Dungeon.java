package net.siegemc.core.Dungeons;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Dungeon {
    public Location dungeonLocation; //(stores coordinate, = new Location(world, x, y, z);)
    public Player[] dungeonPlayers; //(stores players, player input, Need to create a party object to set list)
    private Entity[] dungeonMobs; //(stores amount of dungeon mobs and which type, this.dungeonMobs[] = need to figure out how to use dungeon level to set dungeon mobs)
    private Task[] dungeonTasks; //(store all tasks required to complete before boss spawn, this.dungeonTasks = need to create a task class that generates a specified task using methods)
    private Entity[] dungeonBoss; //(stores boss mob, this.dungeonBoss = need to figure out how to use dungeon level to set dungeon boss)
    public int dungeonLevel; //(stores dungeon level & reward multiplier, player input, = 1;)
    private boolean dungeonFinished = false; //(stores boolean if dungeon is finished, = true;)

    public Dungeon(Player[] partyMembers, int level) {
        this.dungeonPlayers = partyMembers;
        this.dungeonLevel = level;
        //Code to figure out how to use the level to set dungeon tasks and boss variables (grab from a yaml plugin file?)
        //Code to search for a vacant location within the world's 2D grid. Sets dungeonLocation, generates blocks of dungeon schematic
    }

    private Location getDungeonLocation() {
        return dungeonLocation;
    }
    private Player[] getDungeonPlayers() {
        return dungeonPlayers;
    }
    private Entity[] getDungeonEntity() {
        return dungeonMobs;
    }
    private Task[] getDungeonTasks() {
        return dungeonTasks;
    }
    private int getDungeonLevel() {
        return dungeonLevel;
    }


}
