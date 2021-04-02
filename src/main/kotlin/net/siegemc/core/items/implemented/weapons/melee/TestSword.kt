package net.siegemc.core.items.implemented.weapons.melee

import net.siegemc.core.items.CustomItemUtils
import net.siegemc.core.items.enums.Rarity
import net.siegemc.core.items.recipes.recipes
import net.siegemc.core.items.types.weapons.CustomMeleeWeapon
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class TestSword() : CustomMeleeWeapon(
    name = "Test Sword",
    customModelData = 1,
    description = listOf("A sword for testing"),
    levelRequirement = 0,
    material = Material.DIAMOND_SWORD,
    baseStats = CustomItemUtils.statMap(strength = 10.0),
    recipeList = recipes {

    },
    attackSpeed = 1.7
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