package net.siegemc.core.items.CreateItems;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class BowItem extends CustomWeapon {
    @Getter private final String type = "bows";
    /**
     * @param itemName The name of the bow
     */
    public BowItem(String itemName) {
        super(itemName, new ItemStack(Material.BOW, 1));
    }
    
    public BowItem(Map<String, Object> data) {
        super(data);
        setDamage((Double) data.get("weapon.damage"));
    }
}
