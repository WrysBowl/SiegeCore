package net.siegemc.core.v2.interfaces

import net.siegemc.core.items.CustomItemUtils
import net.siegemc.core.items.Rarity
import net.siegemc.core.items.StatGem
import net.siegemc.core.items.StatTypes
import net.siegemc.core.utils.Utils
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.meta.ItemMeta

interface CustomEquipment : CustomItem {

    var statGem: StatGem?
    val baseStats: HashMap<StatTypes, Double>

    fun addStatGem(newStatGem: StatGem) {
        this.statGem = newStatGem
        println("serializing")
    }

    override fun updateMeta(hideRarity: Boolean): ItemMeta {
        val meta = item.itemMeta

        meta.displayName(Utils.parse(if (rarity == Rarity.SPECIAL) "<rainbow>$name</rainbow>" else "${rarity.color}$name"))

        val newLore =
            mutableListOf(Utils.parse(if (rarity == Rarity.SPECIAL) "<rainbow>$rarity</rainbow> <gray>${if (hideRarity) 50 else quality}%" else "${rarity.color}$rarity <gray>$quality%"))
        statGem?.let {
            newLore.add(Utils.parse(" "))
            newLore.add(Utils.parse("<color:#FF3CFF>+${it.amount} <light_purple>${it.type.stylizedName} Gem"))
        }
        if (baseStats.size != 0) {
            newLore.add(Utils.parse(" "))
            val realStats = CustomItemUtils.getStats(this, addGem = false, addRarity = true)
            baseStats.keys.forEach {
                newLore.add(Utils.parse("<green>+${realStats[it]} <gray>${it.stylizedName}"))
            }
        }
        newLore.add(Utils.parse(" "))
        description.forEach {
            newLore.add(Utils.parse("<dark_gray>$it"))
        }
        newLore.add(Utils.parse(" "))
        newLore.add(Utils.parse("<gray>Level: $levelRequirement"))
        if (hideRarity) newLore.add(Utils.parse("<red>This is not the real item"))
        meta.lore(newLore)

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE)
        item.itemMeta = meta
        return meta
    }
}