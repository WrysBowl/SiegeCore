package net.siegemc.core.listeners;

import net.siegemc.core.Core;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryCloseListener implements Listener {
    @EventHandler
    public void onCraftingInventoryClose(InventoryCloseEvent e) { //Removes the invOpened metadata when player closes their inventory so they can click GUIs again
        if (e.getPlayer().hasMetadata("invOpened")) {
            e.getPlayer().removeMetadata("invOpened", Core.plugin());
        }
    }
}
