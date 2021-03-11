package net.siegemc.core.items

import de.tr7zw.nbtapi.NBTItem
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import java.lang.reflect.Constructor


object CustomItemUtils {

    val materials = hashMapOf<String, Material>(
        "TestSword" to Material.DIAMOND_SWORD,
        "TestBoots" to Material.DIAMOND_BOOTS,
        "TestChestplate" to Material.DIAMOND_CHESTPLATE,
        "TestLeggings" to Material.DIAMOND_LEGGINGS,
        "TestHelmet" to Material.DIAMOND_HELMET,
    )

    fun getCustomItem(item: ItemStack): CustomItem? {
        val nbtItem = NBTItem(item)
        return if (nbtItem.hasKey("itemClass")) {
            val type = nbtItem.getString("itemType")
            if (type.equals("Stat Gem")) {
                val statAmount = nbtItem.getString("statGemAmount")
            }
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