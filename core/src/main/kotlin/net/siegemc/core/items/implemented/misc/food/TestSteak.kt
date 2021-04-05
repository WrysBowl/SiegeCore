package net.siegemc.core.items.implemented.misc.food

import net.siegemc.core.items.enums.Rarity
import net.siegemc.core.items.recipes.recipes
import net.siegemc.core.items.types.misc.CustomFood
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class TestSteak() : CustomFood(
    name = "Test Steak",
    customModelData = 1,
    description = listOf("A food for testing"),
    levelRequirement = 0,
    material = Material.COOKED_BEEF,
    recipeList = recipes {

    },
    health = 2
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