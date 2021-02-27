package net.siegemc.core.items.ItemEnums;

import net.siegemc.core.informants.Stats;
import net.siegemc.core.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class Drops {

    short expReward = 0; //Won't give exp
    ArrayList<ItemStack> itemRewards = new ArrayList<>(); //List of items that will be dropped
    short blockRegenDelay = 20; //1 second block regeneration
    double luckChance = 0;

    public enum allDrops {
        GRASS_BLOCK, DIRT, COARSE_DIRT, PODZOL, STONE, LIGHT_GRAY_CONCRETE, ANDESITE, GREEN_TERRACOTTA, GREEN_CONCRETE, LIME_TERRACOTTA
    }

    public void giveBlockDrops(Material blockType, Player player) {
        this.luckChance = Stats.getLuck(player);
        switch(blockType.toString()) {
            case "GRASS_BLOCK":
                calcDrop(Utils.createItem(Material.DARK_OAK_BUTTON, Utils.tacc("&fDirt Clump &7✪"), false), 30);
                calcDrop(Utils.createItem(Material.WHEAT_SEEDS, Utils.tacc("&fSeed &7✪"), false), 10);
                calcDrop(Utils.createItem(Material.GREEN_CARPET, Utils.tacc("&fTurf &7✪"), false), 5);
                calcDrop(Utils.createItem(Material.GREEN_DYE, Utils.tacc("&fMoss &7✪"), false), 1);
                calcDrop(Utils.createItem(Material.GRASS_BLOCK, Utils.tacc("&fMossy Dirt &7✪"), false), 0.5);
                break;
            case "DIRT":
            case "COARSE_DIRT":
                calcDrop(Utils.createItem(Material.DARK_OAK_BUTTON, Utils.tacc("&fDirt Clump &7✪"), false), 50);
                calcDrop(Utils.createItem(Material.STONE_BUTTON, Utils.tacc("&fPebble &7✪"), false), 25);
                break;
            case "PODZOL":
                calcDrop(Utils.createItem(Material.DARK_OAK_BUTTON, Utils.tacc("&fDirt Clump &7✪"), false), 80);
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
                calcDrop(Utils.createItem(Material.GREEN_CARPET, Utils.tacc("&fTurf &7✪"), false), 25);
                calcDrop(Utils.createItem(Material.WHEAT_SEEDS, Utils.tacc("&fSeed &7✪"), false), 15);
                calcDrop(Utils.createItem(Material.GREEN_DYE, Utils.tacc("&fMoss &7✪"), false), 5);
                break;
        }
    }
    public static allDrops checkAllDrops(Material blockType) { //Check to make sure the block has a loot table
        try {
            return allDrops.valueOf(blockType.toString());
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
