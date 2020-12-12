package net.siegemc.core.events;

import net.siegemc.core.Utils;
import net.siegemc.core.VaultHook;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onChat(AsyncPlayerChatEvent event) {
        String group = VaultHook.perms.getPrimaryGroup(event.getPlayer().getWorld().getName(), event.getPlayer());
        event.setCancelled(true);
        boolean isOp = event.getPlayer().isOp();
        Bukkit.broadcastMessage(Utils.tacc((String.format("%s &f%s &6\u00BB &7",group, event.getPlayer().getDisplayName(), event.getMessage()))) + (isOp ? Utils.tacc(event.getMessage()) : event.getMessage()));
    }
}
