package net.siegemc.core.items.CustomItems;

import net.siegemc.core.Core;
import net.siegemc.core.items.CreateItems.ArmorItem;
import net.siegemc.core.items.CreateItems.Rarity;
import net.siegemc.core.items.CreateItems.Stat;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class TestHelmet extends ArmorItem {
    public TestHelmet() {
        super("Test Helmet", new ItemStack(Material.LEATHER_HELMET, 1));
        this.setDescription(Arrays.asList("A quick item to test if armor", "works correctly"));
        this.setLevelRequirement(5);
        this.setRarity(Rarity.DEBUG);
        this.getStats().put(Stat.HEALTH, 30);
        Core.getItems().put("Test Helmet", this);
    }
}
