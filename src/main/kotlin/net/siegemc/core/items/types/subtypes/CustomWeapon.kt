package net.siegemc.core.items.types.subtypes

import org.bukkit.event.entity.EntityDamageByEntityEvent

interface CustomWeapon: CustomEquipment {

    fun onHit(e: EntityDamageByEntityEvent) {
        // placeholder for optional event
    }

}