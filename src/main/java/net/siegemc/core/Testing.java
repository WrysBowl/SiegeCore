package net.siegemc.core;

import net.siegemc.core.items.implemented.equipment.weapons.melee.TestSword;
import org.junit.Test;

public class Testing {
    @Test
    public void main(String[] args) {

    }

    public static void kotlinItems() {
        // KOTLIN ITEMS SHOWCASE

        TestSword testSword = new TestSword(100);
        // This is how you create a completely new item. In this case, the itemstack is generated completely automatically

        testSword.getItem();
        // You can then get the ItemStack
    }
}

