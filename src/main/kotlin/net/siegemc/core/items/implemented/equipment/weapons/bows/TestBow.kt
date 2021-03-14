package net.siegemc.core.items.implemented.equipment.weapons.bows

import net.siegemc.core.items.CustomItemUtils
import net.siegemc.core.items.types.equipment.weapons.CustomBow
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class TestBow @Deprecated("Specify quality") constructor() : CustomBow(
    name = "Test Bow",
    customModelData = 1,
    description = listOf("A bow for testing"),
    levelRequirement = 0,
    material = Material.BOW,
    baseStats = CustomItemUtils.statMap(strength = 10.0),
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