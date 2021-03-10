package net.siegemc.core.items.types

abstract class CustomChestplate : CustomArmor() {
    override val type: ItemTypes = ItemTypes.CHESTPLATE
    override val description: List<String> = listOf("A powerful chestplate")

}