package net.siegemc.core.items.types.equipment.armor

import net.siegemc.core.items.types.ItemTypes

abstract class CustomHelmet : CustomArmor() {
    override val type: ItemTypes = ItemTypes.HELMET
    override val description: List<String> = listOf("A powerful helmet")
}