package net.siegemc.core.items.implemented.materials.mobs

import net.siegemc.core.items.types.CustomMaterial
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class Ectoplasm @Deprecated("Specify quality") constructor() : CustomMaterial(
    name = "Ectoplasm",
    customModelData = 310003,
    description = listOf("Undead residue"),
    material = Material.SLIME_BALL,
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