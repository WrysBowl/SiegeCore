package net.siegemc.core.dungeons;

// Unfinished, the class containing an actual dungeon, not the dungeon type (in DungeonType.java)
class Dungeon {

    public final DungeonType dungeonType;
    public final int index;

    private Dungeon(DungeonType dungeonType, int index) {
        this.dungeonType = dungeonType;
        this.index = index;
    }

    /*public void spawnDungeonBoss() throws InvalidMobTypeException {
        try {
            Entity spawnedMob = new BukkitAPIHelper().spawnMythicMob(bossName, dungeonLocation); //change Dungeon Location to the boss spawn location variable
        } catch (InvalidMobTypeException e) {
            throw new InvalidMobTypeException("MythicMob type " + bossName + " is invalid.");
        }
    }*/
}
