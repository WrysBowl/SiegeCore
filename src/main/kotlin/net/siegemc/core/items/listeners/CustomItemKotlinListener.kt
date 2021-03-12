package net.siegemc.core.items.listeners

import net.siegemc.core.items.CustomItem
import net.siegemc.core.items.CustomItemUtils
import net.siegemc.core.items.StatTypes
import net.siegemc.core.items.types.CustomFood
import net.siegemc.core.items.types.equipment.armor.CustomArmor
import net.siegemc.core.items.types.equipment.weapons.CustomMeleeWeapon
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityRegainHealthEvent
import org.bukkit.event.player.PlayerItemConsumeEvent

class CustomItemKotlinListener : Listener {

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
}