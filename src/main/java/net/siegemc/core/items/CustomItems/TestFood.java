package net.siegemc.core.items.CustomItems;

import net.siegemc.core.Core;
import net.siegemc.core.items.CreateItems.FoodItem;
import net.siegemc.core.items.CreateItems.Rarity;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class TestFood extends FoodItem {
    public TestFood() {
        super("Test Food", new ItemStack(Material.COOKED_BEEF, 1));
        this.setDescription(Arrays.asList("Used to test if custom food would", "actually work. Yeah, that's actually", "about it tbh."));
        this.setFeed(4);
        this.setHeal(3);
        this.setLevelRequirement(0);
        this.setRarity(Rarity.DEBUG);
        Core.getItems().put("Test Food", this);
    }
}
