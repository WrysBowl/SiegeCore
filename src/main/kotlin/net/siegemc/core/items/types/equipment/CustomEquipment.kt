package net.siegemc.core.items.types.equipment

import de.tr7zw.nbtapi.NBTItem
import net.siegemc.core.items.CustomItem
import net.siegemc.core.items.Rarity
import net.siegemc.core.items.StatGem
import net.siegemc.core.items.StatTypes
import net.siegemc.core.utils.Utils
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.meta.ItemMeta

abstract class CustomEquipment : CustomItem() {

    open var statGem: StatGem? = null
    abstract val baseStats: HashMap<StatTypes, Double>

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
            val realStats = getStats(addGem = false, addRarity = true)
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

    override fun serialize(): NBTItem {
        val nbtItem = super.serialize()

        nbtItem.setString("equipmentStatGem", if (statGem != null) statGem!!.toString() else "false")

        item = nbtItem.item
        return nbtItem
    }


    open fun getStats(addGem: Boolean, addRarity: Boolean): HashMap<StatTypes, Double> {
        val map = hashMapOf<StatTypes, Double>()
        StatTypes.values().forEach {
            var totalAmount = 0.0
            if (baseStats.containsKey(it)) {
                totalAmount += baseStats[it]!!
            }
            if (addGem) {
                statGem?.let { gem ->
                    if (gem.type == it) {
                        totalAmount += gem.amount
                    }
                }
            }
            if (addRarity) {
                totalAmount *= getRarityMultiplier(quality)
            }

            map[it] = totalAmount
        }
        return map
    }

    open fun getRarityMultiplier(quality: Int): Double = quality / 100 + 0.5


}