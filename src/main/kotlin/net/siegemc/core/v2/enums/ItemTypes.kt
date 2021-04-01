package net.siegemc.core.v2.enums

import net.siegemc.core.v2.CustomItem
import net.siegemc.core.v2.types.CustomFood
import net.siegemc.core.v2.types.CustomMaterial
import net.siegemc.core.v2.types.CustomMeleeWeapon
import kotlin.reflect.KClass

enum class ItemTypes(val stylizedName: String, val clazz: KClass<out CustomItem>) {
//    WEAPON("Weapon", CustomWeapon::class),
    MELEEWEAPON("Melee Weapon", CustomMeleeWeapon::class),
//    ARMOR("Armor", CustomArmor::class),
//    HELMET("Helmet", CustomHelmet::class),
//    CHESTPLATE("Chestplate", CustomChestplate::class),
//    LEGGINGS("Leggings", CustomLeggings::class),
//    BOOTS("Boots", CustomBoots::class),
//    WAND("Wand", CustomWand::class),
    FOOD("Food", CustomFood::class),
//    STATGEM("Stat Gem", StatGemType::class),
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