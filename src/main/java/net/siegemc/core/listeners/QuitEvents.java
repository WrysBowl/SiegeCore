package net.siegemc.core.listeners;

import net.siegemc.core.informants.Scoreboard;
import net.siegemc.core.informants.Tablist;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitEvents implements Listener {
    @EventHandler
    public void quitEvent(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        new Tablist().tablistUpdate();

        Scoreboard update = new Scoreboard();
        update.updateScoreboardRegular(player);
        update.updateScoreboardNoParty(player);
    }
}
