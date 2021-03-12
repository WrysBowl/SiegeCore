package net.siegemc.core.items.implemented.equipment.wands

import net.siegemc.core.items.CustomItemUtils.statMap
import net.siegemc.core.items.types.equipment.CustomWand
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class TestWand @Deprecated("Specify quality") constructor() : CustomWand(
    name = "Test Wand",
    customModelData = 1,
    description = listOf("A wand for testing"),
    levelRequirement = 0,
    material = Material.STICK,
    baseStats = statMap(strength = 10.0),
    damage = 5.0,
    range = 15,
    red = 100,
    green = 100,
    blue = 100,
    manaRequired = 2,
    damageRadius = 3.5
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