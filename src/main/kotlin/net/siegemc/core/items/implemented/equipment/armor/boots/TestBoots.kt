package net.siegemc.core.items.implemented.equipment.armor.boots

import net.siegemc.core.items.CustomItemUtils
import net.siegemc.core.items.implemented.materials.TestMaterial
import net.siegemc.core.items.recipes.CustomRecipe
import net.siegemc.core.items.types.equipment.armor.CustomBoots
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class TestBoots @Deprecated("Specify quality") constructor() : CustomBoots(
    name = "Test Boots",
    customModelData = 1,
    description = listOf("Boots for testing"),
    levelRequirement = 0,
    material = Material.DIAMOND_BOOTS,
    recipe = CustomRecipe(
        listOf(
            null,
            TestMaterial(0, 1),
            TestMaterial(0, 1),
            TestMaterial(0, 1),
            null,
            null,
            null,
            null
        ), true
    ) { _: Player, fakeRarity: Boolean ->
        val item = TestBoots((0..100).random())
        item.updateMeta(fakeRarity)
        return@CustomRecipe item
    },
    baseStats = CustomItemUtils.statMap(strength = 10.0)
) {

    @Suppress("DEPRECATION")
    constructor(item: ItemStack) : this() {
        this.item = item
        deserialize()
    }

    @Suppress("DEPRECATION")
    constructor(quality: Int) : this() {
        this.quality = quality
    }

    init {
        serialize()
        updateMeta(false)
    }

}