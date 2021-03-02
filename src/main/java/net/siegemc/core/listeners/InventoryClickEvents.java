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
            //Bukkit.getServer().getScheduler().runTaskLater(Core.plugin(), () -> {
            //}, 5);
            CraftingInventory inv = (CraftingInventory) e.getInventory();
            List<ItemStack> matrix = new ArrayList();
            ItemStack result = null;
            Player player = (Player) e.getWhoClicked();

            for(ItemStack i : inv.getMatrix()) {
                matrix.add(i);
            }
            for(CustomShapedRecipe recipe : Core.plugin().getShapedRecipes()) {
                if(recipe.doesFit(matrix)) {
                    result = recipe.getResult();
                }
            }
            for(CustomShapelessRecipe recipe : Core.plugin().getShapelessRecipes()) {
                if(recipe.doesFit(matrix)) {
                    result = recipe.getResult();
                }
            }

            try {
                Bukkit.getLogger().info(inv.getResult().toString());
                Bukkit.getLogger().info(matrix.toString());
            } catch (Exception ex) { }
            if(result != null) {
                boolean hasRes = false;
                try {hasRes = inv.getResult() != null;} catch(Exception ex) {}
                if((hasRes && !inv.getResult().equals(result)) || !hasRes) {
                    inv.setMatrix(inv.getMatrix());
                    inv.setResult(result);

                    player.updateInventory();
                }
            }
        }
    }
}
