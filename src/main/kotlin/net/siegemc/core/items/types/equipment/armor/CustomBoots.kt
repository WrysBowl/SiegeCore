package net.siegemc.core.items.types.equipment.armor

import net.siegemc.core.items.StatTypes
import net.siegemc.core.items.recipes.CustomRecipe
import net.siegemc.core.items.types.ItemTypes
import org.bukkit.Material

abstract class CustomBoots(
    final override val name: String,
    final override val customModelData: Int,
    final override val description: List<String>,
    final override val levelRequirement: Int,
    final override val material: Material,
    final override val recipe: CustomRecipe?,
    override val baseStats: HashMap<StatTypes, Double>
) : CustomArmor() {

    init {
        recipe?.let {
            this::class.qualifiedName?.let { it1 -> CustomRecipe.registerRecipe(it, it1) }
        }
    }

    override val type: ItemTypes = ItemTypes.BOOTS

}