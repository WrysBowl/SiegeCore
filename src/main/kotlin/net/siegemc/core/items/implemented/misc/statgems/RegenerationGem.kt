package net.siegemc.core.items.implemented.misc.statgems

import net.siegemc.core.items.enums.Rarity
import net.siegemc.core.items.enums.StatTypes
import net.siegemc.core.items.recipes.recipes
import net.siegemc.core.items.types.misc.StatGemType
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class RegenerationGem() : StatGemType(
    name = "Regeneration Gem",
    customModelData = 1,
    description = listOf("A powerful gem"),
    levelRequirement = 0,
    material = Material.POPPED_CHORUS_FRUIT,
    recipeList = recipes {

    },
    statType = StatTypes.REGENERATION
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