package net.siegemc.core;

import net.siegemc.core.items.CustomItemUtils;
import net.siegemc.core.items.implemented.equipment.weapons.melee.TestSword;
import net.siegemc.core.utils.Utils;
import org.junit.Test;

public class Testing {

    @Test
    public static void main(String[] args) {
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

