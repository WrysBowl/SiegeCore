package net.siegemc.core.items

class StatGem(val type: StatTypes, var amount: Double) {
    override fun toString(): String {
        return "$type|$amount"
    }
}