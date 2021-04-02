package net.siegemc.core.items.types.armor

import net.siegemc.core.items.StatGem
import net.siegemc.core.items.enums.ItemTypes
import net.siegemc.core.items.enums.Rarity
import net.siegemc.core.items.enums.StatTypes
import net.siegemc.core.items.recipes.CustomRecipeList
import net.siegemc.core.items.types.subtypes.CustomArmor
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

abstract class CustomChestplate(
    override val name: String,
    override val customModelData: Int? = null,
    override val levelRequirement: Int? = null,
    override val description: List<String>,
    override val material: Material,
    override var quality: Int = -1,
    override var item: ItemStack = ItemStack(material),
    override val baseStats: HashMap<StatTypes, Double>,
    override val type: ItemTypes = ItemTypes.CHESTPLATE,
    override val recipeList: CustomRecipeList? = null,
    override var statGem: StatGem? = null
) : CustomArmor {

    override var rarity: Rarity = Rarity.COMMON

    init {
        rarity = Rarity.getFromInt(quality)
    }


}