package net.siegemc.core.items.types

import net.siegemc.core.items.CustomItem
import net.siegemc.core.items.CustomItemUtils
import net.siegemc.core.items.Rarity
import net.siegemc.core.utils.Utils
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerItemConsumeEvent
import org.bukkit.inventory.ItemFlag

abstract class CustomFood : CustomItem {
    override val type: ItemTypes = ItemTypes.FOOD
    override val description: List<String> = listOf("Delicious food")

    open val health: Int = 0
    open val hunger: Int = 0

    open fun onEat(e: PlayerItemConsumeEvent) {
        // TODO(CALL THIS IN A LISTENER)
        CustomItemUtils.getCustomItem(e.item)?.let {
            if (it !is CustomFood) return
            e.isCancelled = true
            e.player.inventory.itemInMainHand.amount = item.amount - 1
            e.player.foodLevel = e.player.foodLevel + it.hunger
            var health: Double = e.player.health + it.health
            if (health > e.player.getMaxHealth()) health = e.player.getMaxHealth()
            e.player.health = health
        }
    }

    override fun updateMeta() {

        val meta = item.itemMeta ?: return

        meta.displayName(Utils.parse(if (rarity == Rarity.SPECIAL) "<rainbow>$name</rainbow>" else "${rarity.color}$name"))

        val newLore = mutableListOf(Utils.parse(if (rarity == Rarity.SPECIAL) "<rainbow>$rarity</rainbow> <gray>$quality%" else "${rarity.color}$rarity <gray>$quality%"))
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
    }

    private fun getRarityMultiplier(quality: Int): Double = quality / 100 + 0.5

}