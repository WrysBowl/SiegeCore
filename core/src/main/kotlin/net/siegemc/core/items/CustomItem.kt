package net.siegemc.core.items

import net.siegemc.core.items.enums.ItemTypes
import net.siegemc.core.items.enums.Rarity
import net.siegemc.core.items.recipes.CustomRecipe
import net.siegemc.core.items.recipes.CustomRecipeList
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

interface CustomItem {
    val name: String
    val customModelData: Int?
    val levelRequirement: Int?
    val description: List<String>
    val type: ItemTypes
    val material: Material
    val recipeList: CustomRecipeList?
    var quality: Int
    var rarity: Rarity
    var item: ItemStack

    fun updateMeta(hideRarity: Boolean)

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
        item.getNbtTag<Int>("itemQuality")?.let {
            quality = it
            rarity = Rarity.getFromInt(quality)
        }

    }

    fun registerRecipes() {
        recipeList?.let { list ->
            list.recipeList.forEach {
                CustomRecipe.registerRecipe(it)
            }
        }

    }
}