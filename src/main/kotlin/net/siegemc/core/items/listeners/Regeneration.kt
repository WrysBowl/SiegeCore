package net.siegemc.core.items.listeners

import net.siegemc.core.Core
import net.siegemc.core.v2.CustomItemUtils
import net.siegemc.core.v2.enums.StatTypes
import org.bukkit.Bukkit

class Regeneration : Runnable {

    fun startRegenTask() {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Core.plugin(), {

            for (player in Bukkit.getOnlinePlayers()) {
                val regenStat = CustomItemUtils.getPlayerStat(player, StatTypes.REGENERATION)
                val healthStat = CustomItemUtils.getPlayerStat(player, StatTypes.HEALTH)
                val currentCustomHealth = CustomItemUtils.getHealth(player)
                player.health += ((regenStat + currentCustomHealth)/healthStat) * player.maxHealth
                if (player.health > player.maxHealth) {
                    player.health = player.maxHealth
                }
            }
        }, 100, 100);
    }

    override fun run() {
        TODO("Not yet implemented")
    }
}
