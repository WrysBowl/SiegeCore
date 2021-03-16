package net.siegemc.core.items.implemented.materials

import net.siegemc.core.items.implemented.equipment.armor.chestplates.TestChestplate
import net.siegemc.core.items.recipes.CustomRecipe
import net.siegemc.core.items.types.CustomMaterial
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class TestMaterial @Deprecated("Specify quality") constructor() : CustomMaterial(
    name = "Test Material",
    customModelData = 1,
    description = listOf("its iron"),
    material = Material.IRON_INGOT,
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