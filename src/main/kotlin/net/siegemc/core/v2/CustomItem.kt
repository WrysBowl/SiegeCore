package net.siegemc.core.v2

import net.siegemc.core.v2.enums.ItemTypes
import net.siegemc.core.v2.enums.Rarity
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

    fun updateMeta(hideRarity: Boolean): ItemMeta

    fun serialize() {
        item = item.setNbtTags(
            "name" to name,
            "customModelData" to customModelData,
            "levelRequirement" to levelRequirement,
            "type" to type.toString(),
            "quality" to quality,
            "rarity" to rarity.toString()
        )
    }

    fun deserialize() {
        // TODO()
    }
}