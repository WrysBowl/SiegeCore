package net.siegemc.core.items.implemented.statgems

import net.siegemc.core.items.StatTypes
import net.siegemc.core.items.types.StatGemType
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class StrengthGem @Deprecated("Specify quality") constructor() : StatGemType(
    name = "Strength Gem",
    customModelData = 1,
    description = listOf("A powerful gem"),
    levelRequirement = 0,
    material = Material.POPPED_CHORUS_FRUIT,
    statType = StatTypes.STRENGTH
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