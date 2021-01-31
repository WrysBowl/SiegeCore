package net.siegemc.core.items;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

public class FoodItem extends CustomItem {
    @Getter @Setter private int feed = 0;
    @Getter @Setter private double heal = 0;
    
    /**
     * @param itemName The name of the item
     * @param rawItem  The item the custom item will be
     */
    public FoodItem(String itemName, ItemStack rawItem) {
        super(itemName, rawItem);
    }
}
