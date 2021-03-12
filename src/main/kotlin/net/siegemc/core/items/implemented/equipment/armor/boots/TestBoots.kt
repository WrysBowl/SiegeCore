package net.siegemc.core.items.implemented.equipment.armor.boots

import net.siegemc.core.items.CustomItemUtils
import net.siegemc.core.items.types.equipment.armor.CustomBoots
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class TestBoots @Deprecated("Specify quality") constructor() : CustomBoots(
    name = "Test Boots",
    description = listOf("Boots for testing"),
    levelRequirement = 0,
    material = Material.DIAMOND_BOOTS,
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