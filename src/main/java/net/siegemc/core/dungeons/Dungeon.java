package net.siegemc.core.dungeons;

import org.bukkit.Bukkit;
import org.bukkit.Location;

class Dungeon {

    public final DungeonType dungeonType;
    public final int index;

    private Dungeon(DungeonType dungeonType, int index) {
        this.dungeonType = dungeonType;
        this.index = index;
    }

    public void reloadSchematic() {
        Location loc = new Location(Bukkit.getWorld("dungeons"), index * dungeonType.dungeonDistance, 128, 500 * index);

    }
    /*public void spawnDungeonBoss() throws InvalidMobTypeException {
        try {
            Entity spawnedMob = new BukkitAPIHelper().spawnMythicMob(bossName, dungeonLocation); //change Dungeon Location to the boss spawn location variable
        } catch (InvalidMobTypeException e) {
            throw new InvalidMobTypeException("MythicMob type " + bossName + " is invalid.");
        }
    }*/
}
