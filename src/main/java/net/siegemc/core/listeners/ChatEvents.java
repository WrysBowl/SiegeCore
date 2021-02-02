package net.siegemc.core.listeners;

import net.siegemc.core.utils.Levels;
import net.siegemc.core.utils.Utils;
import net.siegemc.core.utils.VaultHook;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatEvents implements Listener {

    @EventHandler
    public void playerChat(AsyncPlayerChatEvent e) {
        String message = Utils.tacc(ChatColor.DARK_GRAY + "[" + ChatColor.LIGHT_PURPLE + e.getPlayer().getLevel() + ChatColor.DARK_GRAY + "]" + " " + VaultHook.perms.getPrimaryGroup(e.getPlayer()) + " " + ChatColor.GRAY + e.getPlayer().getName() + ChatColor.GRAY + " » " + ChatColor.WHITE + e.getMessage());
        e.setFormat(message);
    }
}
