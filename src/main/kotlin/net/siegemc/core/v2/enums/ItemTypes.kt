package net.siegemc.core.v2

import net.siegemc.core.items.types.CustomFood
import net.siegemc.core.items.types.CustomMaterial
import net.siegemc.core.items.types.StatGemType
import net.siegemc.core.items.types.equipment.CustomWand
import net.siegemc.core.items.types.equipment.armor.*
import net.siegemc.core.items.types.equipment.weapons.CustomWeapon
import net.siegemc.core.v2.types.CustomMeleeWeapon
import kotlin.reflect.KClass

enum class ItemTypes(val stylizedName: String, val clazz: KClass<out CustomItem>) {
//    WEAPON("Weapon", CustomWeapon::class),
    MELEEWEAPON("Melee Weapon", CustomMeleeWeapon::class);//,
//    ARMOR("Armor", CustomArmor::class),
//    HELMET("Helmet", CustomHelmet::class),
//    CHESTPLATE("Chestplate", CustomChestplate::class),
//    LEGGINGS("Leggings", CustomLeggings::class),
//    BOOTS("Boots", CustomBoots::class),
//    WAND("Wand", CustomWand::class),
//    FOOD("Food", CustomFood::class),
//    STATGEM("Stat Gem", StatGemType::class),
//    MATERIAL("Material", CustomMaterial::class);

    companion object {
        fun getFromId(id: String?): ItemTypes? {
            for (itemType in values()) {
                if (itemType.stylizedName.equals(id, ignoreCase = true)) return itemType
            }
            return null
        }
    }
}