package net.siegemc.core.v2

import net.siegemc.core.items.recipes.CustomRecipe
import net.siegemc.core.v2.enums.ItemTypes
import net.siegemc.core.v2.enums.NbtTypes
import net.siegemc.core.v2.enums.Rarity
import net.siegemc.core.v2.recipes.CustomRecipeList
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
    val recipeList: CustomRecipeList
    var quality: Int
    var rarity: Rarity
    var item: ItemStack

    fun updateMeta(hideRarity: Boolean): ItemMeta

    fun serialize() {
        item = item.setNbtTags(
            "itemName" to name,
            "CustomModelData" to customModelData,
            "itemLevelRequirement" to levelRequirement,
            "itemType" to type.toString(),
            "itemQuality" to quality,
            "itemRarity" to rarity.toString()
        )
    }

    fun deserialize() {
        val nbtTags = item.getNbtTags("itemQuality" to NbtTypes.INT)
        nbtTags["itemQuality"]?.let {
            quality = it as Int
            rarity = Rarity.getFromInt(quality)
        }

    }

    fun registerRecipes() {
        for (customRecipe in recipeList.recipeList) {
            CustomRecipe.registerRecipe(customRecipe, )
        }
    }
}