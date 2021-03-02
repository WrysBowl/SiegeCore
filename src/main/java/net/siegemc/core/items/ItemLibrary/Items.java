package net.siegemc.core.items.ItemLibrary;

import net.siegemc.core.utils.Utils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Items {
    public enum Tools { //Used to check when to set gamemode to adventure/survival
        WOODEN_PICKAXE, WOODEN_AXE, WOODEN_HOE, WOODEN_SHOVEL,
        STONE_PICKAXE, STONE_AXE, STONE_HOE, STONE_SHOVEL,
        GOLDEN_PICKAXE, GOLDEN_AXE, GOLDEN_HOE, GOLDEN_SHOVEL,
        DIAMOND_PICKAXE, DIAMOND_AXE, DIAMOND_HOE, DIAMOND_SHOVEL,
        NETHERITE_PICKAXE, NETHERITE_AXE, NETHERITE_HOE, NETHERITE_SHOVEL;
    }
    public static Tools checkTools(Material toolType) { //Search the Tools enum
        try {
            return Tools.valueOf(toolType.toString());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }



    public static ItemStack searchItemLibrary(String itemName, Integer tier, Integer amount) {
        switch(itemName) {
            case "DIRT_CLUMP":
                switch(tier) {
                    case 1:
                        return Utils.createItem(Material.DARK_OAK_BUTTON, Utils.tacc("&7Dirt Clump &7✪"), false, amount);
                    case 2:
                        return Utils.createItem(Material.DARK_OAK_BUTTON, Utils.tacc("&aDirt Clump &7✪✪"), false, amount);
                    case 3:
                        return Utils.createItem(Material.DARK_OAK_BUTTON, Utils.tacc("&9Dirt Clump &7✪✪✪"), false, amount);
                    case 4:
                        return Utils.createItem(Material.DARK_OAK_BUTTON, Utils.tacc("&5Dirt Clump &7✪✪✪✪"), false, amount);
                    case 5:
                        return Utils.createItem(Material.DARK_OAK_BUTTON, Utils.tacc("&6Dirt Clump &7✪✪✪✪✪"), false, amount);
                }
            case "SEED":
                switch(tier) {
                    case 1:
                        return Utils.createItem(Material.WHEAT_SEEDS, Utils.tacc("&7Seed &7✪"), false, amount);
                    case 2:
                        return Utils.createItem(Material.WHEAT_SEEDS, Utils.tacc("&aSeed &7✪✪"), false, amount);
                    case 3:
                        return Utils.createItem(Material.WHEAT_SEEDS, Utils.tacc("&9Seed &7✪✪✪"), false, amount);
                    case 4:
                        return Utils.createItem(Material.WHEAT_SEEDS, Utils.tacc("&5Seed &7✪✪✪✪"), false, amount);
                    case 5:
                        return Utils.createItem(Material.WHEAT_SEEDS, Utils.tacc("&6Seed &7✪✪✪✪✪"), false, amount);
                }
            case "TURF":
                switch(tier) {
                    case 1:
                        return Utils.createItem(Material.GREEN_CARPET, Utils.tacc("&7Turf &7✪"), false, amount);
                    case 2:
                        return Utils.createItem(Material.GREEN_CARPET, Utils.tacc("&aTurf &7✪✪"), false, amount);
                    case 3:
                        return Utils.createItem(Material.GREEN_CARPET, Utils.tacc("&9Turf &7✪✪✪"), false, amount);
                    case 4:
                        return Utils.createItem(Material.GREEN_CARPET, Utils.tacc("&5Turf &7✪✪✪✪"), false, amount);
                    case 5:
                        return Utils.createItem(Material.GREEN_CARPET, Utils.tacc("&6Turf &7✪✪✪✪✪"), false, amount);
                }
            case "MOSS":
                switch (tier) {
                    case 1:
                        return Utils.createItem(Material.GREEN_DYE, Utils.tacc("&7Moss &7✪"), false, amount);
                    case 2:
                        return Utils.createItem(Material.GREEN_DYE, Utils.tacc("&aMoss &7✪✪"), false, amount);
                    case 3:
                        return Utils.createItem(Material.GREEN_DYE, Utils.tacc("&9Moss &7✪✪✪"), false, amount);
                    case 4:
                        return Utils.createItem(Material.GREEN_DYE, Utils.tacc("&5Moss &7✪✪✪✪"), false, amount);
                    case 5:
                        return Utils.createItem(Material.GREEN_DYE, Utils.tacc("&6Moss &7✪✪✪✪✪"), false, amount);
                }
            case "MOSSY_DIRT":
                switch (tier) {
                    case 1:
                        return Utils.createItem(Material.GRASS_BLOCK, Utils.tacc("&7Mossy Dirt &7✪"), false, amount);
                    case 2:
                        return Utils.createItem(Material.GRASS_BLOCK, Utils.tacc("&aMossy Dirt &7✪✪"), false, amount);
                    case 3:
                        return Utils.createItem(Material.GRASS_BLOCK, Utils.tacc("&9Mossy Dirt &7✪✪✪"), false, amount);
                    case 4:
                        return Utils.createItem(Material.GRASS_BLOCK, Utils.tacc("&5Mossy Dirt &7✪✪✪✪"), false, amount);
                    case 5:
                        return Utils.createItem(Material.GRASS_BLOCK, Utils.tacc("&6Mossy Dirt &7✪✪✪✪✪"), false, amount);
                }
            case "PEBBLE":
                switch (tier) {
                    case 1:
                        return Utils.createItem(Material.STONE_BUTTON, Utils.tacc("&7Pebble &7✪"), false, amount);
                    case 2:
                        return Utils.createItem(Material.STONE_BUTTON, Utils.tacc("&aPebble &7✪✪"), false, amount);
                    case 3:
                        return Utils.createItem(Material.STONE_BUTTON, Utils.tacc("&9Pebble &7✪✪✪"), false, amount);
                    case 4:
                        return Utils.createItem(Material.STONE_BUTTON, Utils.tacc("&5Pebble &7✪✪✪✪"), false, amount);
                    case 5:
                        return Utils.createItem(Material.STONE_BUTTON, Utils.tacc("&6Pebble &7✪✪✪✪✪"), false, amount);
                }
            case "ROCK":
                switch (tier) {
                    case 1:
                        return Utils.createItem(Material.GRAY_DYE, Utils.tacc("&7Rock &7✪"), false, amount);
                    case 2:
                        return Utils.createItem(Material.GRAY_DYE, Utils.tacc("&aRock &7✪✪"), false, amount);
                    case 3:
                        return Utils.createItem(Material.GRAY_DYE, Utils.tacc("&9Rock &7✪✪✪"), false, amount);
                    case 4:
                        return Utils.createItem(Material.GRAY_DYE, Utils.tacc("&5Rock &7✪✪✪✪"), false, amount);
                    case 5:
                        return Utils.createItem(Material.GRAY_DYE, Utils.tacc("&6Rock &7✪✪✪✪✪"), false, amount);
                }
            case "STONE":
                switch (tier) {
                    case 1:
                        return Utils.createItem(Material.STONE, Utils.tacc("&7Stone &7✪"), false, amount);
                    case 2:
                        return Utils.createItem(Material.STONE, Utils.tacc("&aStone &7✪✪"), false, amount);
                    case 3:
                        return Utils.createItem(Material.STONE, Utils.tacc("&9Stone &7✪✪✪"), false, amount);
                    case 4:
                        return Utils.createItem(Material.STONE, Utils.tacc("&5Stone &7✪✪✪✪"), false, amount);
                    case 5:
                        return Utils.createItem(Material.STONE, Utils.tacc("&6Stone &7✪✪✪✪✪"), false, amount);
                }
        }
        return null;
    }
}
