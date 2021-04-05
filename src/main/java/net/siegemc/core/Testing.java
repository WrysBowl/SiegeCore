package net.siegemc.core;

import net.siegemc.core.items.CustomItemUtils;
import net.siegemc.core.items.implemented.equipment.weapons.melee.TestSword;
import net.siegemc.core.utils.Utils;
import net.siegemc.core.items.implemented.misc.materials.DirtClump;
import net.siegemc.core.listeners.BlockBreakListener;
import net.siegemc.core.party.Party;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;

public class Testing {

    public static HashMap<String, HashMap<String, Number[]>> blockDrops = new HashMap<String, HashMap<String, Number[]>>() {{
        put("Material.GRASS_BLOCK", new HashMap(){{
            put("DIRT_CLUMP", new Number[]{ 1, 1, 30 });
            put("SEED", new Number[]{ 1, 1, 10 });
            put("TURF", new Number[]{ 1, 1, 5 });
            put("MOSS", new Number[]{ 1, 1, 1 });
            put("MOSSY_DIRT", new Number[]{ 1, 1, 0.5 });
        }});
    }};

    @Test
    public static void main(String args[]) {
        double num = 1.0;
        if (blockDrops.get("Material.") == null) {
            num = 0.0;
        }
        System.out.println(num);
    }


    public static void kotlinItems() {
        // KOTLIN ITEMS SHOWCASE

        TestSword testSword = new TestSword(100);
        // This is how you create a completely new item. In this case, the itemstack is generated completely automatically

        testSword.getItem();
        // You can then get the ItemStack

        CustomItemUtils.INSTANCE.getCustomItem(testSword.getItem());
        // This function is used to get the custom item of an itemstack. Make sure to wrap this in try/catch or it will
        // throw an NPE. The INSTANCE is because this is pepega java and that's a kotlin object.
    }
}

