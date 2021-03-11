package net.siegemc.core.items.types.equipment.weapons

import net.siegemc.core.items.Rarity
import net.siegemc.core.items.types.ItemTypes
import net.siegemc.core.utils.Utils
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.inventory.ItemFlag

abstract class CustomMeleeWeapon : CustomWeapon() {
    override val type: ItemTypes = ItemTypes.WEAPON
    override val description: List<String> = listOf("A powerful melee weapon!")

    abstract val attackSpeed: Double

    open fun onHit(e: EntityDamageByEntityEvent) {
        // does nothing lol
    }

    override fun updateMeta() {
        val meta = item.itemMeta ?: return

        meta.displayName(Utils.parse(if (rarity == Rarity.SPECIAL) "<rainbow>$name</rainbow>" else "${rarity.color}$name"))

        val newLore = mutableListOf(Utils.parse(if (rarity == Rarity.SPECIAL) "<rainbow>$rarity</rainbow> <gray>$quality%" else "${rarity.color}$rarity <gray>$quality%"))
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
        meta.lore(newLore)

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE)
        meta.removeAttributeModifier(Attribute.GENERIC_ATTACK_SPEED)
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, AttributeModifier("Attack Speed", attackSpeed, AttributeModifier.Operation.ADD_NUMBER))
        item.itemMeta = meta
    }

}