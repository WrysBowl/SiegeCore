package net.siegemc.core.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class BowItem extends CustomWeapon {
    /**
     * @param itemName The name of the bow
     */
    public BowItem(String itemName) {
        super(itemName, new ItemStack(Material.BOW, 1));
    }
}
