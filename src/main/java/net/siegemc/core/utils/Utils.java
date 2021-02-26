package net.siegemc.core.utils;

import net.siegemc.core.Core;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class Utils {
    static public String tacc(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    static public String strip(String str) {
        return ChatColor.stripColor(str);
    }

    static public NamespacedKey namespacedKey(String str) {
        return new NamespacedKey(Core.plugin(), str);
    }

    public static ItemStack createItem(final Material material, final String name, final boolean glowing, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        // Set the name of the item
        meta.setDisplayName(name);

        // Set glowing state of the item
        if (glowing) {
            meta.addEnchant(Enchantment.MENDING, 1, true); //Enchant with lure and will remove glowing effect
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        // Set the lore of the item
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }



}
