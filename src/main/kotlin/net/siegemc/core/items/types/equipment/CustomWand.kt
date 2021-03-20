package net.siegemc.core.items.types.equipment

import de.tr7zw.nbtapi.NBTItem
import net.siegemc.core.v2.enums.StatTypes
import net.siegemc.core.items.recipes.CustomRecipe
import net.siegemc.core.v2.enums.ItemTypes
import net.siegemc.core.items.types.equipment.weapons.CustomWeapon
import org.bukkit.Material

abstract class CustomWand(
    final override val name: String,
    final override val customModelData: Int,
    final override val description: List<String>,
    final override val levelRequirement: Int,
    final override val material: Material,
    final override val recipe: CustomRecipe?,
    override val baseStats: HashMap<StatTypes, Double>,
    val range: Int = 12,
    val red: Int = 255,
    val green: Int = 255,
    val blue: Int = 255,
    val manaRequired: Int = 0,
    val damageRadius: Double = 2.5
) : CustomWeapon() {

    init {
        recipe?.let {
            this::class.qualifiedName?.let { it1 -> CustomRecipe.registerRecipe(it, it1) }
        }
    }

    override val type: ItemTypes = ItemTypes.WAND

    override fun serialize(): NBTItem {
        val nbtItem = super.serialize()

        nbtItem.setInteger("wandRange", range)
        nbtItem.setInteger("wandRed", red)
        nbtItem.setInteger("wandGreen", green)
        nbtItem.setInteger("wandBlue", blue)
        nbtItem.setDouble("wandDamageRadius", damageRadius)
        nbtItem.setInteger("wandManaRequired", manaRequired)

        item = nbtItem.item
        return nbtItem
    }

}