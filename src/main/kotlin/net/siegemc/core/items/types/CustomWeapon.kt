package net.siegemc.core.items.types

import org.bukkit.event.entity.EntityDamageByEntityEvent

abstract class CustomWeapon : CustomEquipment() {
    override val type: ItemTypes = ItemTypes.WEAPON

    abstract val damage: Double

    abstract fun onHit(e: EntityDamageByEntityEvent)

}