package net.siegemc.core.items.implemented.equipment.weapons.melee

import net.siegemc.core.v2.CustomItemUtils.statMap
import net.siegemc.core.items.implemented.materials.TestMaterial
import net.siegemc.core.items.recipes.CustomRecipe
import net.siegemc.core.items.types.equipment.weapons.CustomMeleeWeapon
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class TestSword @Deprecated("Specify quality") constructor() : CustomMeleeWeapon(
    name = "Test Sword",
    customModelData = 1,
    description = listOf("A sword for testing"),
    levelRequirement = 0,
    material = Material.DIAMOND_SWORD,
    recipe = CustomRecipe(
        listOf(
            null,
            null,
            TestMaterial(0, 1),
            TestMaterial(0, 1),
            null,
            null,
            null,
            TestMaterial(0, 1),
            TestMaterial(0, 1)
        )
    ) { _: Player, fakeRarity: Boolean ->
        val item = TestSword((0..100).random())
        item.updateMeta(fakeRarity)
        return@CustomRecipe item
    },
    baseStats = statMap(strength = 10.0),
    attackSpeed = 1.7
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