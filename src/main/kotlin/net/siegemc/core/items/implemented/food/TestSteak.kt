package net.siegemc.core.items.implemented.food

import de.tr7zw.nbtapi.NBTItem
import net.siegemc.core.items.Rarity
import net.siegemc.core.items.types.CustomFood
import org.bukkit.Material
import org.bukkit.event.player.PlayerItemConsumeEvent
import org.bukkit.inventory.ItemStack

class TestSteak(override var item: ItemStack, override val quality: Int) : CustomFood() {

    constructor(quality: Int) : this(ItemStack(Material.COOKED_BEEF), quality)

    // default item properties
    override val name: String = "Test Steak"
    override var rarity: Rarity = Rarity.DEBUG
    override val levelRequirement: Int = 0
    override val material: Material = Material.COOKED_BEEF

    // food properties
    override val hunger: Int = 5
    override val health: Int = 2

    override fun onEat(e: PlayerItemConsumeEvent) {
        super.onEat(e)
        e.player.sendTitle("You ate food", null, 1, 5, 1)
    }


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


        }

        item = nbtItem.item

        updateMeta()

    }
}