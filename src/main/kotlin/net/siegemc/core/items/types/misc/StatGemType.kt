package net.siegemc.core.items.types.misc

import net.siegemc.core.items.CustomItem
import net.siegemc.core.items.enums.ItemTypes
import net.siegemc.core.items.enums.Rarity
import net.siegemc.core.items.enums.StatTypes
import net.siegemc.core.items.getNbtTag
import net.siegemc.core.items.recipes.CustomRecipeList
import net.siegemc.core.items.setNbtTags
import net.siegemc.core.utils.Utils
import org.bukkit.Material
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

abstract class StatGemType(
    override val name: String,
    override val customModelData: Int? = null,
    override val levelRequirement: Int? = null,
    override val description: List<String>,
    override val material: Material,
    override var quality: Int = -1,
    override var item: ItemStack = ItemStack(material),
    override val type: ItemTypes = ItemTypes.STATGEM,
    override val recipeList: CustomRecipeList? = null,
    val statType: StatTypes
) : CustomItem {

    override var rarity: Rarity = Rarity.COMMON

    open var statAmount: Double = 0.0
        set(value) {
            field = value
            this.serialize()
        }

    init {
        rarity = Rarity.getFromInt(quality)
    }

    override fun serialize() {
        super.serialize()
        item = item.setNbtTags(
            "statGemTypeStat" to statType,
            "statGemTypeAmount" to statAmount
        )
    }
    override fun deserialize() {
        super.deserialize()
        item.getNbtTag<Double>("statGemTypeAmount")?.let {
            statAmount = it
        }
    }

    override fun updateMeta(hideRarity: Boolean): ItemMeta {

        val meta = item.itemMeta

        meta.displayName(Utils.parse(if (rarity == Rarity.SPECIAL) "<rainbow>$name</rainbow>" else "${rarity.color}$name"))

        val newLore = mutableListOf(Utils.parse(if (rarity == Rarity.SPECIAL) "<rainbow>$rarity</rainbow> <gray>${if (hideRarity) 50 else quality}%" else "${rarity.color}$rarity <gray>$quality%"))
        newLore.add(Utils.parse(" "))
        newLore.add(Utils.parse("<color:#FF3CFF>+${statAmount} <light_purple>${statType.stylizedName} Gem"))
        newLore.add(Utils.parse(" "))
        description.forEach {
            newLore.add(Utils.parse("<dark_gray>$it"))
        }
        newLore.add(Utils.parse(" "))
        newLore.add(Utils.parse("<gray>Level: $levelRequirement"))
        if (hideRarity) newLore.add(Utils.parse("<red>This is not the real item"))
        meta.lore(newLore)

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE)
        item.itemMeta = meta
        return meta
    }

}