package net.siegemc.core.items.implemented.equipment.weapons.melee

import net.siegemc.core.v2.CustomItemUtils.statMap
import net.siegemc.core.items.implemented.materials.TestMaterial
import net.siegemc.core.items.recipes.CustomRecipe
import net.siegemc.core.items.types.equipment.weapons.CustomMeleeWeapon
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class Twig @Deprecated("Specify quality") constructor() : CustomMeleeWeapon(
    name = "Twig",
    customModelData = 110001,
    description = listOf("A twig found", "on the ground"),
    levelRequirement = 1,
    material = Material.STICK,
    recipe = CustomRecipe(
        listOf(
            null,
            null,
            TestMaterial(0, 1),
            TestMaterial(0, 1),
            null,
            null,
            TestMaterial(0, 1),
            TestMaterial(0, 1),
            TestMaterial(0, 1)
        )
    ) { _: Player, fakeRarity: Boolean ->
        val item = Twig((0..100).random())
        item.updateMeta(fakeRarity)
        return@CustomRecipe item
    },
    baseStats = statMap(strength = 2.0),
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