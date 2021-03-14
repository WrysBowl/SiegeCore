package net.siegemc.core.items.implemented.equipment.weapons.melee

import net.siegemc.core.items.CustomItemUtils.statMap
import net.siegemc.core.items.types.equipment.weapons.CustomMeleeWeapon
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class Sticky_Stick @Deprecated("Specify quality") constructor() : CustomMeleeWeapon(
    name = "Sticky Twig",
    customModelData = 110002,
    description = listOf("Globs of slime", "on a stick"),
    levelRequirement = 5,
    material = Material.STICK,
    baseStats = statMap(strength = 2.5),
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