package net.siegemc.core.items;

import net.siegemc.core.items.ItemLibrary.Items;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Recipes {

    List<CustomShapelessRecipe> shapelessRecipes = new ArrayList();
    List<CustomShapedRecipe> shapedRecipes = new ArrayList();

    public List<CustomShapelessRecipe> getShapelessRecipes() {
        return shapelessRecipes;
    }
    public List<CustomShapedRecipe> getShapedRecipes() {
        return shapedRecipes;
    }

    public Recipes() {

        for (int i = 0; i < Items.Reagents.length; i++) {
            loadRecipes(Items.Reagents[i]);
        }
    }

    // New function to automate both Upgrade and Dismantle loading for Reagents
    // Eliminated tier parameter
    public void loadRecipes(String reagent) {
        List<ItemStack> craftingGrid = new ArrayList();
        ItemStack result = null;

        // Load all upgrade recipes for the reagent
        // i in addition to being used as the loop iterator is also used as the tier
        for (int i = 1; i < 5; i++) {
            craftingGrid.add(Items.searchItemLibrary(reagent, i, 8));
            result = Items.searchItemLibrary(reagent, (i + 1), 1);
            shapelessRecipes.add(new CustomShapelessRecipe(craftingGrid, result));
        }

        // Load all dismantle recipes for the reagent
        for (int i = 2; i < 6; i++) {
            craftingGrid.add(Items.searchItemLibrary(reagent, i, 1));
            result = Items.searchItemLibrary(reagent, i - 1, 4);
            shapelessRecipes.add(new CustomShapelessRecipe(craftingGrid, result));
        }
    }

    // Old code

    /*
    public void loadDirtClumpRecipe(String recipeType, Integer tier) {
        /*
        List<ItemStack> matrix = new ArrayList<>();
        ItemStack item = new ItemStack(Material.STONE,2);
        ItemStack res = new ItemStack(Material.OAK_LOG,4); //resulting product is 4 oak logs
        for(int i = 0; i <8; i++) matrix.add(item); //2 stone pieces in each crafting slot
        plugin.addRecipe(new CustomShapedRecipe(matrix,res));
        */
        /*
        List<ItemStack> craftingGrid = new ArrayList();
        ItemStack result = null;

        if (recipeType.equals("UPGRADE")) {
            switch (tier) {
                case 1:
                    craftingGrid.add(Items.searchItemLibrary("DIRT_CLUMP", 1, 8));
                    result = Items.searchItemLibrary("DIRT_CLUMP", 2, 1);
                case 2:
                    craftingGrid.add(Items.searchItemLibrary("DIRT_CLUMP", 2, 8));
                    result = Items.searchItemLibrary("DIRT_CLUMP", 3, 1);
            }
        } else if (recipeType.equals("DISMANTLE")) {
            switch (tier) {
                case 2:
                    craftingGrid.add(Items.searchItemLibrary("DIRT_CLUMP", 2, 1));
                    result = Items.searchItemLibrary("DIRT_CLUMP", 1, 4);
            }
        }
        shapelessRecipes.add(new CustomShapelessRecipe(craftingGrid, result));

    }*/


}
