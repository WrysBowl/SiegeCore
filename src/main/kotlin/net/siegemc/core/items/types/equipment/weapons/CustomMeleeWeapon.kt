package net.siegemc.core.items.types.equipment.weapons

import net.siegemc.core.items.CustomItemUtils
import net.siegemc.core.items.StatTypes
import net.siegemc.core.items.recipes.CustomRecipe
import net.siegemc.core.items.types.ItemTypes
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.inventory.meta.ItemMeta

// Up here is the primary constructor, where most of the arguments for the type are listed
abstract class CustomMeleeWeapon(
    final override val name: String,
    final override val customModelData: Int,
    final override val description: List<String>,
    final override val levelRequirement: Int,
    final override val material: Material,
    final override val recipe: CustomRecipe?,
    override val baseStats: HashMap<StatTypes, Double>,
    //override val damage: Double,
    val attackSpeed: Double
) : CustomWeapon() {
    override val type: ItemTypes = ItemTypes.WEAPON

    // This is an event that can be called by a listener on a custom item. If the custom weapon extends the function, it
    // will run. If not, it will run this event that does literally nothing.
    // This is open and not abstract so that all items don't have to extend it.
    open fun onHit(e: EntityDamageByEntityEvent) {
        //Need to register crit chance from "Luck"
        //Register sounds, particles
        val damage = CustomItemUtils.getPlayerStat(e.damager as Player, StatTypes.STRENGTH)
        e.damage = damage
    }

    // This function just updates the lore of the item with all the necessary information.
    // Here we extend and modify the attribute modifier
    override fun updateMeta(hideRarity: Boolean): ItemMeta {
        val meta = super.updateMeta(hideRarity)
        meta.removeAttributeModifier(Attribute.GENERIC_ATTACK_SPEED)
        meta.addAttributeModifier(
            Attribute.GENERIC_ATTACK_SPEED,
            AttributeModifier("Attack Speed", attackSpeed, AttributeModifier.Operation.ADD_NUMBER)
        )
        item.itemMeta = meta
        return meta
    }

    init {
        recipe?.let {
            this::class.qualifiedName?.let { it1 -> CustomRecipe.registerRecipe(it, it1) }
        }
    }

}