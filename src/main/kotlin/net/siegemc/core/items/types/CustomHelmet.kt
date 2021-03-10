package net.siegemc.core.items.types

abstract class CustomHelmet : CustomArmor() {
    override val type: ItemTypes = ItemTypes.HELMET
    override val description: List<String> = listOf("A powerful helmet")
}