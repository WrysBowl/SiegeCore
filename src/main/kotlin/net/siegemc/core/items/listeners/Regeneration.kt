package net.siegemc.core.items.listeners

import net.siegemc.core.Core
import net.siegemc.core.items.CustomItemUtils
import net.siegemc.core.items.StatTypes
import org.bukkit.Bukkit

class Regeneration : Runnable {

    fun startRegenTask() {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Core.plugin(), {

            for (player in Bukkit.getOnlinePlayers()) {
                val regenStat = CustomItemUtils.getPlayerStat(player, StatTypes.REGENERATION)
                val healthStat = CustomItemUtils.getPlayerStat(player, StatTypes.HEALTH)
                val currentCustomHealth = CustomItemUtils.getHealth(player)
                val regen = ((regenStat + currentCustomHealth)/healthStat) * player.maxHealth

                if (!regen.isNaN()) player.health += regen
                else if (player.health < player.maxHealth) player.health += 1
                if (player.health > player.maxHealth) player.health = player.maxHealth
            }
        }, 100, 100);
    }

    override fun run() {
        TODO("Not yet implemented")
    }
}
