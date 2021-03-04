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

        loadDirtClumpRecipe("UPGRADE", 1);
        loadDirtClumpRecipe("DISMANTLE", 2);
    }

    public void loadDirtClumpRecipe(String recipeType, Integer tier) {
        /*
        List<ItemStack> matrix = new ArrayList<>();
        ItemStack item = new ItemStack(Material.STONE,2);
        ItemStack res = new ItemStack(Material.OAK_LOG,4); //resulting product is 4 oak logs
        for(int i = 0; i <8; i++) matrix.add(item); //2 stone pieces in each crafting slot
        plugin.addRecipe(new CustomShapedRecipe(matrix,res));
        */
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
    }
}
