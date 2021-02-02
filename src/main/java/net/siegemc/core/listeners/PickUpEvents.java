package net.siegemc.core.listeners;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class PickUpEvents implements Listener{
    public static Economy econ = null;

    @EventHandler
    public void entityPickUp(EntityPickupItemEvent e) {
        ItemStack eGetItem = e.getItem().getItemStack();
        if ((e.getEntity() instanceof Player) && (eGetItem.getItemMeta().getDisplayName().equals("&eGold Coin")) && (eGetItem.getType().equals(Material.SUNFLOWER))) {
            int goldAmount = e.getItem().getItemStack().getAmount();
            EconomyResponse r = econ.depositPlayer((OfflinePlayer) e.getEntity(), goldAmount);
        }
    }
}
