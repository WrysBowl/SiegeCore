package net.siegemc.core.dungeons;

public class DungeonBosses {
    enum BossNames {
        KingSlime,
        BanditKing,
        ZombieKing
    }

    public static String dungeonBoss(int dungeonLevel) {
        return BossNames.values()[dungeonLevel].name();
    }
}
