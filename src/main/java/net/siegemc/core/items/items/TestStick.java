package net.siegemc.core.items.items;

import net.siegemc.core.Core;
import net.siegemc.core.items.Rarity;
import net.siegemc.core.items.Stat;
import net.siegemc.core.items.SwordItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class TestStick extends SwordItem {
    public TestStick() {
        super("Test Stick", new ItemStack(Material.STICK, 1));
        this.setRarity(Rarity.DEBUG);
        this.setDescription(Arrays.asList("This is just a item to see if", "everything works correctly so far!"));
        this.setLevelRequirement(5);
        this.setDamage(200);
        this.getStats().put(Stat.STRENGTH, 100);
        this.getStats().put(Stat.LUCK, 12);
        this.getStats().put(Stat.TOUGHNESS, 2);
        this.getStats().put(Stat.WISDOM, 5);
        this.getStats().put(Stat.REGENERATION, 6);
        this.getStats().put(Stat.HEALTH, 5);
        Core.getItems().put("Test Stick", this);
    }
}
