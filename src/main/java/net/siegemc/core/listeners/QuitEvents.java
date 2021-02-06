package net.siegemc.core.listeners;

import net.siegemc.core.informants.Scoreboard;
import net.siegemc.core.informants.Tablist;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitEvents implements Listener {

    @EventHandler
    public void quitEvent(PlayerQuitEvent e) {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        new Tablist().tablistUpdate();

        Scoreboard s = new Scoreboard();
        for (Player p : Bukkit.getOnlinePlayers()) {
            s.updateScoreboard(p);
        }
    }
}
