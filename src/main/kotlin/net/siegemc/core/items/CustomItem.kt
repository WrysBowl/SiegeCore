package net.siegemc.core.items

import de.tr7zw.nbtapi.NBTItem
import net.siegemc.core.items.types.ItemTypes
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

abstract class CustomItem {
    open var item: ItemStack = ItemStack(material)
    open var quality: Int = 0
        set(value) {
            field = value
            rarity = Rarity.getFromInt(value)
        }
    open var rarity: Rarity = Rarity.getFromInt(quality)

    abstract val name: String
    abstract val customModelData: Int
    abstract val levelRequirement: Int
    abstract val description: List<String>
    abstract val type: ItemTypes
    abstract val material: Material

    abstract fun updateMeta(): ItemMeta



    // This function takes all of the base item properties and adds them to the NBT of the item. It is extended by all
    // types that introduce new properties that must be saved onto the itemstack.
    open fun serialize(): NBTItem {
        val nbtItem = NBTItem(item)

        nbtItem.setBoolean("customItem", true)
        nbtItem.setString("itemName", name)
        nbtItem.setString("itemType", type.toString())
        nbtItem.setInteger("itemQuality", quality)
        nbtItem.setInteger("custom", quality)
        nbtItem.setString("itemRarity", rarity.toString())
        nbtItem.setInteger("itemLevelRequirement", levelRequirement)
        nbtItem.setString("itemClass", this::class.qualifiedName)

        item = nbtItem.item
        val meta = item.itemMeta
        meta.setCustomModelData(customModelData)
        item.itemMeta = meta
        return nbtItem
    }

    // This function is used when getting an already existing custom item from an ItemStack.
    // It is also extended by any types that save differing properties (such as the StatGemType).
    open fun deserialize(): NBTItem {
        val nbtItem = NBTItem(item)

        quality = nbtItem.getInteger("itemQuality")

        return nbtItem
    }

}