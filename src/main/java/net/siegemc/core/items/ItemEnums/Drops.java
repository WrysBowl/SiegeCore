package net.siegemc.core.items.ItemEnums;

import net.siegemc.core.informants.Stats;
import net.siegemc.core.utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class Drops {

    short expReward = 0; //Won't give exp
    ArrayList<ItemStack> itemRewards = new ArrayList<>(); //List of items that will be dropped
    short blockRegenDelay = 20; //1 second block regeneration
    double luckChance = 0;

    public enum droppableBlocks {
        GRASS_BLOCK, DIRT, COARSE_DIRT, PODZOL, STONE, LIGHT_GRAY_CONCRETE, ANDESITE, GREEN_TERRACOTTA, GREEN_CONCRETE, LIME_TERRACOTTA
    }

    public void giveBlockDrops(Material blockType, Player player) {
        this.luckChance = Stats.getLuck(player);
        switch(blockType.toString()) {
            case "GRASS_BLOCK":
                calcDrop(Items.searchItemLibrary("DIRT_CLUMP", 1),30);
                calcDrop(Items.searchItemLibrary("SEED", 1), 10);
                calcDrop(Items.searchItemLibrary("TURF", 1), 5);
                calcDrop(Items.searchItemLibrary("MOSS", 1), 1);
                calcDrop(Items.searchItemLibrary("MOSSY_DIRT", 1), 0.5);
                break;
            case "DIRT":
            case "COARSE_DIRT":
                calcDrop(Items.searchItemLibrary("DIRT_CLUMP", 1), 50);
                calcDrop(Utils.createItem(Material.STONE_BUTTON, Utils.tacc("&fPebble &7✪"), false), 25);
                break;
            case "PODZOL":
                calcDrop(Items.searchItemLibrary("DIRT_CLUMP", 1), 80);
                break;
            case "STONE":
            case "LIGHT_GRAY_CONCRETE":
            case "ANDESITE":
                calcDrop(Utils.createItem(Material.STONE_BUTTON, Utils.tacc("&fPebble &7✪"), false), 75);
                calcDrop(Utils.createItem(Material.GRAY_DYE, Utils.tacc("&fRock &7✪"), false), 10);
                calcDrop(Utils.createItem(Material.STONE, Utils.tacc("&fStone &7✪"), false), 1);
                break;
            case "GREEN_TERRACOTTA":
            case "GREEN_CONCRETE":
            case "LIME_TERRACOTTA":
                calcDrop(Items.searchItemLibrary("TURF", 1), 25);
                calcDrop(Items.searchItemLibrary("SEED", 1), 15);
                calcDrop(Items.searchItemLibrary("MOSS", 1), 5);
                break;
        }
    }
    public static droppableBlocks checkAllDrops(Material blockType) { //Check to make sure the block has a loot table
        try {
            return droppableBlocks.valueOf(blockType.toString());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
    public short getExpReward() {
        return expReward;
    }
    public ArrayList<ItemStack> getItemRewards() {
        return itemRewards;
    }
    public short getBlockRegenDelay() {
        return blockRegenDelay;
    }
    private void calcDrop(ItemStack item, double dropChance) {

        if (((Math.random() * 100) + 1) <= dropChance) { //Default chance to get a grass block
            if (((Math.random() * 100) + 1) <= luckChance/2) { //Adds another item if player is lucky
                itemRewards.add(item);
            }
            itemRewards.add(item);
        }
    }
}
