package net.siegemc.core.items.types.equipment.armor

import net.siegemc.core.items.types.ItemTypes
import net.siegemc.core.items.types.equipment.armor.CustomArmor

abstract class CustomChestplate : CustomArmor() {
    override val type: ItemTypes = ItemTypes.CHESTPLATE
    override val description: List<String> = listOf("A powerful chestplate")

}