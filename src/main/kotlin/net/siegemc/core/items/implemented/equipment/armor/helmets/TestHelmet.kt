package net.siegemc.core.items.implemented.equipment.armor.helmets

import net.siegemc.core.items.CustomItemUtils
import net.siegemc.core.items.types.equipment.armor.CustomHelmet
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class TestHelmet @Deprecated("Specify quality") constructor() : CustomHelmet(
    name = "Test Helmet",
    description = listOf("Helmet for testing"),
    levelRequirement = 0,
    material = Material.DIAMOND_HELMET,
    baseStats = CustomItemUtils.statMap(strength = 10.0)
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