package net.siegemc.core.listeners

import net.siegemc.core.Core
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerPortalEvent

class PortalTeleport : Listener {
    @EventHandler
    fun portalEnter(e: PlayerPortalEvent) {
        val p = e.player
        if (Core.plugin().portalConfig.teleportToCorresponding(p))
            e.isCancelled = true
    }
}
