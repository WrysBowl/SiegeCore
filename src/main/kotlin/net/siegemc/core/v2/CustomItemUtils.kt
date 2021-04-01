package net.siegemc.core.v2

import de.tr7zw.nbtapi.NBTItem
import net.siegemc.core.items.CustomItem
import net.siegemc.core.items.types.equipment.armor.CustomBoots
import net.siegemc.core.items.types.equipment.armor.CustomChestplate
import net.siegemc.core.items.types.equipment.armor.CustomHelmet
import net.siegemc.core.items.types.equipment.armor.CustomLeggings
import net.siegemc.core.v2.enums.StatTypes
import net.siegemc.core.v2.types.subtypes.CustomEquipment
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.lang.reflect.Constructor
import java.util.*
import kotlin.collections.HashMap


object CustomItemUtils {

    fun getCustomItem(item: ItemStack): CustomItem? {
        if (!item.hasItemMeta()) return null
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

    fun isCustomItemType(item: ItemStack, className: String): Boolean {
        val nbtItem = NBTItem(item)
        if (!nbtItem.hasKey("itemClass")) return false
        return className == nbtItem.getString("itemClass")
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

    fun getPlayerStat(player: Player, statType: StatTypes): Double {
        var output = 0.0
        val inventory = player.inventory

        getCustomItem(inventory.itemInMainHand)?.let {
            if (it is CustomWeapon && it.baseStats.containsKey(statType)) {
                output += it.baseStats[statType]!!
            }
        }

        inventory.helmet?.let { helmet ->
            getCustomItem(helmet)?.let {
                if (it is CustomHelmet && it.baseStats.containsKey(statType)) {
                    output += it.baseStats[statType]!!
                }
            }
        }

        inventory.chestplate?.let { chestplate ->
            getCustomItem(chestplate)?.let {
                if (it is CustomChestplate && it.baseStats.containsKey(statType)) {
                    output += it.baseStats[statType]!!
                }
            }
        }

        inventory.leggings?.let { leggings ->
            getCustomItem(leggings)?.let {
                if (it is CustomLeggings && it.baseStats.containsKey(statType)) {
                    output += it.baseStats[statType]!!
                }
            }
        }

        inventory.boots?.let { boots ->
            getCustomItem(boots)?.let {
                if (it is CustomBoots && it.baseStats.containsKey(statType)) {
                    output += it.baseStats[statType]!!
                }
            }
        }

        return output
    }

    fun getHealth(player: Player): Double {
        var healthStat: Double = getPlayerStat(player, StatTypes.HEALTH)
        return (player.health/player.maxHealth) * healthStat
    }
    fun getCurrentHealth(player: Player) : Double {
        return (getHealth(player) / getPlayerStat(player, StatTypes.HEALTH)) * player.maxHealth
    }

    fun getStats(item: CustomEquipment, addGem: Boolean, addRarity: Boolean): HashMap<StatTypes, Double> {
        val map = hashMapOf<StatTypes, Double>()
        StatTypes.values().forEach {
            var totalAmount = 0.0
            if (item.baseStats.containsKey(it)) {
                totalAmount += item.baseStats[it]!!
            }
            if (addGem) {
                item.statGem?.let { gem ->
                    if (gem.type == it) {
                        totalAmount += gem.amount
                    }
                }
            }
            if (addRarity) {
                totalAmount *= getRarityMultiplier(item.quality)
            }

            map[it] = totalAmount
        }
        return map
    }

    @JvmStatic
    fun getRarityMultiplier(quality: Int): Double = quality / 100 + 0.5

    fun serializeToItem(nbtItem: NBTItem, hashmap: HashMap<String, Any>) {
        hashmap.forEach {
            when (it.value) {
                is String -> nbtItem.setString(it.key, it.value as String)
                is Int -> nbtItem.setInteger(it.key, it.value as Int)
            }
        }
    }
}

fun ItemStack.setNbtTags(vararg pairs: Pair<String, Any?>): ItemStack {
    val tags = hashMapOf(*pairs)
    val nbtItem = NBTItem(this)
    tags.forEach { entry ->
        entry.value?.let {
            when (it) {
                // Numbers
                is Int -> nbtItem.setInteger(entry.key, it)
                is Long -> nbtItem.setLong(entry.key, it)
                is Short -> nbtItem.setShort(entry.key, it)
                is Double -> nbtItem.setDouble(entry.key, it)
                is Float -> nbtItem.setFloat(entry.key, it)
                is IntArray -> nbtItem.setIntArray(entry.key, it)
                // Bytes
                is Byte -> nbtItem.setByte(entry.key, it)
                is ByteArray -> nbtItem.setByteArray(entry.key, it)
                // Other Types
                is String -> nbtItem.setString(entry.key, it)
                is Boolean -> nbtItem.setBoolean(entry.key, it)
                // Useful Objects
                is ItemStack -> nbtItem.setItemStack(entry.key, it)
                is UUID -> nbtItem.setUUID(entry.key, it)
                // Leftovers
                else -> nbtItem.setObject(entry.key, it)
            }
        }

    }
    return nbtItem.item
}

fun ItemStack.getNbtTags(vararg pairs: Pair<String, NbtTypes>): HashMap<String, Any?> {
    val nbtItem = NBTItem(this)
    val output = hashMapOf<String, Any?>()
    pairs.forEach {
        val value: Any? = when (it.second) {
            NbtTypes.INT -> nbtItem.getInteger(it.first)
            NbtTypes.LONG -> nbtItem.getLong(it.first)
            NbtTypes.SHORT -> nbtItem.getShort(it.first)
            NbtTypes.DOUBLE -> nbtItem.getDouble(it.first)
            NbtTypes.FLOAT -> nbtItem.getFloat(it.first)
            NbtTypes.INTARRAY -> nbtItem.getIntArray(it.first)
            // Bytes
            NbtTypes.BYTE -> nbtItem.getByte(it.first)
            NbtTypes.BYTEARRAY -> nbtItem.getByteArray(it.first)
            // Other Types
            NbtTypes.STRING -> nbtItem.getString(it.first)
            NbtTypes.BOOLEAN -> nbtItem.getBoolean(it.first)
            // Useful Objects
            NbtTypes.ITEMSTACK -> nbtItem.getItemStack(it.first)
            NbtTypes.UUID -> nbtItem.getUUID(it.first)
        }
        output[it.first] = value
    }
    return output
}

enum class NbtTypes {
    INT,
    LONG,
    SHORT,
    DOUBLE,
    FLOAT,
    INTARRAY,
    BYTE,
    BYTEARRAY,
    STRING,
    BOOLEAN,
    ITEMSTACK,
    UUID
}