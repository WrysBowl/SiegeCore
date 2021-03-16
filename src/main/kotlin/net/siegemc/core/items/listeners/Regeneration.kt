package net.siegemc.core.items.listeners

import net.siegemc.core.Core
import net.siegemc.core.items.CustomItemUtils
import net.siegemc.core.items.StatTypes
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitScheduler

class Regeneration : Runnable {

    fun startRegenTask() {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Core.plugin(), {

            for (player in Bukkit.getOnlinePlayers()) {
                val regenStat = CustomItemUtils.getPlayerStat(player, StatTypes.REGENERATION)
                val healthStat = CustomItemUtils.getPlayerStat(player, StatTypes.HEALTH)
                val currentCustomHealth = CustomItemUtils.getHealth(player)
                Bukkit.broadcastMessage("REGEN: " + regenStat.toString())
                Bukkit.broadcastMessage("HEALTH: " + healthStat.toString())
                Bukkit.broadcastMessage("currentCustomHealth: " + currentCustomHealth.toString())
                player.health += ((regenStat + currentCustomHealth)/healthStat) * player.maxHealth
                if (player.health > healthStat) {
                    player.health = healthStat
                }
            }
        }, 100, 100);
    }

    override fun run() {
        TODO("Not yet implemented")
    }
}
