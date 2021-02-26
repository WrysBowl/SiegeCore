package net.siegemc.core.items.ItemEnums;

import net.siegemc.core.informants.Stats;
import net.siegemc.core.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class Drops {

    short expReward;
    ArrayList<ItemStack> itemRewards = new ArrayList<ItemStack>();
    short blockRegenDelay;
    double luckChance;

    public enum allDrops {
        GRASS_BLOCK, STONE
    }

    public void giveBlockDrops(Material blockType, Player player) {
        this.luckChance = Stats.getLuck(player);
        switch(blockType.toString()) {
            case "GRASS_BLOCK":
                this.expReward = 0;
                this.blockRegenDelay = 20;
                calcDrop(Utils.createItem(Material.GRASS_BLOCK, ChatColor.GREEN + "Grass Block", false), 10);
                calcDrop(Utils.createItem(Material.GRASS_BLOCK, ChatColor.GREEN + "Enchanted Grass Block", true), 5);
            case "STONE":

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

        if (((Math.random() * 100) + 1) <= dropChance) { //Adds player's luck value to the default chance to get a grass block
            if (((Math.random() * 100) + 1) <= luckChance/2) { //Adds another item if player is lucky
                itemRewards.add(item);
            }
            itemRewards.add(item);
        }
    }
}
