package net.siegemc.core.items

import de.tr7zw.nbtapi.NBTItem
import net.siegemc.core.items.implemented.equipment.weapons.melee.TestSword
import net.siegemc.core.items.recipes.CustomRecipe
import net.siegemc.core.items.types.equipment.armor.CustomBoots
import net.siegemc.core.items.types.equipment.armor.CustomChestplate
import net.siegemc.core.items.types.equipment.armor.CustomHelmet
import net.siegemc.core.items.types.equipment.armor.CustomLeggings
import net.siegemc.core.items.types.equipment.weapons.CustomWeapon
import org.bukkit.entity.Item
import org.bukkit.entity.Player
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
        return (getHealth(player)/getPlayerStat(player, StatTypes.HEALTH)) * player.maxHealth
    }
}