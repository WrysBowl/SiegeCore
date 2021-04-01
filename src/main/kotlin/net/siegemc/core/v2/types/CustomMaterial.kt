package net.siegemc.core.v2.types

import net.siegemc.core.items.types.CustomFood
import net.siegemc.core.v2.*
import net.siegemc.core.v2.enums.ItemTypes
import net.siegemc.core.v2.enums.NbtTypes
import net.siegemc.core.v2.enums.Rarity
import net.siegemc.core.v2.enums.StatTypes
import net.siegemc.core.v2.recipes.CustomRecipeList
import net.siegemc.core.v2.types.subtypes.CustomEquipment
import org.bukkit.Material
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerItemConsumeEvent
import org.bukkit.inventory.ItemStack

abstract class CustomMaterial(
    override val name: String,
    override val customModelData: Int? = null,
    override val levelRequirement: Int? = null,
    override val description: List<String>,
    override val material: Material,
    override var quality: Int = -1,
    override var item: ItemStack = ItemStack(material),
    override val type: ItemTypes = ItemTypes.MATERIAL,
    override val recipeList: CustomRecipeList
) : CustomItem {

    override var rarity: Rarity = Rarity.COMMON

    var tier: Int = 1
        set(value) {
            field = value
            this.serialize()
        }

    init {
        rarity = Rarity.getFromInt(quality)
    }

    override fun serialize() {
        super.serialize()
        item = item.setNbtTags(
            "materialTier" to tier
        )
    }
    override fun deserialize() {
        super.deserialize()
        val nbtTags = item.getNbtTags("materialTier" to NbtTypes.INT)
        nbtTags["materialTier"]?.let {
            tier = it as Int
        }
    }

    open fun onEat(e: PlayerItemConsumeEvent) {
        e.player.inventory.itemInMainHand.amount = item.amount - 1
        e.player.foodLevel = e.player.foodLevel + this.hunger
        var health: Double = e.player.health + this.health
        if (health > e.player.maxHealth) health = e.player.maxHealth
        e.player.health = health
    }

}