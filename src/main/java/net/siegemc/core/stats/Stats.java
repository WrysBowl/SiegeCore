package net.siegemc.core.stats;

import com.archyx.aureliumskills.api.AureliumAPI;
import net.siegemc.core.Core;
import net.siegemc.core.items.BowItem;
import net.siegemc.core.items.CustomItem;
import net.siegemc.core.items.CustomWeapon;
import net.siegemc.core.items.Stat;
import net.siegemc.core.utils.NBT;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Stats {
    public static double getWepStat(ItemStack itemWep, String statType) {
        ItemMeta metaWep = itemWep.getItemMeta();

        //Check if the item has nbt tags
        if (metaWep == null) { return 0; }

        //Check the nbt 'item' of the weapon
        CustomItem customItem = Core.getItems().get(NBT.getString(metaWep, "item"));

        //Check if item is a weapon
        if (!(customItem instanceof CustomWeapon)) { return 0; }

        int perfectQuality = NBT.getInt(metaWep, "perfectQuality");
        CustomWeapon weapon = (CustomWeapon) customItem;
        double wepStrength = weapon.getStats().getOrDefault(Stat.getFromID(statType), 0);
        return CustomItem.calculateStatValue((int) Math.floor(wepStrength), perfectQuality);
    }



    public static double getStrength(Player p) {
        double strength = 0;
        ItemStack itemWep = p.getInventory().getItemInMainHand();
        strength += getWepStat(itemWep, "STRENGTH");

        //Calculates player's skill level for Strength
        strength += AureliumAPI.getStatLevel(p, com.archyx.aureliumskills.stats.Stat.STRENGTH);

        //Calculates strength of player's armor
        ItemStack itemHelm = p.getInventory().getHelmet();
        if (itemHelm!=null) {
            ItemMeta metaHelm = itemHelm.getItemMeta();
            CustomItem helmet = Core.getItems().get(NBT.getString(metaHelm, "item"));
            if (helmet instanceof CustomItem) {
                int perfectQualityHelm = NBT.getInt(metaHelm, "perfectQuality"); //get percentage
                double helmStrength = helmet.getStats().getOrDefault(Stat.STRENGTH, 0); //get strength NBT
                strength += CustomItem.calculateStatValue((int) Math.floor(helmStrength), perfectQualityHelm);
            }
        }

        ItemStack itemChest = p.getInventory().getChestplate();
        if (itemChest!=null) {
            ItemMeta metaChest = itemChest.getItemMeta();
            CustomItem chestplate = Core.getItems().get(NBT.getString(metaChest, "item"));
            if (chestplate instanceof CustomItem) {
                int perfectQualityChest = NBT.getInt(metaChest, "perfectQuality");
                double chestStrength = chestplate.getStats().getOrDefault(Stat.STRENGTH, 0);
                strength += CustomItem.calculateStatValue((int) Math.floor(chestStrength), perfectQualityChest);
            }
        }

        ItemStack itemLeg = p.getInventory().getLeggings();
        if (itemLeg!=null) {
            ItemMeta metaLeg = itemLeg.getItemMeta();
            CustomItem leggings = Core.getItems().get(NBT.getString(metaLeg, "item"));
            if (leggings instanceof CustomItem) {
                int perfectQualityLeg = NBT.getInt(metaLeg, "perfectQuality");
                double legStrength = leggings.getStats().getOrDefault(Stat.STRENGTH, 0);
                strength += CustomItem.calculateStatValue((int) Math.floor(legStrength), perfectQualityLeg);
            }
        }

        ItemStack itemBoots = p.getInventory().getBoots();
        if (itemBoots!=null) {
            ItemMeta metaBoots = itemBoots.getItemMeta();
            CustomItem boots = Core.getItems().get(NBT.getString(metaBoots, "item"));
            if (boots instanceof CustomItem) {
                int perfectQualityBoots = NBT.getInt(metaBoots, "perfectQuality");
                double bootsStrength = boots.getStats().getOrDefault(Stat.STRENGTH, 0);
                strength += CustomItem.calculateStatValue((int) Math.floor(bootsStrength), perfectQualityBoots);
            }
        }
        return strength;
    }
}
