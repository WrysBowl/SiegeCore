package net.siegemc.core.items;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

public class CustomWeapon extends CustomItem {
    @Getter @Setter private double damage = 0;
    
    public CustomWeapon(String itemName, ItemStack rawItem) {
        super(itemName, rawItem);
    }
}
