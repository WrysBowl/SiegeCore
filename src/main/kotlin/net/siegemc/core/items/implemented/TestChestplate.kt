package net.siegemc.core.items.implemented

import de.tr7zw.nbtapi.NBTItem
import net.siegemc.core.items.Rarity
import net.siegemc.core.items.StatGem
import net.siegemc.core.items.StatTypes
import net.siegemc.core.items.types.CustomChestplate
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.inventory.ItemStack

class TestChestplate(override var item: ItemStack, override val quality: Int) : CustomChestplate() {

    constructor(quality: Int) : this(ItemStack(Material.DIAMOND_CHESTPLATE), quality)

    // default item properties
    override val name: String = "Test Chestplate"
    override var rarity: Rarity = Rarity.DEBUG
    override val levelRequirement: Int = 0
    override val material: Material = Material.DIAMOND_CHESTPLATE
    override val description: List<String> = listOf("A test chestplate!")

    // equipment properties
    override var statGem: StatGem? = null

    // armor properties

    override fun onHit(e: EntityDamageByEntityEvent) {
        if (e.entity is Player) {
            (e.entity as Player).sendTitle("Chestplate Damage", null, 1, 5, 1)
        }
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

            // get stat gem
            if (nbtItem.hasKey("itemStatGem") || nbtItem.getString("itemStatGem") !== "false") {
                val statGemStr = nbtItem.getString("itemStatGem")
                val statGemArr = statGemStr.split('|')
                try {
                    val statGemType = StatTypes.valueOf(statGemArr[0])
                    val statGemAmount = statGemArr[1].toDoubleOrNull()
                    statGemAmount?.let {
                        val newStatGem = StatGem(statGemType, it)
                        statGem = newStatGem
                    }
                } catch (e: Exception) {
                    nbtItem.setString("itemStatGem", "false")
                }
            }

            // get our item specific properties

            // note that we don't fix weapon damage because when we set myProp it also updates damage
        } else {
            // create new item

            // setting default item properties
            nbtItem.setBoolean("customItem", true)
            nbtItem.setString("itemName", name)
            nbtItem.setString("itemRarity", rarity.toString())
            nbtItem.setInteger("itemLevelRequirement", levelRequirement)
            nbtItem.setString("itemClass", "net.siegemc.core.items.implemented.TestChestplate")


        }

        item = nbtItem.item

    }
}