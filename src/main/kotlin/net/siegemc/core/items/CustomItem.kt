package net.siegemc.core.items

import net.siegemc.core.items.Rarity
import net.siegemc.core.items.types.ItemTypes
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

interface CustomItem {
    var item: ItemStack

    val name: String
    val levelRequirement: Int
    val description: List<String>
    val quality: Int
    val rarity: Rarity
    val type: ItemTypes
    val material: Material

    fun updateMeta()

}