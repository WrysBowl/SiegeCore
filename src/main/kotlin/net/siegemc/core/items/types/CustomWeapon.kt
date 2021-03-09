package net.siegemc.core.items.types

import net.siegemc.core.items.CustomItem
import org.bukkit.event.entity.EntityDamageByEntityEvent

abstract class CustomWeapon : CustomItem {
    override val type: ItemTypes = ItemTypes.WEAPON

    abstract val damage: Double

    abstract fun onHit(e: EntityDamageByEntityEvent)

}