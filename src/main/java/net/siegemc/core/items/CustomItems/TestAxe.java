package net.siegemc.core.items.CustomItems;

import net.siegemc.core.Core;
import net.siegemc.core.items.CreateItems.AxeItem;
import net.siegemc.core.items.CreateItems.Rarity;
import net.siegemc.core.items.CreateItems.Stat;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class TestAxe extends AxeItem {
    public TestAxe() {
        super("Test Axe", new ItemStack(Material.WOODEN_AXE));
        this.setDamage(50);
        this.setLevelRequirement(5);
        this.setDescription(Arrays.asList("The debug item for axes, used for", "testing their cooldown and such"));
        this.setCooldown(40);
        this.setRarity(Rarity.DEBUG);
        this.getStats().put(Stat.STRENGTH, 30);
        this.getStats().put(Stat.REGENERATION, 20);
        Core.getItems().put("Test Axe", this);
    }
}
