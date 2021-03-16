package net.siegemc.core.items.types.equipment.armor

import net.siegemc.core.items.CustomItemUtils
import net.siegemc.core.items.StatTypes
import net.siegemc.core.items.types.ItemTypes
import net.siegemc.core.items.types.equipment.CustomEquipment
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent

abstract class CustomArmor : CustomEquipment() {
    override val type: ItemTypes = ItemTypes.ARMOR

    open fun onHit(e: EntityDamageByEntityEvent) {
        var toughness = CustomItemUtils.getPlayerStat(e.entity as Player, StatTypes.TOUGHNESS)
        e.damage = e.damage * (1 - (toughness/1000))
    }

}