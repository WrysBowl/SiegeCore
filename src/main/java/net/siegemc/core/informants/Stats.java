package net.siegemc.core.informants;

import com.archyx.aureliumskills.api.AureliumAPI;
import net.siegemc.core.Core;
import net.siegemc.core.items.CreateItems.CustomItem;
import net.siegemc.core.items.CreateItems.CustomWeapon;
import net.siegemc.core.items.CreateItems.Stat;
import net.siegemc.core.utils.NBT;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Stats {
    public static double getWepStat(ItemStack itemWep, String statType) {
        if (itemWep == null) { return 0; }

        //Check if the item has nbt tags
        ItemMeta metaWep = itemWep.getItemMeta();
        if (metaWep == null) { return 0; }

        //Check the nbt 'item' of the weapon
        CustomItem customItem = Core.getItems().get(NBT.getString(metaWep, "item"));
        if (!(customItem instanceof CustomWeapon)) { return 0; }

        int perfectQuality = NBT.getInt(metaWep, "perfectQuality");
        CustomWeapon weapon = (CustomWeapon) customItem;
        double wepStrength = weapon.getStats().getOrDefault(Stat.getFromID(statType), 0);
        return CustomItem.calculateStatValue((int) Math.floor(wepStrength), perfectQuality);
    }

    public static double getHelmStat(ItemStack itemHelm, String statType) {
        if (itemHelm == null) { return 0; }

        ItemMeta metaWep = itemHelm.getItemMeta();
        if (metaWep == null) { return 0; }

        //Check the nbt 'item' of the weapon
        CustomItem helmet = Core.getItems().get(NBT.getString(metaWep, "item"));
        if (helmet == null) { return 0; }

        int perfectQuality = NBT.getInt(metaWep, "perfectQuality");
        double helmStat = helmet.getStats().getOrDefault(Stat.getFromID(statType), 0);
        return CustomItem.calculateStatValue((int) Math.floor(helmStat), perfectQuality);
    }

    public static double getChestStat(ItemStack itemChest, String statType) {
        if (itemChest == null) { return 0; }

        ItemMeta metaWep = itemChest.getItemMeta();
        if (metaWep == null) { return 0; }

        //Check the nbt 'item' of the weapon
        CustomItem chestplate = Core.getItems().get(NBT.getString(metaWep, "item"));
        if (chestplate == null) { return 0; }

        int perfectQuality = NBT.getInt(metaWep, "perfectQuality");
        double chestStat = chestplate.getStats().getOrDefault(Stat.getFromID(statType), 0);
        return CustomItem.calculateStatValue((int) Math.floor(chestStat), perfectQuality);
    }

    public static double getLegStat(ItemStack itemLegs, String statType) {
        if (itemLegs == null) { return 0; }

        ItemMeta metaWep = itemLegs.getItemMeta();
        if (metaWep == null) { return 0; }

        //Check the nbt 'item' of the weapon
        CustomItem leggings = Core.getItems().get(NBT.getString(metaWep, "item"));
        if (leggings == null) { return 0; }

        int perfectQuality = NBT.getInt(metaWep, "perfectQuality");
        double legStat = leggings.getStats().getOrDefault(Stat.getFromID(statType), 0);
        return CustomItem.calculateStatValue((int) Math.floor(legStat), perfectQuality);
    }

    public static double getBootStat(ItemStack itemBoots, String statType) {
        if (itemBoots == null) { return 0; }

        ItemMeta metaWep = itemBoots.getItemMeta();
        if (metaWep == null) { return 0; }

        //Check the nbt 'item' of the weapon
        CustomItem boots = Core.getItems().get(NBT.getString(metaWep, "item"));
        if (boots == null) { return 0; }

        int perfectQuality = NBT.getInt(metaWep, "perfectQuality");
        double bootStat = boots.getStats().getOrDefault(Stat.getFromID(statType), 0);
        return CustomItem.calculateStatValue((int) Math.floor(bootStat), perfectQuality);
    }

    public static double getStrength(Player p) {
        double strength = 0;
        strength += getWepStat(p.getInventory().getItemInMainHand(), "STRENGTH");
        strength += getHelmStat(p.getInventory().getHelmet(), "STRENGTH");
        strength += getChestStat(p.getInventory().getChestplate(), "STRENGTH");
        strength += getLegStat(p.getInventory().getLeggings(), "STRENGTH");
        strength += getBootStat(p.getInventory().getBoots(), "STRENGTH");
        strength += AureliumAPI.getStatLevel(p, com.archyx.aureliumskills.stats.Stat.STRENGTH);
        return strength;
    }

    public static double getHealth(Player p) {
        double health = 0;
        health += getWepStat(p.getInventory().getItemInMainHand(), "HEALTH");
        health += getHelmStat(p.getInventory().getHelmet(), "HEALTH");
        health += getChestStat(p.getInventory().getChestplate(), "HEALTH");
        health += getLegStat(p.getInventory().getLeggings(), "HEALTH");
        health += getBootStat(p.getInventory().getBoots(), "HEALTH");
        health += AureliumAPI.getStatLevel(p, com.archyx.aureliumskills.stats.Stat.HEALTH);
        return health;
    }

    public static double getRegeneration(Player p) {
        double regen = 0;
        regen += getWepStat(p.getInventory().getItemInMainHand(), "REGENERATION");
        regen += getHelmStat(p.getInventory().getHelmet(), "REGENERATION");
        regen += getChestStat(p.getInventory().getChestplate(), "REGENERATION");
        regen += getLegStat(p.getInventory().getLeggings(), "REGENERATION");
        regen += getBootStat(p.getInventory().getBoots(), "REGENERATION");
        regen += AureliumAPI.getStatLevel(p, com.archyx.aureliumskills.stats.Stat.REGENERATION);
        return regen;
    }

    public static double getLuck(Player p) {
        double luck = 0;
        luck += getWepStat(p.getInventory().getItemInMainHand(), "LUCK");
        luck += getHelmStat(p.getInventory().getHelmet(), "LUCK");
        luck += getChestStat(p.getInventory().getChestplate(), "LUCK");
        luck += getLegStat(p.getInventory().getLeggings(), "LUCK");
        luck += getBootStat(p.getInventory().getBoots(), "LUCK");
        luck += AureliumAPI.getStatLevel(p, com.archyx.aureliumskills.stats.Stat.LUCK);
        return luck;
    }

    public static double getWisdom(Player p) {
        double wisdom = 0;
        wisdom += getWepStat(p.getInventory().getItemInMainHand(), "WISDOM");
        wisdom += getHelmStat(p.getInventory().getHelmet(), "WISDOM");
        wisdom += getChestStat(p.getInventory().getChestplate(), "WISDOM");
        wisdom += getLegStat(p.getInventory().getLeggings(), "WISDOM");
        wisdom += getBootStat(p.getInventory().getBoots(), "WISDOM");
        wisdom += AureliumAPI.getStatLevel(p, com.archyx.aureliumskills.stats.Stat.WISDOM);
        return wisdom;
    }

    public static double getToughness(Player p) {
        double toughness = 0;
        toughness += getWepStat(p.getInventory().getItemInMainHand(), "TOUGHNESS");
        toughness += getHelmStat(p.getInventory().getHelmet(), "TOUGHNESS");
        toughness += getChestStat(p.getInventory().getChestplate(), "TOUGHNESS");
        toughness += getLegStat(p.getInventory().getLeggings(), "TOUGHNESS");
        toughness += getBootStat(p.getInventory().getBoots(), "TOUGHNESS");
        toughness += AureliumAPI.getStatLevel(p, com.archyx.aureliumskills.stats.Stat.TOUGHNESS);
        return toughness;
    }
}
