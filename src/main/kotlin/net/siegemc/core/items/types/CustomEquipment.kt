package net.siegemc.core.items.types

import net.siegemc.core.items.CustomItem
import net.siegemc.core.items.StatGem
import net.siegemc.core.utils.Utils
import org.bukkit.inventory.ItemFlag

abstract class CustomEquipment : CustomItem {
    abstract var statGem: StatGem?

    override fun updateMeta() {
        val meta = item.itemMeta ?: return

        meta.displayName(Utils.parse("${rarity.color}${name}"))

        var newLore = mutableListOf(Utils.parse("${rarity.color}<gray>"))
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

}