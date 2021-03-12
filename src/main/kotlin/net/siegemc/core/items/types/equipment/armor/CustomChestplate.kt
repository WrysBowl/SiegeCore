package net.siegemc.core.items.types.equipment.armor

import net.siegemc.core.items.StatTypes
import net.siegemc.core.items.types.ItemTypes
import org.bukkit.Material

abstract class CustomChestplate(
    final override val name: String,
    final override val description: List<String>,
    final override val levelRequirement: Int,
    final override val material: Material,
    override val baseStats: HashMap<StatTypes, Double>
) : CustomArmor() {

    override val type: ItemTypes = ItemTypes.CHESTPLATE

}