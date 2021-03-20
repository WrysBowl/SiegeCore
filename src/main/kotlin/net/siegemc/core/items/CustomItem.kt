package net.siegemc.core.items

import net.siegemc.core.v2.enums.ItemTypes
import net.siegemc.core.v2.enums.Rarity
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

interface CustomItem {
    val name: String
    val customModelData: Int?
    val levelRequirement: Int?
    val description: List<String>
    val type: ItemTypes
    val material: Material
    val quality: Int
    val rarity: Rarity
    var item: ItemStack

    fun updateMeta(fakeRarity: Boolean)
}