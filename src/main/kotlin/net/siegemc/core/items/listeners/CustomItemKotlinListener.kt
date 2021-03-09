package net.siegemc.core.items.listeners

import net.siegemc.core.items.types.CustomWeapon
import net.siegemc.core.items.CustomItem
import net.siegemc.core.items.CustomItemUtils
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent

class CustomItemKotlinListener : Listener {

    @EventHandler
    fun onHit(e: EntityDamageByEntityEvent) {
        if (e.damager is Player) {
            val item = (e.damager as Player).inventory.itemInMainHand
            val customItem: CustomItem? = CustomItemUtils.getCustomItem(item)

            customItem?.let {
                if (it is CustomWeapon) {
                    it.onHit(e)
                }
            }
        }
    }
}