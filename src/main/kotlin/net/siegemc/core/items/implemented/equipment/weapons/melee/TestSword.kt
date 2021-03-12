package net.siegemc.core.items.implemented.equipment.weapons.melee

import net.siegemc.core.items.CustomItemUtils.statMap
import net.siegemc.core.items.types.equipment.weapons.CustomMeleeWeapon
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class TestSword @Deprecated("Specify quality") constructor() : CustomMeleeWeapon(
    name = "Test Sword",
    description = listOf("A sword for testing"),
    levelRequirement = 0,
    material = Material.DIAMOND_SWORD,
    baseStats = statMap(strength = 10.0),
    damage = 1.2,
    attackSpeed = 1.7
) {

    @Suppress("DEPRECATION")
    constructor(item: ItemStack): this() {
        this.item = item
        deserialize()
    }

    @Suppress("DEPRECATION")
    constructor(quality: Int): this() {
        this.quality = quality
    }

    init {
        serialize()
        updateMeta()
    }

}