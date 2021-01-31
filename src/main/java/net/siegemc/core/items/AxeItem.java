package net.siegemc.core.items;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

public class AxeItem extends CustomWeapon {
    @Getter @Setter private int cooldown = 0;
    
    public AxeItem(String itemName, ItemStack rawItem) {
        super(itemName, rawItem);
    }
}
