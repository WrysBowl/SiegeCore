package net.siegemc.core.items.types.equipment.armor

import net.siegemc.core.items.types.ItemTypes

abstract class CustomLeggings : CustomArmor() {
    override val type: ItemTypes = ItemTypes.LEGGINGS
    override val description: List<String> = listOf("Powerful leggings")
}