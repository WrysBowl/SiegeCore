package net.siegemc.core.items.listeners

import net.siegemc.core.Core
import net.siegemc.core.items.CustomItem
import net.siegemc.core.items.CustomItemUtils
import net.siegemc.core.items.StatTypes
import net.siegemc.core.items.types.CustomFood
import net.siegemc.core.items.types.equipment.CustomWand
import net.siegemc.core.items.types.equipment.armor.CustomArmor
import net.siegemc.core.items.types.equipment.weapons.CustomMeleeWeapon
import net.siegemc.core.listeners.DamageIndicators
import net.siegemc.core.utils.NBT
import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityRegainHealthEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerItemConsumeEvent
import org.bukkit.scheduler.BukkitRunnable
import kotlin.math.roundToInt

class CustomItemKotlinListener : Listener {

    //    Yes, I know this class is very ugly
    //    Feel free to change it if you want in the future :shrug:
    var cooldown: MutableList<Player> = mutableListOf()

    @EventHandler
    fun onHit(e: EntityDamageByEntityEvent) {
        if (e.damager is Player) {
            val item = (e.damager as Player).inventory.itemInMainHand
            val customItem: CustomItem? = CustomItemUtils.getCustomItem(item)

            customItem?.let {
                if (it is CustomMeleeWeapon) {
                    it.onHit(e)
                }
            }
        } else if (e.entity is Player) {
            val armor = (e.entity as Player).inventory.armorContents
            armor.forEach { item ->
                val customItem: CustomItem? = CustomItemUtils.getCustomItem(item)

                customItem?.let {
                    if (it is CustomArmor) {
                        it.onHit(e)
                    }
                }
            }
        }
    }

    @EventHandler
    fun onConsume(e: PlayerItemConsumeEvent) {
        CustomItemUtils.getCustomItem(e.item)?.let {
            if (it is CustomFood) it.onEat(e)
        }
    }

    @EventHandler
    fun onRegen(event: EntityRegainHealthEvent) {
        if (event.entity !is Player) return
        if (event.regainReason == EntityRegainHealthEvent.RegainReason.EATING) event.isCancelled = true
        val player = event.entity as Player
        var regen: Double = CustomItemUtils.getPlayerStat(player, StatTypes.REGENERATION)

        regen = when {
            event.regainReason == EntityRegainHealthEvent.RegainReason.SATIATED -> regen * 0.14
            player.foodLevel == 10 -> regen * 0.12
            else -> regen * 0.1
        }
        event.amount = regen
    }

    @EventHandler
    fun onInteract(event: PlayerInteractEvent) {
        if (event.action != Action.RIGHT_CLICK_AIR &&
            event.action != Action.RIGHT_CLICK_BLOCK
        ) return
        val player = event.player
        val item = player.inventory.itemInMainHand

        CustomItemUtils.getCustomItem(item)?.let {
            if (it is CustomWand) {
                val entity = player.getTargetEntity(it.range)
                val loc = if (entity == null || entity.isDead) {
                    val block = player.getTargetBlock(it.range) ?: return
                    block.location
                } else {
                    entity.location.add(0.0, entity.height, 0.0)
                }
                if (cooldown.contains(player)) return
                cooldown.add(player)
                drawParticles(player.location.add(0.0, player.eyeHeight, 0.0), loc, it.red, it.green, it.blue)
                for (e in loc.getNearbyLivingEntities(it.damageRadius)) {
                    if (e is Player || e is ArmorStand) continue
                    DamageIndicators.showIndicator(
                        e,
                        it.baseStats.get(StatTypes.STRENGTH)!!, false
                    )
                    e.damage(it.baseStats.get(StatTypes.STRENGTH)!!)
                    NBT.addString(e, "attacker", NBT.serializePlayer(player))
                }
                object : BukkitRunnable() {
                    override fun run() {
                        cooldown.remove(player)
                    }
                }.runTaskLaterAsynchronously(Core.plugin(), 2)
            }
        }
    }

    private fun drawParticles(aL: Location, bL: Location, r: Int, g: Int, b: Int) {
        Thread(Runnable {
            var i = 0
            if (aL.world == null || bL.world == null || aL.world != bL.world) return@Runnable
            val distance = aL.distance(bL)
            val p1 = aL.toVector()
            val p2 = bL.toVector()
            val vector = p2.clone().subtract(p1).normalize().multiply(0.2)
            var length = 0.0
            while (length < distance) {
                i++
                val loc = p1.toLocation(aL.world)
                aL.world.spawnParticle(
                    Particle.REDSTONE,
                    loc,
                    0,
                    0.0,
                    0.0,
                    0.0,
                    1.0,
                    Particle.DustOptions(Color.fromRGB(r, g, b), 1.0F)
                )
                length += 0.2
                try {
                    if (i % 10 == 0) Thread.sleep(50)
                } catch (ignored: InterruptedException) {
                }
                p1.add(vector)
            }
        }).start()
    }
}