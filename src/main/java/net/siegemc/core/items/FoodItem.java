package net.siegemc.core.items;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class FoodItem extends CustomItem {
    @Getter @Setter private int feed = 0;
    @Getter @Setter private double heal = 0;
    @Getter private final String type = "foods";
    
    /**
     * @param itemName The name of the item
     * @param rawItem  The item the custom item will be
     */
    public FoodItem(String itemName, ItemStack rawItem) {
        super(itemName, rawItem);
    }
    
    @Override
    public HashMap<String,Object> serialize() {
        HashMap<String, Object> r = super.serialize();
        r.put("food.feed", getFeed());
        r.put("food.heal", getHeal());
        return r;
    }
    
    public FoodItem(Map<String, Object> data) {
        super(data);
        setFeed((Integer) data.get("food.feed"));
        setHeal((Double) data.get("food.heal"));
    }
}
