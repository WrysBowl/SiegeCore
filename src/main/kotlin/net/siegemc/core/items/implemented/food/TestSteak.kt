package net.siegemc.core.items.implemented.food

import net.siegemc.core.items.implemented.equipment.armor.chestplates.TestChestplate
import net.siegemc.core.items.implemented.materials.TestMaterial
import net.siegemc.core.items.recipes.CustomRecipe
import net.siegemc.core.items.types.CustomFood
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class TestSteak @Deprecated("Specify quality") constructor() : CustomFood(
    name = "Test Steak",
    customModelData = 1,
    description = listOf("Food for testing"),
    levelRequirement = 0,
    material = Material.COOKED_BEEF,
    recipe = CustomRecipe(listOf(TestMaterial(0, 2), TestMaterial(0, 1), TestMaterial(0, 1), TestMaterial(0, 1), TestMaterial(0, 1), TestMaterial(0, 1), TestMaterial(0, 1), TestMaterial(0, 1), TestMaterial(0, 1))) {
        return@CustomRecipe TestSteak((0..100).random())
    },
    health = 2,
    hunger = 5
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

    init {
        serialize()
        updateMeta()
    }

}