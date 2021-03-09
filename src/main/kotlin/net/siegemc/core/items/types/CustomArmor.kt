package net.siegemc.core.items.types

import org.bukkit.event.entity.EntityDamageByEntityEvent

abstract class CustomArmor : CustomEquipment() {
    override val type: ItemTypes = ItemTypes.ARMOR

    abstract fun onHit(e: EntityDamageByEntityEvent)
}