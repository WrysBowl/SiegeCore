package net.siegemc.core.items.CustomItems;

import net.siegemc.core.Core;
import net.siegemc.core.items.CreateItems.BowItem;
import net.siegemc.core.items.CreateItems.Rarity;
import net.siegemc.core.items.CreateItems.Stat;

import java.util.Arrays;

public class TestBow extends BowItem {
    public TestBow() {
        super("Debug Bow");
        this.setDescription(Arrays.asList("The bow used to test if damage", "can work with BowItem."));
        this.setRarity(Rarity.DEBUG);
        this.setLevelRequirement(5);
        this.setDamage(95);
        this.getStats().put(Stat.STRENGTH, 100);
        Core.getItems().put("Debug Bow", this);
    }
}
