package net.siegemc.core.items.implemented.materials.mobs

import net.siegemc.core.items.implemented.materials.TestMaterial
import net.siegemc.core.items.recipes.CustomRecipe
import net.siegemc.core.items.types.CustomMaterial
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class Wool @Deprecated("Specify quality") constructor() : CustomMaterial(
    name = "Wool",
    customModelData = 310005,
    description = listOf("A ball of fluff", "to keep you warm"),
    material = Material.WHITE_WOOL,
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