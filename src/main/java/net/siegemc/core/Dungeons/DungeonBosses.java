package net.siegemc.core.Dungeons;

public class DungeonBosses {
    enum BossNames {
        KingSlime,
        BanditKing,
        ZombieKing
    }

    public static String dungeonBoss(int dungeonLevel) { // Gets the dungeon boss from an index, we could probably convert this all to a more advanced system to get the entity, spawn it in a dungeon, etc, using the enum (like I,DeltaRays did for DungeonType)
        return BossNames.values()[dungeonLevel].name();
    }
}
