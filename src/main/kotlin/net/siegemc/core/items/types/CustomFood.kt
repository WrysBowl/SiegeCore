package net.siegemc.core.items.types

import de.tr7zw.nbtapi.NBTItem
import net.siegemc.core.items.CustomItem
import net.siegemc.core.items.CustomItemUtils
import net.siegemc.core.items.Rarity
import net.siegemc.core.utils.Utils
import org.bukkit.Material
import org.bukkit.event.player.PlayerItemConsumeEvent
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.meta.ItemMeta

abstract class CustomFood(
    final override val name: String,
    final override val description: List<String>,
    final override val levelRequirement: Int,
    final override val material: Material,
    val health: Int = 0,
    val hunger: Int = 0
) : CustomItem() {
    override val type: ItemTypes = ItemTypes.FOOD


    open fun onEat(e: PlayerItemConsumeEvent) {
        CustomItemUtils.getCustomItem(e.item)?.let {
            if (it !is CustomFood) return
            e.isCancelled = true
            e.player.inventory.itemInMainHand.amount = item.amount - 1
            e.player.foodLevel = e.player.foodLevel + it.hunger
            var health: Double = e.player.health + it.health
            if (health > e.player.maxHealth) health = e.player.maxHealth
            e.player.health = health
        }
    }

    override fun serialize(): NBTItem {
        val nbtItem = super.serialize()

        nbtItem.setInteger("foodHealth", health)
        nbtItem.setInteger("foodHunger", hunger)


        item = nbtItem.item
        return nbtItem
    }

    override fun updateMeta(): ItemMeta {

        val meta = item.itemMeta

        meta.displayName(Utils.parse(if (rarity == Rarity.SPECIAL) "<rainbow>$name</rainbow>" else "${rarity.color}$name"))

        val newLore =
            mutableListOf(Utils.parse(if (rarity == Rarity.SPECIAL) "<rainbow>$rarity</rainbow> <gray>$quality%" else "${rarity.color}$rarity <gray>$quality%"))
        val realHunger = hunger * getRarityMultiplier(quality)
        val realHealth = health * getRarityMultiplier(quality)
        if (realHunger > 0 || realHealth > 0) newLore.add(Utils.parse(" "))
        if (realHunger > 0) newLore.add(Utils.parse("<red>+$realHunger Hunger"))
        if (realHealth > 0) newLore.add(Utils.parse("<red>+$realHealth Health"))
        newLore.add(Utils.parse(" "))
        description.forEach {
            newLore.add(Utils.parse("<dark_gray>$it"))
        }
        newLore.add(Utils.parse(" "))
        newLore.add(Utils.parse("<gray>Level: $levelRequirement"))
        meta.lore(newLore)

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE)
        item.itemMeta = meta
        return meta
    }

    private fun getRarityMultiplier(quality: Int): Double = quality / 100 + 0.5

}