package net.siegemc.core.items.Recipes.Weapons;

import net.siegemc.core.items.Recipes.CustomShapedRecipe;
import net.siegemc.core.items.Recipes.Recipes;
import net.siegemc.core.items.implemented.equipment.weapons.melee.Twig;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Swords extends Recipes {

    List<ItemStack> craftingGrid = new ArrayList();
    ItemStack result = null;

    public Swords() {
        Twig();
    }

    private void Twig() {
        //craftingGrid.set(0, ); //Items.searchItemLibrary("DIRT_CLUMP", 1, 8)
        result = new Twig().getItem();
        shapedRecipes.add(new CustomShapedRecipe(craftingGrid,result));
    }
}
