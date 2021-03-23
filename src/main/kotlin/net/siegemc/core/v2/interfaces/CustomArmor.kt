package net.siegemc.core.v2.interfaces

import org.bukkit.event.entity.EntityDamageByEntityEvent

interface CustomArmor: CustomEquipment {

    fun onHit(e: EntityDamageByEntityEvent) {
        // placeholder for optional event
    }

}