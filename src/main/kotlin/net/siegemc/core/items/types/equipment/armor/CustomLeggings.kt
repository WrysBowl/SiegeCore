package net.siegemc.core.items.types

abstract class CustomLeggings : CustomArmor() {
    override val type: ItemTypes = ItemTypes.LEGGINGS
    override val description: List<String> = listOf("Powerful leggings")
}