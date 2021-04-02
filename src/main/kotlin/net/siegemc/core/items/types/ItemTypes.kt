package net.siegemc.core.items.types

import net.siegemc.core.items.CustomItem
import net.siegemc.core.items.types.armor.CustomBoots
import net.siegemc.core.items.types.armor.CustomChestplate
import net.siegemc.core.items.types.armor.CustomHelmet
import net.siegemc.core.items.types.armor.CustomLeggings
import net.siegemc.core.items.types.misc.CustomFood
import net.siegemc.core.items.types.misc.CustomMaterial
import net.siegemc.core.items.types.misc.CustomWand
import net.siegemc.core.items.types.misc.StatGemType
import net.siegemc.core.items.types.subtypes.CustomArmor
import net.siegemc.core.items.types.subtypes.CustomWeapon
import kotlin.reflect.KClass

enum class ItemTypes(val stylizedName: String, val clazz: KClass<out CustomItem>) {
    WEAPON("Weapon", CustomWeapon::class),
    ARMOR("Armor", CustomArmor::class),
    HELMET("Helmet", CustomHelmet::class),
    CHESTPLATE("Chestplate", CustomChestplate::class),
    LEGGINGS("Leggings", CustomLeggings::class),
    BOOTS("Boots", CustomBoots::class),
    WAND("Wand", CustomWand::class),
    FOOD("Food", CustomFood::class),
    STATGEM("Stat Gem", StatGemType::class),
    MATERIAL("Material", CustomMaterial::class);

    companion object {
        fun getFromId(id: String?): ItemTypes? {
            for (itemType in values()) {
                if (itemType.stylizedName.equals(id, ignoreCase = true)) return itemType
            }
            return null
        }
    }
}