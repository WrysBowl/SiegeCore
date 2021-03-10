package net.siegemc.core.items.implemented.equipment.wands

import de.tr7zw.nbtapi.NBTItem
import net.siegemc.core.items.Rarity
import net.siegemc.core.items.StatGem
import net.siegemc.core.items.StatTypes
import net.siegemc.core.items.types.equipment.CustomWand
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class TestWand(override var item: ItemStack, override val quality: Int) : CustomWand() {

    constructor(quality: Int) : this(ItemStack(Material.STICK), quality)

    // default item properties
    override val name: String = "Test Wand"
    override var rarity: Rarity = Rarity.DEBUG
    override val levelRequirement: Int = 0
    override val material: Material = Material.STICK
    override val description: List<String> = listOf("A test wand!")

    // equipment properties
    override var statGem: StatGem? = null
    override val baseStats: HashMap<StatTypes, Double> = hashMapOf(StatTypes.STRENGTH to 50.0)

    // wand properties
    override val range: Int = 15
    override val red: Int = 100
    override val green: Int = 100
    override val blue: Int = 100
    override val damageRadius: Double = 3.5
    override val manaRequired: Int = 2

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
            nbtItem.setString("itemRarity", rarity.toString())
            nbtItem.setInteger("itemLevelRequirement", levelRequirement)
            nbtItem.setInteger("wandRange", range)
            nbtItem.setInteger("wandRed", red)
            nbtItem.setInteger("wandGreen", green)
            nbtItem.setInteger("wandBlue", blue)
            nbtItem.setDouble("wandDamageRadius", damageRadius)
            nbtItem.setInteger("wandManaRequired", manaRequired)
            nbtItem.setString("itemClass", "net.siegemc.core.items.implemented.equipment.wands.TestWand")


        }

        item = nbtItem.item

        updateMeta()

    }
}