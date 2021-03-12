package net.siegemc.core.items.implemented.food

import net.siegemc.core.items.types.CustomFood
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class TestSteak @Deprecated("Specify quality") constructor() : CustomFood(
    name = "Test Steak",
    description = listOf("Food for testing"),
    levelRequirement = 0,
    material = Material.COOKED_BEEF,
    health = 2,
    hunger = 5
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