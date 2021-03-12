package net.siegemc.core.listeners;

import net.siegemc.core.Core;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class InventoryClose implements Listener {
    @EventHandler
    public void onCraftingInventoryClick(InventoryCloseEvent e) { //Removes the invOpened metadata when player closes their inventory so they can click GUIs again
        if (e.getPlayer().hasMetadata("invOpened")) {
            e.getPlayer().removeMetadata("invOpened", Core.plugin());
        }
    }
}
