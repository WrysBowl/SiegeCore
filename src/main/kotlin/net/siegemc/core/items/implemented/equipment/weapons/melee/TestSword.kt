package net.siegemc.core.items.implemented.equipment.weapons.melee

import de.tr7zw.nbtapi.NBTItem
import net.siegemc.core.items.Rarity
import net.siegemc.core.items.StatGem
import net.siegemc.core.items.StatTypes
import net.siegemc.core.items.types.equipment.weapons.CustomMeleeWeapon
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.inventory.ItemStack


class TestSword(override var item: ItemStack, override val quality: Int) : CustomMeleeWeapon() {

    constructor(quality: Int) : this(ItemStack(Material.DIAMOND_SWORD), quality)

    // default item properties
    override val name: String = "Test Sword"
    override var rarity: Rarity = Rarity.DEBUG
    override val levelRequirement: Int = 0
    override val material: Material = Material.DIAMOND_SWORD
    override val description: List<String> = listOf("A test sword!")

    // equipment properties
    override var statGem: StatGem? = null
    override val baseStats: HashMap<StatTypes, Double> = hashMapOf(StatTypes.STRENGTH to 10.0)

    // weapon properties
    override val damage: Double
        get() = myProp * 2.0
    override val attackSpeed: Double = 1.7

    // item specific properties
    private var myProp: Int = 10
        set(value) {
            field = value
            val nbtItem = NBTItem(item)
            if (!nbtItem.hasKey("testSwordMyProp") || nbtItem.getInteger("testSwordMyProp")!! != myProp) nbtItem.setInteger(
                "testSwordMyProp",
                myProp
            )
            // also set the weapon damage because it depends on myProp
            if (!nbtItem.hasKey("weaponDamage") || nbtItem.getDouble("weaponDamage")!! != damage) nbtItem.setDouble(
                "weaponDamage",
                damage
            )
            this.item = nbtItem.item
        }


    override fun onHit(e: EntityDamageByEntityEvent) {
        if (e.damager is Player) {
            (e.damager as Player).sendTitle("Custom Item!", null, 1, 5, 1)
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
            if (nbtItem.hasKey("testSwordMyProp")) myProp = nbtItem.getInteger("testSwordMyProp")

            // note that we don't fix weapon damage because when we set myProp it also updates damage
        } else {
            // create new item

            // setting default item properties
            nbtItem.setBoolean("customItem", true)
            nbtItem.setString("itemName", name)
            nbtItem.setString("itemType", type.stylizedName)
            nbtItem.setString("itemRarity", rarity.toString())
            nbtItem.setInteger("itemLevelRequirement", levelRequirement)
            nbtItem.setString("itemClass", this::class.qualifiedName)

            nbtItem.setString("equipmentStatGem", if (statGem != null) "${statGem!!.type}|${statGem!!.amount}" else "false")

            // setting item specific properties (done first because damage depends on myProp)
            if (nbtItem.hasKey("testSwordMyProp")) myProp = nbtItem.getInteger("testSwordMyProp")
            else {
                // setting item and weapon specific properties (we don't have to do this if we set myProp)
                nbtItem.setInteger("testSwordMyProp", myProp)
                nbtItem.setDouble("weaponDamage", damage)
            }




        }

        item = nbtItem.item

        updateMeta()

    }
}