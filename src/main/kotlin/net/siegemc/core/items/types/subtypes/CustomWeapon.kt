package net.siegemc.core.items.types.subtypes

import net.siegemc.core.items.CustomItemUtils
import net.siegemc.core.items.enums.StatTypes
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent

interface CustomWeapon: CustomEquipment {

    fun onHit(e: EntityDamageByEntityEvent) {

    }

}