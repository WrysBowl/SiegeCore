package net.siegemc.core.items.ItemEnums;

import org.bukkit.Material;
import org.bukkit.block.Block;

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
}
