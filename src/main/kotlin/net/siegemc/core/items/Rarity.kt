package net.siegemc.core.items

enum class Rarity(val id: String, val color: String) {
    COMMON("Common", "<gray>"),
    UNCOMMON("Uncommon", "<green>"),
    RARE("Rare", "<blue>"),
    EPIC("Epic", "<dark_purple>"),
    LEGENDARY("Legendary", "<gold>"),
    SPECIAL("Special", "<rainbow>"),
    DEBUG("Debug", "<red>");

    companion object {
        fun getFromId(id: String?): Rarity {
            for (rarity in values()) {
                if (rarity.id.equals(id, ignoreCase = true)) return rarity
            }
            return COMMON
        }

        fun getFromInt(int: Int): Rarity {
            return when (int) {
                in 0..39 -> COMMON
                in 40..59 -> UNCOMMON
                in 60..79 -> RARE
                in 80..94 -> EPIC
                in 95..100 -> LEGENDARY
                in 101..150 -> SPECIAL
                else -> DEBUG
            }
        }
    }
}