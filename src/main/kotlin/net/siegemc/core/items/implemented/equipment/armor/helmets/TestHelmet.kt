package net.siegemc.core.items.implemented.equipment.armor.helmets

import net.siegemc.core.v2.CustomItemUtils
import net.siegemc.core.items.implemented.materials.TestMaterial
import net.siegemc.core.items.recipes.CustomRecipe
import net.siegemc.core.items.types.equipment.armor.CustomHelmet
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class TestHelmet @Deprecated("Specify quality") constructor() : CustomHelmet(
    name = "Test Helmet",
    customModelData = 1,
    description = listOf("Helmet for testing"),
    levelRequirement = 0,
    material = Material.DIAMOND_HELMET,
    recipe = CustomRecipe(
        listOf(TestMaterial(0, 1), null, TestMaterial(0, 1), null, null, null, null, null),
        true
    ) { _: Player, fakeRarity: Boolean ->
        val item = TestHelmet((0..100).random())
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