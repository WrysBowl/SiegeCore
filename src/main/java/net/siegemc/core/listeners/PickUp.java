package net.siegemc.core.listeners;

import net.siegemc.core.informants.Scoreboard;
import net.siegemc.core.utils.Utils;
import net.siegemc.core.utils.VaultHook;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class PickUp implements Listener{

    @EventHandler
    public void entityPickUp(EntityPickupItemEvent e) {
        ItemStack eGetItem = e.getItem().getItemStack();
        if (!(e.getEntity() instanceof Player)) return;
        if (!eGetItem.getType().equals(Material.SUNFLOWER)) return;
        if (!Utils.strip(eGetItem.getItemMeta().getDisplayName()).equals("Gold Coin")) return;
        e.setCancelled(true);
        int goldAmount = e.getItem().getItemStack().getAmount();
        VaultHook.econ.depositPlayer((OfflinePlayer) e.getEntity(), goldAmount);
        e.getItem().remove();
        new Scoreboard().updateScoreboard((Player) e.getEntity());
    }
}
