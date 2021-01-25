package net.siegemc.core.Dungeons;

public class DungeonBosses {
    public static String dungeonBoss(int dungeonLevel){
        String bossName = null;
        switch(dungeonLevel){
            case 1:
                bossName = "KingSlime";
                break;
            case 2:
                bossName = "BanditKing";
                break;
            case 3:
                bossName = "ZombieKing";
                break;
        }
        return bossName;
    }
}
