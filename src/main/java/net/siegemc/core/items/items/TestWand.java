package net.siegemc.core.items.items;

import net.siegemc.core.Core;
import net.siegemc.core.items.Rarity;
import net.siegemc.core.items.Stat;
import net.siegemc.core.items.WandItem;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class TestWand extends WandItem {
    public TestWand() {
        super("Test Wand", new ItemStack(Material.STICK));
        this.setManaRequired(10);
        this.setParticle(Particle.COMPOSTER);
        this.setDamage(10000);
        this.setDamageRadius(5.5);
        this.setRange(120);
        this.setRarity(Rarity.DEBUG);
        this.getStats().put(Stat.STRENGTH, 100);
        this.setDescription(Arrays.asList("This is just a test wand used", "for testing wands"));
        Core.getItems().put("Test Wand", this);
    }
}
