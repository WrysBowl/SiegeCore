package net.siegemc.core.items.types.equipment.weapons

import net.siegemc.core.items.StatTypes
import net.siegemc.core.items.types.ItemTypes
import org.bukkit.Material
import org.bukkit.event.entity.EntityDamageByEntityEvent

abstract class CustomBow(
    final override val name: String,
    final override val customModelData: Int,
    final override val description: List<String>,
    final override val levelRequirement: Int,
    final override val material: Material,
    override val baseStats: HashMap<StatTypes, Double>,
    override val damage: Double
) : CustomWeapon() {
    override val type: ItemTypes = ItemTypes.WEAPON

    @SuppressWarnings("unused")
    open fun onHit(e: EntityDamageByEntityEvent) {
        // does nothing lol
    }


}