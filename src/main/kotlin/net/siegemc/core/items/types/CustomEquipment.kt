package net.siegemc.core.items.types

import net.siegemc.core.items.CustomItem
import net.siegemc.core.items.StatGem

abstract class CustomEquipment : CustomItem {
    abstract var statGem: StatGem?
}