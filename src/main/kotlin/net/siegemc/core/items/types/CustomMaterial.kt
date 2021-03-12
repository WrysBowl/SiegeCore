package net.siegemc.core.items.types

import de.tr7zw.nbtapi.NBTItem
import net.siegemc.core.items.CustomItem
import net.siegemc.core.utils.Utils
import org.bukkit.Material
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.meta.ItemMeta

abstract class CustomMaterial(
    final override val name: String,
    final override val customModelData: Int,
    final override val description: List<String>,
    final override val material: Material
) : CustomItem() {
    override val type: ItemTypes = ItemTypes.MATERIAL
    override val levelRequirement: Int = 0

    open var tier: Int = 1


    override fun serialize(): NBTItem {
        val nbtItem = super.serialize()

        nbtItem.setInteger("materialTier", tier)

        item = nbtItem.item
        return nbtItem
    }

    override fun deserialize(): NBTItem {
        val nbtItem = super.deserialize()

        tier = nbtItem.getInteger("materialTier")

        return nbtItem
    }

    override fun updateMeta(): ItemMeta {

        val meta = item.itemMeta

        meta.displayName(Utils.parse("<gray>$name <yellow>${"☆".repeat(tier)}"))

        val newLore = mutableListOf(Utils.parse(" "))
        description.forEach {
            newLore.add(Utils.parse("<dark_gray>$it"))
        }
        meta.lore(newLore)

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE)
        item.itemMeta = meta
        return meta
    }


}