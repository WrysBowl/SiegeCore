package net.siegemc.core.utils;

import org.bukkit.Material;

public enum Items {
    WOODEN_PICKAXE, WOODEN_AXE, WOODEN_HOE, WOODEN_SHOVEL,
    STONE_PICKAXE, STONE_AXE, STONE_HOE, STONE_SHOVEL,
    GOLDEN_PICKAXE, GOLDEN_AXE, GOLDEN_HOE, GOLDEN_SHOVEL,
    DIAMOND_PICKAXE, DIAMOND_AXE, DIAMOND_HOE, DIAMOND_SHOVEL,
    NETHERITE_PICKAXE, NETHERITE_AXE, NETHERITE_HOE, NETHERITE_SHOVEL;

    public static Items checkTool(Material toolType) {
        try {
            return Items.valueOf(toolType.toString());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
