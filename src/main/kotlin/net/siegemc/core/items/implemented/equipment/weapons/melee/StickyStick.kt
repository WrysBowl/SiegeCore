package net.siegemc.core.items.implemented.equipment.weapons.melee

import net.siegemc.core.items.CustomItemUtils.statMap
import net.siegemc.core.items.implemented.equipment.armor.chestplates.TestChestplate
import net.siegemc.core.items.implemented.materials.TestMaterial
import net.siegemc.core.items.recipes.CustomRecipe
import net.siegemc.core.items.types.equipment.weapons.CustomMeleeWeapon
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class StickyStick @Deprecated("Specify quality") constructor() : CustomMeleeWeapon(
    name = "Sticky Twig",
    customModelData = 110002,
    description = listOf("Globs of slime", "on a stick"),
    levelRequirement = 5,
    material = Material.STICK,
    recipe = CustomRecipe(
        listOf(
            TestMaterial(0, 1),
            TestMaterial(0, 1),
            TestMaterial(0, 1),
            TestMaterial(0, 1),
            TestMaterial(0, 1),
            TestMaterial(0, 1),
            TestMaterial(0, 1),
            TestMaterial(0, 1),
            TestMaterial(0, 1)
        )
    ) { _: Player, fakeRarity: Boolean ->
        val item = StickyStick((0..100).random())
        item.updateMeta(fakeRarity)
        return@CustomRecipe item
    },
    baseStats = statMap(strength = 2.5),
    attackSpeed = 4.0
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