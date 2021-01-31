package net.siegemc.core.items;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class WandItem extends CustomWeapon {
    @Getter @Setter private int range = 12;
    @Getter private int red = 255;
    @Getter private int green = 255;
    @Getter private int blue = 255;
    @Getter @Setter private int manaRequired = 0;
    @Getter @Setter private double damageRadius = 2.5;
    @Getter private final String type = "wands";
    
    public WandItem(String itemName, ItemStack rawItem) {
        super(itemName, rawItem);
    }
    
    public void setColor(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }
    
    @Override
    public HashMap<String,Object> serialize() {
        HashMap<String, Object> r = super.serialize();
        r.put("wand.range", getRange());
        r.put("wand.manaRequired", getManaRequired());
        r.put("wand.damageRadius", getDamageRadius());
        r.put("wand.color.red", getRed());
        r.put("wand.color.green", getGreen());
        r.put("wand.color.blue", getBlue());
        return r;
    }
    
    public WandItem(Map<String, Object> data) {
        super(data);
        setRange((Integer) data.get("wand.range"));
        setManaRequired((Integer) data.get("wand.manaRequired"));
        setDamageRadius((Double) data.get("wand.damageRadius"));
        setColor((int) data.get("wand.color.red"), (int) data.get("wand.color.green"), (int) data.get("wand.color.blue"));
    }
}
