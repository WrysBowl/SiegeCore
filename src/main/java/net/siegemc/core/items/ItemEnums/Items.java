package net.siegemc.core.items.ItemEnums;

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



    public static ItemStack searchItemLibrary(String itemName, Integer tier) {
        switch(itemName) {
            case "DIRT_CLUMP":
                switch(tier) {
                    case 1:
                        return Utils.createItem(Material.DARK_OAK_BUTTON, Utils.tacc("&7Dirt Clump &7✪"), false);
                    case 2:
                        return Utils.createItem(Material.DARK_OAK_BUTTON, Utils.tacc("&aDirt Clump &7✪✪"), false);
                    case 3:
                        return Utils.createItem(Material.DARK_OAK_BUTTON, Utils.tacc("&9Dirt Clump &7✪✪✪"), false);
                    case 4:
                        return Utils.createItem(Material.DARK_OAK_BUTTON, Utils.tacc("&5Dirt Clump &7✪✪✪✪"), false);
                    case 5:
                        return Utils.createItem(Material.DARK_OAK_BUTTON, Utils.tacc("&6Dirt Clump &7✪✪✪✪✪"), false);
                }
            case "SEED":
                switch(tier) {
                    case 1:
                        return Utils.createItem(Material.WHEAT_SEEDS, Utils.tacc("&7Seed &7✪"), false);
                    case 2:
                        return Utils.createItem(Material.WHEAT_SEEDS, Utils.tacc("&aSeed &7✪✪"), false);
                    case 3:
                        return Utils.createItem(Material.WHEAT_SEEDS, Utils.tacc("&9Seed &7✪✪✪"), false);
                    case 4:
                        return Utils.createItem(Material.WHEAT_SEEDS, Utils.tacc("&5Seed &7✪✪✪✪"), false);
                    case 5:
                        return Utils.createItem(Material.WHEAT_SEEDS, Utils.tacc("&6Seed &7✪✪✪✪✪"), false);
                }
            case "TURF":
                switch(tier) {
                    case 1:
                        return Utils.createItem(Material.GREEN_CARPET, Utils.tacc("&7Turf &7✪"), false);
                    case 2:
                        return Utils.createItem(Material.GREEN_CARPET, Utils.tacc("&aTurf &7✪✪"), false);
                    case 3:
                        return Utils.createItem(Material.GREEN_CARPET, Utils.tacc("&9Turf &7✪✪✪"), false);
                    case 4:
                        return Utils.createItem(Material.GREEN_CARPET, Utils.tacc("&5Turf &7✪✪✪✪"), false);
                    case 5:
                        return Utils.createItem(Material.GREEN_CARPET, Utils.tacc("&6Turf &7✪✪✪✪✪"), false);
                }
            case "MOSS":
                switch (tier) {
                    case 1:
                        return Utils.createItem(Material.GREEN_DYE, Utils.tacc("&7Moss &7✪"), false);
                    case 2:
                        return Utils.createItem(Material.GREEN_DYE, Utils.tacc("&aMoss &7✪✪"), false);
                    case 3:
                        return Utils.createItem(Material.GREEN_DYE, Utils.tacc("&9Moss &7✪✪✪"), false);
                    case 4:
                        return Utils.createItem(Material.GREEN_DYE, Utils.tacc("&5Moss &7✪✪✪✪"), false);
                    case 5:
                        return Utils.createItem(Material.GREEN_DYE, Utils.tacc("&6Moss &7✪✪✪✪✪"), false);
                }
            case "MOSSY_DIRT":
                switch (tier) {
                    case 1:
                        return Utils.createItem(Material.GRASS_BLOCK, Utils.tacc("&7Mossy Dirt &7✪"), false);
                    case 2:
                        return Utils.createItem(Material.GRASS_BLOCK, Utils.tacc("&aMossy Dirt &7✪✪"), false);
                    case 3:
                        return Utils.createItem(Material.GRASS_BLOCK, Utils.tacc("&9Mossy Dirt &7✪✪✪"), false);
                    case 4:
                        return Utils.createItem(Material.GRASS_BLOCK, Utils.tacc("&5Mossy Dirt &7✪✪✪✪"), false);
                    case 5:
                        return Utils.createItem(Material.GRASS_BLOCK, Utils.tacc("&6Mossy Dirt &7✪✪✪✪✪"), false);
                }
        }
        return null;
    }
}
