package net.siegemc.core.items.DropTable;

import net.siegemc.core.items.ItemLibrary.Items;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class BlockDrops {

    short expReward = 0; //Won't give exp
    ArrayList<ItemStack> itemRewards = new ArrayList<>(); //List of items that will be dropped
    short blockRegenDelay = 20; //1 second block regeneration
    double luckChance = 0;
    double extraDropChance = 0;

    public enum droppableBlocks {
        GRASS_BLOCK, DIRT, COARSE_DIRT, PODZOL, STONE, LIGHT_GRAY_CONCRETE, ANDESITE, GREEN_TERRACOTTA, GREEN_CONCRETE, LIME_TERRACOTTA
    }

    public void giveBlockDrops(Material blockType, Player player) {
        //this.luckChance = Stats.getLuck(player);
        switch(blockType.toString()) {
            case "GRASS_BLOCK":
                calcDrop(Items.searchMaterialLibrary("DIRT_CLUMP", 1, 1),30);
                calcDrop(Items.searchMaterialLibrary("SEED", 1, 1), 10);
                calcDrop(Items.searchMaterialLibrary("TURF", 1, 1), 5);
                calcDrop(Items.searchMaterialLibrary("MOSS", 1, 1), 1);
                calcDrop(Items.searchMaterialLibrary("MOSSY_DIRT", 1, 1), 0.5);
                break;
            case "DIRT":
            case "COARSE_DIRT":
                calcDrop(Items.searchMaterialLibrary("DIRT_CLUMP", 1, 1), 50);
                calcDrop(Items.searchMaterialLibrary("PEBBLE",1, 1), 25);
                break;
            case "PODZOL":
                calcDrop(Items.searchMaterialLibrary("DIRT_CLUMP", 1, 1), 80);
                break;
            case "STONE":
            case "LIGHT_GRAY_CONCRETE":
            case "ANDESITE":
                calcDrop(Items.searchMaterialLibrary("PEBBLE",1, 1), 75);
                calcDrop(Items.searchMaterialLibrary("ROCK", 1, 1), 10);
                calcDrop(Items.searchMaterialLibrary("STONE", 1, 1), 1);
                break;
            case "GREEN_TERRACOTTA":
            case "GREEN_CONCRETE":
            case "LIME_TERRACOTTA":
                calcDrop(Items.searchMaterialLibrary("TURF", 1, 1), 25);
                calcDrop(Items.searchMaterialLibrary("SEED", 1, 1), 15);
                calcDrop(Items.searchMaterialLibrary("MOSS", 1, 1), 5);
                break;
            case "VINE":
                calcDrop(Items.searchMaterialLibrary("VINE", 1, 1),100);
                break;
            case "SPRUCE_WOOD":
            case "SPRUCE_LOG":
            case "OAK_WOOD":
            case "OAK_PLANK":
                calcDrop(Items.searchMaterialLibrary("WOOD", 1, 1),100);
                break;
            case "IRON_ORE":
                calcDrop(Items.searchMaterialLibrary("METAL_SCRAP", 1, 1),100);
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

        if (((Math.random() * 100) + 1) <= (dropChance + extraDropChance)) { //Default chance to get a grass block
            if (((Math.random() * 100) + 1) <= (luckChance/2)) { //Adds another item if player is lucky
                itemRewards.add(item);
            }
            itemRewards.add(item);
        }
    }
}
