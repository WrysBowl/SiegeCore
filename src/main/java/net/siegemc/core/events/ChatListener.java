package net.siegemc.core.events;

import net.siegemc.core.Profile;
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
        Bukkit.broadcastMessage(Utils.tacc((String.format("&8[&6%s&8] &7%s &r%s &f%s &6\u00BB &7", Profile.getLevel(event.getPlayer(), null), Profile.getGuild(event.getPlayer(), null), group, event.getPlayer().getDisplayName(), event.getMessage()))) + (isOp ? Utils.tacc(event.getMessage()) : event.getMessage()));
    }
}
