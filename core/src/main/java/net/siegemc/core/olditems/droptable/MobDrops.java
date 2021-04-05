package net.siegemc.core.olditems.droptable;

import io.lumine.xikage.mythicmobs.mobs.MythicMob;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class MobDrops {

    short expReward = 0; //Won't give exp
    ArrayList<ItemStack> itemRewards = new ArrayList<>(); //List of items that will be dropped
    double luckChance = 0;
    double extraDropChance = 0;

    public enum droppableMobs {
        FeatheredMeat, Porky, Wooly, MooMoo, Pigeon, Sushi,
        Bandit, BanditArcher, Blob, ScorchingBlob, Goblin, Ogre, Orc, SkeletalLegionnare, SkeletalBowman, InfectedDigger, ZombifiedDigger
    }

    public void giveMobDrops(MythicMob entityType, Player player) {
        //this.luckChance = Stats.getLuck(player);
        /*switch(entityType.getInternalName()) {
            case "Pigeon":
            case "FeatheredMeat":
                calcDrop(Items.searchMaterialLibrary("FEATHER", 1, 1),50);
                calcDrop(Items.searchMaterialLibrary("DRUMSTICK", 1, 1),50);
                break;
            case "Porky":
                calcDrop(Items.searchMaterialLibrary("DRUMSTICK", 1, 1),75);
                break;
            case "Wooly":
                calcDrop(new Wool(0, 1).getItem(), 50);
                calcDrop(new Drumstick(0, 1).getItem(),50);
                break;
            case "MooMoo":
                calcDrop(Items.searchMaterialLibrary("LEATHER", 1, 1),100);
                calcDrop(Items.searchMaterialLibrary("DRUMSTICK", 1, 1),100);
                break;
            case "SUSHI":
                calcDrop(Items.searchMaterialLibrary("DRUMSTICK", 1, 1),25);
            case "Blob":
                calcDrop(new Slime(0, 1).getItem(), 100);
                break;
            case "ScorchingBlob":
                calcDrop(Items.searchMaterialLibrary("MAGMA", 1, 1), 100);
                break;
            case "InfectedDigger":
            case "ZombifiedDigger":
                calcDrop(new Ectoplasm(0, 1).getItem(), 100);
                break;
            case "SkeletalLegionnare":
            case "SkeletalBowman":
                calcDrop(Items.searchMaterialLibrary("BONE", 1, 1), 100);
                break;
        }*/
    }
    public static droppableMobs checkAllDrops(MythicMob entityType) { //Check to make sure the block has a loot table
        try {
            return droppableMobs.valueOf(entityType.getInternalName());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
    public int getExpReward() {
        return expReward;
    }
    public ArrayList<ItemStack> getItemRewards() {
        return itemRewards;
    }
    private void calcDrop(ItemStack item, double dropChance) {

        if (((Math.random() * 100) + 1) <= (dropChance + extraDropChance)) { //Default chance to get a grass block
            if (((Math.random() * 100) + 1) <= (luckChance/2)) { //Adds another item if player is lucky
                itemRewards.add(item);
            }
            itemRewards.add(item);
        }
    }
}
