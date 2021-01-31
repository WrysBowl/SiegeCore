package net.siegemc.core.items;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Particle;
import org.bukkit.inventory.ItemStack;

public class WandItem extends CustomWeapon {
    @Getter @Setter private int range = 12;
    @Getter @Setter private Particle particle = null;
    @Getter @Setter private int manaRequired = 0;
    @Getter @Setter private double damageRadius = 2.5;
    
    public WandItem(String itemName, ItemStack rawItem) {
        super(itemName, rawItem);
    }
}
