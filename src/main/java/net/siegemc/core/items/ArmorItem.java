package net.siegemc.core.items;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class ArmorItem extends CustomItem {
    @Getter private final String type = "armor";
    public ArmorItem(String itemName, ItemStack rawItem) {
        super(itemName, rawItem);
    }
    
    public ArmorItem(Map<String, Object> data) {
        super(data);
    }
}
