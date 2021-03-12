package net.siegemc.core.items.implemented.equipment.armor.chestplates

import net.siegemc.core.items.CustomItemUtils
import net.siegemc.core.items.types.equipment.armor.CustomChestplate
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class TestChestplate @Deprecated("Specify quality") constructor() : CustomChestplate(
    name = "Test Chestplate",
    customModelData = 1,
    description = listOf("Chestplate for testing"),
    levelRequirement = 0,
    material = Material.DIAMOND_CHESTPLATE,
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