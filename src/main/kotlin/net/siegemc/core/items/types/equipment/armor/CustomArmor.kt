package net.siegemc.core.items.types.equipment.armor

import net.siegemc.core.items.types.equipment.CustomEquipment
import net.siegemc.core.items.types.ItemTypes
import org.bukkit.event.entity.EntityDamageByEntityEvent

abstract class CustomArmor : CustomEquipment() {
    override val type: ItemTypes = ItemTypes.ARMOR

    open fun onHit(e: EntityDamageByEntityEvent) {
        // does nothing lol
    }

}