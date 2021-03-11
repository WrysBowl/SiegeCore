package net.siegemc.core.items.implemented.statgems

import de.tr7zw.nbtapi.NBTItem
import net.siegemc.core.items.Rarity
import net.siegemc.core.items.StatTypes
import net.siegemc.core.items.types.StatGemItem
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class StrengthGem(override var item: ItemStack, override val quality: Int, override var statAmount: Double) : StatGemItem() {

    constructor(quality: Int, statAmount: Double) : this(ItemStack(Material.POPPED_CHORUS_FRUIT), quality, statAmount)

    constructor(item: ItemStack) {
        val nbtItem = NBTItem(item)
        val nbtQuality = nbtItem.getString("itemQuality")
        val nbtType = nbtItem.getString("amou")
        val newQuality = if (nbtItem.hasKey(""))
        this()
    }

    // default item properties
    override val name: String = "Strength Gem"
    override var rarity: Rarity = Rarity.DEBUG
    override val levelRequirement: Int = 0
    override val material: Material = Material.POPPED_CHORUS_FRUIT

    // food properties
    override val statType = StatTypes.STRENGTH
//    override var statAmount: Double = 0.0



    init {
        rarity = Rarity.getFromInt(quality)
        val nbtItem = NBTItem(item)

        // this is already a custom item and not a new item, so set our custom test props and fix the default properties
        if (nbtItem.hasKey("customItem")) {
            // fix default properties
            if (!nbtItem.hasKey("itemName") || nbtItem.getString("itemName") != name) nbtItem.setString(
                "itemName",
                name
            )
            if (!nbtItem.hasKey("itemRarity") || Rarity.valueOf(nbtItem.getString("itemRarity")) != rarity) nbtItem.setString(
                "itemRarity",
                rarity.toString()
            )
            if (!nbtItem.hasKey("itemLevelRequirement") || nbtItem.getInteger("itemLevelRequirement") != levelRequirement) nbtItem.setInteger(
                "itemLevelRequirement",
                levelRequirement
            )


            // get our item specific properties

            // note that we don't fix weapon damage because when we set myProp it also updates damage
        } else {
            // create new item

            // setting default item properties
            nbtItem.setBoolean("customItem", true)
            nbtItem.setString("itemName", name)
            nbtItem.setString("itemType", type.stylizedName)
            nbtItem.setInteger("itemQuality", quality)
            nbtItem.setString("itemRarity", rarity.toString())
            nbtItem.setInteger("itemLevelRequirement", levelRequirement)
            nbtItem.setString("itemClass", this::class.qualifiedName)

            nbtItem.setString("statGemType", statType.stylizedName)
            nbtItem.setDouble("statGemAmount", statAmount)


        }

        item = nbtItem.item

        updateMeta()

    }
}