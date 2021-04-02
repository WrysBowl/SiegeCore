package net.siegemc.core.items.types.weapons

import net.siegemc.core.items.StatGem
import net.siegemc.core.items.enums.ItemTypes
import net.siegemc.core.items.enums.Rarity
import net.siegemc.core.items.enums.StatTypes
import net.siegemc.core.items.recipes.CustomRecipeList
import net.siegemc.core.items.types.subtypes.CustomWeapon
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

abstract class CustomBow(
    override val name: String,
    override val customModelData: Int? = null,
    override val levelRequirement: Int? = null,
    override val description: List<String>,
    override val material: Material,
    override var quality: Int = -1,
    override var item: ItemStack = ItemStack(material),
    override val baseStats: HashMap<StatTypes, Double>,
    override val type: ItemTypes = ItemTypes.BOW,
    override val recipeList: CustomRecipeList? = null,
    override var statGem: StatGem? = null
) : CustomWeapon {

    override var rarity: Rarity = Rarity.COMMON

    init {
        rarity = Rarity.getFromInt(quality)
    }


}