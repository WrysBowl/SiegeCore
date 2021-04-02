package net.siegemc.core.items.implemented.armor

import net.siegemc.core.items.CustomItemUtils
import net.siegemc.core.items.enums.Rarity
import net.siegemc.core.items.recipes.recipes
import net.siegemc.core.items.types.armor.CustomChestplate
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class TestChestplate() : CustomChestplate(
    name = "Test Chestplate",
    customModelData = 1,
    description = listOf("A chestplate for testing"),
    levelRequirement = 0,
    material = Material.DIAMOND_CHESTPLATE,
    recipeList = recipes {

    },
    baseStats = CustomItemUtils.statMap(strength = 10.0)
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