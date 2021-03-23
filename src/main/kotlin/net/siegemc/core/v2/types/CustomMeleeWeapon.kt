package net.siegemc.core.v2.types

import net.siegemc.core.items.Rarity
import net.siegemc.core.items.StatGem
import net.siegemc.core.items.StatTypes
import net.siegemc.core.items.types.ItemTypes
import net.siegemc.core.v2.interfaces.CustomEquipment
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

abstract class CustomMeleeWeapon(
    override val name: String,
    override val customModelData: Int? = null,
    override val levelRequirement: Int? = null,
    override val description: List<String>,
    override val material: Material,
    override var quality: Int = -1,
    override var item: ItemStack = ItemStack(material),
    override val baseStats: HashMap<StatTypes, Double>,
    override val type: ItemTypes = ItemTypes.MELEEWEAPON,
    val attackSpeed: Double,
    override var statGem: StatGem? = null
) : CustomEquipment {

    override var rarity: Rarity = Rarity.COMMON

    init {
        rarity = Rarity.getFromInt(quality)
    }

}