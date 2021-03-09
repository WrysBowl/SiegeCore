package net.siegemc.core.items.types

import net.siegemc.core.items.CustomItem
import kotlin.reflect.KClass

enum class ItemTypes(val stylizedName: String, val clazz: KClass<out CustomItem>) {
    WEAPON("Weapon", CustomWeapon::class)
}