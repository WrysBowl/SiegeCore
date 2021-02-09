package net.siegemc.core.listeners;

import net.siegemc.core.utils.Levels;
import net.siegemc.core.utils.Utils;
import net.siegemc.core.utils.VaultHook;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatEvents implements Listener {

    @EventHandler
    public void playerChat(AsyncPlayerChatEvent e) {
        String player = e.getPlayer().getDisplayName();
        String level = "&8[&d"+ Levels.getLevel(e.getPlayer())+"&8]";
        String prefix = VaultHook.perms.getPrimaryGroup(e.getPlayer());
        String message = e.getMessage().replaceAll("&k", "");
        String check = Utils.strip(message);
        if (check.equalsIgnoreCase("") || check.equalsIgnoreCase(" ")) {
            e.getPlayer().sendMessage(Utils.tacc("You can not send a empty message!"));
            e.setCancelled(true);
            return;
        }
        e.setFormat(Utils.tacc(String.format("%s %s &7%s »&f %s", level, prefix, player, message)));
    }
}
