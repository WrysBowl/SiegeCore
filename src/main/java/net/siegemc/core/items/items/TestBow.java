package net.siegemc.core.items.items;

import net.siegemc.core.Core;
import net.siegemc.core.items.BowItem;
import net.siegemc.core.items.Rarity;
import net.siegemc.core.items.Stat;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class TestBow extends BowItem {
    public TestBow() {
        super("Debug Bow", new ItemStack(Material.BOW, 1));
        this.setDescription(Arrays.asList("The bow used to test if damage", "can work with BowItem."));
        this.setRarity(Rarity.DEBUG);
        this.setLevelRequirement(5);
        this.setDamage(95);
        this.getStats().put(Stat.STRENGTH, 100);
        Core.getItems().put("Debug Bow", this);
    }
}
