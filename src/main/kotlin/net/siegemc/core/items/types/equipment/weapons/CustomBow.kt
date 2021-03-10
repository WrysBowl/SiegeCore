package net.siegemc.core.items.types.equipment.weapons

import net.siegemc.core.items.types.ItemTypes
import org.bukkit.event.entity.EntityDamageByEntityEvent

abstract class CustomBow : CustomWeapon() {
    override val type: ItemTypes = ItemTypes.WEAPON
    override val description: List<String> = listOf("A powerful bow")

    open fun onHit(e: EntityDamageByEntityEvent) {
        // does nothing lol
    }


}