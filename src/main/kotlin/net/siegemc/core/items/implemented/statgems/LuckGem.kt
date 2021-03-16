package net.siegemc.core.items.implemented.statgems

import net.siegemc.core.items.StatTypes
import net.siegemc.core.items.implemented.equipment.armor.chestplates.TestChestplate
import net.siegemc.core.items.implemented.materials.TestMaterial
import net.siegemc.core.items.recipes.CustomRecipe
import net.siegemc.core.items.types.StatGemType
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class LuckGem @Deprecated("Specify quality") constructor() : StatGemType(
    name = "Luck Gem",
    customModelData = 1,
    description = listOf("A powerful gem"),
    levelRequirement = 0,
    material = Material.POPPED_CHORUS_FRUIT,
    recipe = CustomRecipe(listOf(TestMaterial(0, 3), TestMaterial(0, 3), TestMaterial(0, 1), TestMaterial(0, 1), TestMaterial(0, 1), TestMaterial(0, 1), TestMaterial(0, 1), TestMaterial(0, 1), TestMaterial(0, 1))) {
        return@CustomRecipe LuckGem((0..100).random())
    },
    statType = StatTypes.LUCK
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
    constructor(quality: Int, statAmount: Double): this() {
        this.quality = quality
        this.statAmount = statAmount
    }

    init {
        serialize()
        updateMeta()
    }

}