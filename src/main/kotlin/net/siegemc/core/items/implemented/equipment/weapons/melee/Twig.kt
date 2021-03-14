package net.siegemc.core.items.implemented.equipment.weapons.melee

import net.siegemc.core.items.CustomItemUtils.statMap
import net.siegemc.core.items.types.equipment.weapons.CustomMeleeWeapon
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class Twig @Deprecated("Specify quality") constructor() : CustomMeleeWeapon(
    name = "Twig",
    customModelData = 110001,
    description = listOf("A twig found", "on the ground"),
    levelRequirement = 1,
    material = Material.STICK,
    baseStats = statMap(strength = 2.0),
    attackSpeed = 4.0
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