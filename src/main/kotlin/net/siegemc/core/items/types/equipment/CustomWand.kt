package net.siegemc.core.items.types.equipment

import net.siegemc.core.items.types.ItemTypes

abstract class CustomWand : CustomEquipment() {
    override val type: ItemTypes = ItemTypes.WAND
    override val description: List<String> = listOf("A powerful wand")

    open val range: Int = 12
    open val red: Int = 255
    open val green: Int = 255
    open val blue: Int = 255
    open val manaRequired: Int = 0
    open val damageRadius: Double = 2.5

}