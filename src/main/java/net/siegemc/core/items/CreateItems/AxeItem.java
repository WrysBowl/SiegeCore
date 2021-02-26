package net.siegemc.core.items.CreateItems;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class AxeItem extends CustomWeapon {
    @Getter @Setter private int cooldown = 0;
    @Getter private final String type = "axes";
    
    public AxeItem(String itemName, ItemStack rawItem) {
        super(itemName, rawItem);
    }
    
    @Override
    public HashMap<String,Object> serialize() {
        HashMap<String, Object> r = super.serialize();
        r.put("weapon.cooldown", getCooldown());
        return r;
    }
    
    public AxeItem(Map<String, Object> data) {
        super(data);
        setCooldown((Integer) data.get("weapon.cooldown"));
    }
}
