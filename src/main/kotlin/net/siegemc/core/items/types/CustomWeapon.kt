package net.siegemc.core.items.types

import net.kyori.adventure.text.minimessage.MiniMessage
import net.siegemc.core.utils.Utils
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.inventory.ItemFlag

abstract class CustomWeapon : CustomEquipment() {
    override val type: ItemTypes = ItemTypes.WEAPON
    override val description: List<String> = listOf("A powerful weapon")

    abstract val damage: Double

    abstract fun onHit(e: EntityDamageByEntityEvent)


}