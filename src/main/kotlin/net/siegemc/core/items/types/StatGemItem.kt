package net.siegemc.core.items.types

import net.siegemc.core.items.CustomItem
import net.siegemc.core.items.Rarity
import net.siegemc.core.items.StatTypes
import net.siegemc.core.utils.Utils
import org.bukkit.inventory.ItemFlag

abstract class StatGemItem : CustomItem {
    override val type: ItemTypes = ItemTypes.STATGEM
    override val description: List<String> = listOf("Delicious food")

    abstract val statType: StatTypes
    abstract var statAmount: Double

    override fun updateMeta() {

        val meta = item.itemMeta ?: return

        meta.displayName(Utils.parse(if (rarity == Rarity.SPECIAL) "<rainbow>$name</rainbow>" else "${rarity.color}$name"))

        val newLore = mutableListOf(Utils.parse(if (rarity == Rarity.SPECIAL) "<rainbow>$rarity</rainbow> <gray>$quality%" else "${rarity.color}$rarity <gray>$quality%"))
        newLore.add(Utils.parse(" "))
        newLore.add(Utils.parse("<color:#FF3CFF>+${statAmount} <light_purple>${statType.stylizedName} Gem"))
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