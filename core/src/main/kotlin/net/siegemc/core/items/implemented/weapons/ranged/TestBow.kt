package net.siegemc.core.items.implemented.weapons.ranged

import net.siegemc.core.items.CustomItemUtils
import net.siegemc.core.items.enums.Rarity
import net.siegemc.core.items.recipes.recipes
import net.siegemc.core.items.types.weapons.CustomBow
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class TestBow() : CustomBow(
    name = "Test Bow",
    customModelData = 1,
    description = listOf("A bow for testing"),
    levelRequirement = 0,
    material = Material.BOW,
    baseStats = CustomItemUtils.statMap(strength = 20.0),
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