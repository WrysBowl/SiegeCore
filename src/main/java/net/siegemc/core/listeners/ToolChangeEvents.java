package net.siegemc.core.listeners;

import net.siegemc.core.Core;
import net.siegemc.core.informants.Scoreboard;
import net.siegemc.core.informants.Tablist;
import net.siegemc.core.utils.Items;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;

public class ToolChangeEvents implements Listener {

    @EventHandler
    public void onToolChange(PlayerItemHeldEvent e) {
        Player player = e.getPlayer();
        Material toolType = player.getInventory().getItemInMainHand().getType();
        Bukkit.getServer().getScheduler().runTaskLater(Core.plugin(), () -> {
            if (player.getGameMode() == GameMode.ADVENTURE) {
                if (Items.checkTool(toolType) != null) { //Checks if tool of player is contained in the enum
                    player.setGameMode(GameMode.SURVIVAL);
                }
            }
            if (player.getGameMode() == GameMode.SURVIVAL) {
                if (Items.checkTool(toolType) == null) { //Checks if tool of player is not contained in the enum
                    player.setGameMode(GameMode.ADVENTURE);
                }
            }
        }, 1L);
    }

}