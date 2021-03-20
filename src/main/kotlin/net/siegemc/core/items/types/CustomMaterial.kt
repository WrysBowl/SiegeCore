package net.siegemc.core.items.types

import de.tr7zw.nbtapi.NBTItem
import net.siegemc.core.items.CustomItem
import net.siegemc.core.items.recipes.CustomRecipe
import net.siegemc.core.utils.Utils
import net.siegemc.core.v2.enums.ItemTypes
import org.bukkit.Material
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.meta.ItemMeta

abstract class CustomMaterial(
    final override val name: String,
    final override val customModelData: Int,
    final override val description: List<String>,
    final override val material: Material,
    final override val recipe: CustomRecipe?
) : CustomItem() {

    init {
        recipe?.let {
            this::class.qualifiedName?.let { it1 -> CustomRecipe.registerRecipe(it, it1) }
        }
    }

    override val type: ItemTypes = ItemTypes.MATERIAL
    override val levelRequirement: Int = 0

    open var tier: Int = 1
        set(value) {
            field = value
            val nbtItem = NBTItem(item)
            nbtItem.setInteger("materialTier", value)
            item = nbtItem.item
        }


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

    override fun updateMeta(hideRarity: Boolean): ItemMeta {

        val meta = item.itemMeta

        meta.displayName(Utils.parse("<gray>$name <yellow>${"✪".repeat(tier)}"))

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