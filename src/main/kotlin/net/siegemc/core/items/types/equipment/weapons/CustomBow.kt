package net.siegemc.core.items.types.equipment.weapons

import net.siegemc.core.v2.enums.StatTypes
import net.siegemc.core.items.recipes.CustomRecipe
import net.siegemc.core.v2.enums.ItemTypes
import org.bukkit.Material
import org.bukkit.event.entity.EntityDamageByEntityEvent

abstract class CustomBow(
    final override val name: String,
    final override val customModelData: Int,
    final override val description: List<String>,
    final override val levelRequirement: Int,
    final override val material: Material,
    final override val recipe: CustomRecipe?,
    override val baseStats: HashMap<StatTypes, Double>,
    //override val damage: Double
) : CustomWeapon() {
    override val type: ItemTypes = ItemTypes.WEAPON

    init {
        recipe?.let {
            this::class.qualifiedName?.let { it1 -> CustomRecipe.registerRecipe(it, it1) }
        }
    }

    @SuppressWarnings("unused")
    open fun onHit(e: EntityDamageByEntityEvent) {
        // does nothing lol
    }


}