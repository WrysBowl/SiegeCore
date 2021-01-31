package net.siegemc.core.items;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class SwordItem extends CustomWeapon {
    @Getter private final String type = "swords";
    public SwordItem(String itemName, ItemStack rawItem) {
        super(itemName, rawItem);
    }
    
    public SwordItem(Map<String, Object> data) {
        super(data);
        setDamage((Double) data.get("weapon.damage"));
    }
}