package net.siegemc.core.items.implemented.misc.materials

import net.siegemc.core.items.enums.Rarity
import net.siegemc.core.items.recipes.recipes
import net.siegemc.core.items.types.misc.CustomMaterial
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class DirtClump() : CustomMaterial(
    name = "Dirt Clump",
    customModelData = 320001,
    description = listOf("A muddy clump of earth"),
    levelRequirement = 0,
    material = Material.FLINT,
    recipeList = recipes {
    }
) {

    constructor(quality: Int): this() {
        this.quality = quality
        this.rarity = Rarity.getFromInt(quality)
    }

    constructor(item: ItemStack): this() {
        this.item = item
        deserialize()
    }

}