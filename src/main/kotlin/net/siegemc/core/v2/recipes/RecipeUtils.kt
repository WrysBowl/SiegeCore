package net.siegemc.core.v2.recipes

import net.siegemc.core.items.recipes.CustomRecipe
import net.siegemc.core.v2.implemented.TestSword


fun recipes(init: CustomRecipeList.() -> Unit): CustomRecipeList {
    val customRecipeList = CustomRecipeList()
    customRecipeList.init()
    return customRecipeList
}

fun test() {
    recipes {
        recipe {
            shaped = false
            s1(TestSword(25))
            s2(TestSword(25))
            item { player, b ->
                TestSword(50)
            }
        }
        recipe {
            shaped = true
            s3(TestSword(50))
            s4(TestSword(50))
            item { player, b ->
                TestSword(100)
            }
        }
    }

}

class CustomRecipeList() {
    var recipeList = mutableListOf<CustomRecipe>()
    fun recipe(init: CustomRecipe.() -> Unit): CustomRecipe {
        val customRecipe = CustomRecipe()
        customRecipe.init()
        recipeList.add(customRecipe)
        return customRecipe
    }
}