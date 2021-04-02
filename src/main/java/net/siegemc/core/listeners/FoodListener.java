package net.siegemc.core.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;

public class FoodListener implements Listener {
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
    public void hungerChange(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }
}
