package net.siegemc.core.v2.types.subtypes

import org.bukkit.event.entity.EntityDamageByEntityEvent

interface CustomArmor: CustomEquipment {

    fun onHit(e: EntityDamageByEntityEvent) {
        // placeholder for optional event
    }

}