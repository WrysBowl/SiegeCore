package net.siegemc.core.items.implemented

import net.siegemc.core.items.Rarity
import net.siegemc.core.items.types.ItemTypes
import net.siegemc.core.items.types.equipment.weapons.CustomMeleeWeapon
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class TestSword : CustomMeleeWeapon {

    override var item: ItemStack

    override val name: String = "Test Sword"
    override val levelRequirement: Int = 0
    override val description: List<String> = listOf("A sword for testing")
    override val quality: Int
    override val rarity: Rarity
    override val material: Material = Material.DIAMOND_SWORD


}