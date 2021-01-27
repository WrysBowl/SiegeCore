package net.siegemc.core.Dungeons;

public class DungeonBosses {
    private static String bossNames[] = new String[] {
            "KingSlime", "BanditKing", "ZombieKing"
    };
    public static String dungeonBoss(int dungeonLevel){
        return bossNames[dungeonLevel-1];
    }
}
