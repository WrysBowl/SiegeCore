package net.siegemc.core.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

public class Food implements Listener {
    @EventHandler
    public void playerItemHeldEvent(PlayerItemHeldEvent e) { //Sets food level to 19 if item is edible, else set to 20
        Player player = e.getPlayer();
        player.setFoodLevel(20);

        if (player.getInventory().getItem(e.getNewSlot()) == null) { return; }

        if (player.getInventory().getItem(e.getNewSlot()).getType().isEdible()) {
            player.setFoodLevel(19);
        }
    }

    @EventHandler
    public void consumeEvent(PlayerItemConsumeEvent e) {
        e.setCancelled(true);
        ItemStack tool = e.getPlayer().getInventory().getItemInMainHand();
        tool.setAmount(tool.getAmount()-1);
        if (tool.getAmount() == 0) {
            e.getPlayer().setFoodLevel(20);
        }
    }

    @EventHandler
    public void hungerChange(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }
}
