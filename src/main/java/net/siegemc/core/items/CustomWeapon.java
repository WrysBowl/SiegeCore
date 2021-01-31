package net.siegemc.core.items;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class CustomWeapon extends CustomItem {
    @Getter @Setter private double damage = 0;
    
    public CustomWeapon(String itemName, ItemStack rawItem) {
        super(itemName, rawItem);
    }
    
    public CustomWeapon(Map<String, Object> data) {
        super(data);
        setDamage((Double) data.get("weapon.damage"));
    }
    
    @Override
    public HashMap<String,Object> serialize() {
        HashMap<String, Object> r = super.serialize();
        r.put("weapon.damage", getDamage());
        return r;
    }
}
