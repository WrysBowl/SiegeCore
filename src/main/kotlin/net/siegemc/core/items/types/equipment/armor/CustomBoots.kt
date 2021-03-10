package net.siegemc.core.items.types.equipment.armor

import net.siegemc.core.items.types.ItemTypes

abstract class CustomBoots : CustomArmor() {
    override val type: ItemTypes = ItemTypes.BOOTS
    override val description: List<String> = listOf("Powerful boots (this is the default boots description)")


}