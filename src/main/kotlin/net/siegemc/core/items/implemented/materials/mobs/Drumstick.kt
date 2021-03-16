package net.siegemc.core.items.implemented.materials.mobs

import net.siegemc.core.items.types.CustomMaterial
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class Drumstick @Deprecated("Specify quality") constructor() : CustomMaterial(
    name = "Slime",
    customModelData = 310008,
    description = listOf("A leg of meat from an animal"),
    material = Material.CHICKEN,
    recipe = null
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

    @Suppress("DEPRECATION")
    constructor(quality: Int, tier: Int): this() {
        this.quality = quality
        this.tier = tier
    }

    init {
        serialize()
        updateMeta()
    }

}