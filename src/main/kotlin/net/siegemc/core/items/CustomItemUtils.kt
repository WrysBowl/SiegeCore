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

    fun statMap(
        strength: Double? = null,
        regeneration: Double? = null,
        toughness: Double? = null,
        health: Double? = null,
        luck: Double? = null
    ): HashMap<StatTypes, Double> {
        val map = hashMapOf<StatTypes, Double>()
        strength?.let { map[StatTypes.STRENGTH] = it }
        regeneration?.let { map[StatTypes.REGENERATION] = it }
        toughness?.let { map[StatTypes.TOUGHNESS] = it }
        health?.let { map[StatTypes.HEALTH] = it }
        luck?.let { map[StatTypes.LUCK] = it }
        return map
    }
}