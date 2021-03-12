package net.siegemc.core.items.implemented.equipment.armor.leggings

import net.siegemc.core.items.CustomItemUtils
import net.siegemc.core.items.types.equipment.armor.CustomLeggings
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class TestLeggings @Deprecated("Specify quality") constructor() : CustomLeggings(
    name = "Test Leggings",
    customModelData = 1,
    description = listOf("Boots for testing"),
    levelRequirement = 0,
    material = Material.DIAMOND_LEGGINGS,
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