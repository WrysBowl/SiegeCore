package net.siegemc.core.items

import de.tr7zw.nbtapi.NBTItem
import org.bukkit.inventory.ItemStack
import java.lang.reflect.Constructor


object CustomItemUtils {

    fun getCustomItem(item: ItemStack): CustomItem? {
        val nbtItem = NBTItem(item)
        return if (nbtItem.hasKey("itemClass")) {
            try {
                val className = nbtItem.getString("itemClass")
                val clazz = Class.forName(className)
                val constructor: Constructor<out Any> = clazz.getConstructor(ItemStack::class.java)
                val newClass = constructor.newInstance(item)
                newClass as? CustomItem

            } catch(e: Exception) {
                null
            }
        } else {
            null
        }

    }
}