package net.siegemc.core.items.types

import de.tr7zw.nbtapi.NBTItem
import net.siegemc.core.items.CustomItem
import net.siegemc.core.items.Rarity
import net.siegemc.core.items.StatTypes
import net.siegemc.core.utils.Utils
import org.bukkit.Material
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.meta.ItemMeta

abstract class StatGemType(
    final override val name: String,
    final override val customModelData: Int,
    final override val description: List<String>,
    final override val levelRequirement: Int,
    final override val material: Material,
    val statType: StatTypes
) : CustomItem() {
    override val type: ItemTypes = ItemTypes.STATGEM

    open var statAmount: Double = 0.0

    override fun serialize(): NBTItem {
        val nbtItem = super.serialize()

        nbtItem.setString("statGemType", statType.toString())
        nbtItem.setDouble("statGemAmount", statAmount)

        item = nbtItem.item
        return nbtItem
    }

    override fun deserialize(): NBTItem {
        val nbtItem = super.deserialize()

        statAmount = nbtItem.getDouble("statGemAmount")

        return nbtItem
    }

    override fun updateMeta(): ItemMeta {

        val meta = item.itemMeta

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
        return meta
    }

}