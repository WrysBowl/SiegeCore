package net.siegemc.core.items.recipes

import net.siegemc.core.items.CustomItem
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.lang.reflect.Constructor
import kotlin.reflect.KClass

class CustomRecipe(val items: List<CustomItem?>, private val shaped: Boolean = true, val createItem: (Player, Boolean) -> CustomItem) {

    fun matches(matrix: List<CustomItem?>): Boolean {
        return if (shaped) {
            matrix == items
        } else {
            matrix.containsAll(items)
        }
    }

    companion object {
        // This string is the qualified class name of the custom item, so that it can be easily initialized.
        var recipes: HashMap<CustomRecipe, String> = hashMapOf()

        fun registerRecipe(recipe: CustomRecipe, className: String) {
            recipes[recipe] = className
        }
    }

}
