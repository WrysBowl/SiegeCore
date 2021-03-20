package net.siegemc.core.items.types.equipment.weapons

import net.siegemc.core.v2.enums.ItemTypes
import net.siegemc.core.items.types.equipment.CustomEquipment

abstract class CustomWeapon : CustomEquipment() {
    override val type: ItemTypes = ItemTypes.WEAPON

    //abstract val damage: Double



}