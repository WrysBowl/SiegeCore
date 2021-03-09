package net.siegemc.core.items

import net.siegemc.core.items.CreateItems.Rarity
import net.siegemc.core.items.types.ItemTypes
import org.bukkit.inventory.ItemStack

interface CustomItem {
    var item: ItemStack

    val name: String
    val levelRequirement: Int
    val rarity: Rarity
    val type: ItemTypes

}