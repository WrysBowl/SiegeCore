package net.siegemc.core.listeners;

import net.siegemc.core.Core;
import net.siegemc.core.items.CustomShapedRecipe;
import net.siegemc.core.items.CustomShapelessRecipe;
import net.siegemc.core.items.Recipes;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class InventoryClickEvents implements Listener {

    // Custom crafting recipe check
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        if (e.getInventory().getType() == InventoryType.WORKBENCH) {
            if (e.getSlotType() != InventoryType.SlotType.CRAFTING) {return;}
            if (e.getCursor().getType().isAir()) {return;}

            Bukkit.getServer().getScheduler().runTaskLater(Core.plugin(), () -> {
                CraftingInventory inv = (CraftingInventory) e.getInventory();
                List<ItemStack> matrix = new ArrayList();
                ItemStack result = null;
                Player player = (Player) e.getWhoClicked();

                for (ItemStack i : inv.getMatrix()) {
                    matrix.add(i);
                }
                Bukkit.getLogger().info(matrix.toString());
                for (CustomShapedRecipe recipe : Core.plugin().getShapedRecipes()) {
                    if (recipe.doesFit(matrix)) {
                        result = recipe.getResult();
                    }
                }
                for (CustomShapelessRecipe recipe : Core.plugin().getShapelessRecipes()) {
                    if (recipe.doesFit(matrix)) {
                        result = recipe.getResult();
                    }
                }

                //Known bug: After grabbing the result slot for 8 Tier 1 dirt clumps to 1 tier 2 dirt clump, the crafting table updates
                //and is replaced with 14 tier 1 dirt clumps. Lowering the recipe requirement down to 4 dirt clumps/tier2, replaces the
                //crafting table with 6 tier 1 dirt clumps instead of 14 with the original 8 tier 1 dirt clump recipe.
                if(result != null) {
                    inv.setMatrix(inv.getMatrix());
                    inv.setResult(result);
                    player.updateInventory();
                }
            }, 1);
        }
    }
}
