package net.siegemc.core.v2.types

import net.siegemc.core.items.types.CustomFood
import net.siegemc.core.v2.CustomItem
import net.siegemc.core.v2.CustomItemUtils
import net.siegemc.core.v2.StatGem
import net.siegemc.core.v2.enums.ItemTypes
import net.siegemc.core.v2.enums.Rarity
import net.siegemc.core.v2.enums.StatTypes
import net.siegemc.core.v2.recipes.CustomRecipeList
import net.siegemc.core.v2.types.subtypes.CustomEquipment
import org.bukkit.Material
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerItemConsumeEvent
import org.bukkit.inventory.ItemStack

abstract class CustomFood(
    override val name: String,
    override val customModelData: Int? = null,
    override val levelRequirement: Int? = null,
    override val description: List<String>,
    override val material: Material,
    override var quality: Int = -1,
    override var item: ItemStack = ItemStack(material),
    override val type: ItemTypes = ItemTypes.FOOD,
    override val recipeList: CustomRecipeList,
    val health: Int = 0,
    val hunger: Int = 0
) : CustomItem {

    override var rarity: Rarity = Rarity.COMMON

    init {
        rarity = Rarity.getFromInt(quality)
    }

    open fun onEat(e: PlayerItemConsumeEvent) {
        e.player.inventory.itemInMainHand.amount = item.amount - 1
        e.player.foodLevel = e.player.foodLevel + this.hunger
        var health: Double = e.player.health + this.health
        if (health > e.player.maxHealth) health = e.player.maxHealth
        e.player.health = health
    }

}