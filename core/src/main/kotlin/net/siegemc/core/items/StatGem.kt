package net.siegemc.core.items

import net.siegemc.core.items.enums.StatTypes

class StatGem(val type: StatTypes, var amount: Double) {
    override fun toString(): String {
        return "$type|$amount"
    }

    companion object {
        fun fromString(string: String): StatGem {
            val stringArr = string.split("|")
            return StatGem(StatTypes.valueOf(stringArr[0]), stringArr[1].toDouble())
        }
    }

}