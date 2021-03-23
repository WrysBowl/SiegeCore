package net.siegemc.core.v2.interfaces

import net.siegemc.core.items.Rarity
import net.siegemc.core.items.types.ItemTypes
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

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

    fun updateMeta(fakeRarity: Boolean): ItemMeta
}