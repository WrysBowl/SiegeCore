package net.siegemc.core.items;

import com.archyx.aureliumskills.api.AureliumAPI;
import lombok.Getter;
import lombok.Setter;
import net.siegemc.core.Core;
import net.siegemc.core.utils.NBT;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class CustomItem {
    @Getter @Setter private ItemStack rawItem;
    @Getter @Setter private List<String> description = Collections.singletonList("No description found for this item!");
    @Getter @Setter private Rarity rarity = Rarity.COMMON;
    @Getter @Setter private int levelRequirement = 0;
    @Getter private final HashMap<Stat, Integer> stats = new HashMap<>();
    @Getter final String itemName;
    
    /**
     * @param itemName The name of the item
     * @param rawItem The item the custom item will be
     */
    public CustomItem(String itemName, ItemStack rawItem) {
        this.itemName = itemName;
        setRawItem(rawItem);
    }
    
    /**
     * Returns the CustomItem that extends the CustomItem class
     *
     * @param item The itemstack
     * @return The custom item or null if it is a regular item
     */
    public static CustomItem getType(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return null;
    
        String key = NBT.getString(meta, "item");
    
        return Core.getItems().get(key);
    }
    
    public ItemStack get() {
        return get((int) Math.round(Math.random() * 100 + 1));
    }
    
    public ItemStack get(int perfectQuality) {
        ItemStack item = rawItem;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return null;
        
        String color = "§" + this.getRarity().getColor();
        meta.setDisplayName(color+this.getItemName());
        
        List<String> lore = new ArrayList<>();
        lore.add(color+this.getRarity().getID()+" §7"+perfectQuality+"%");
        lore.add(" ");
        for (Stat stat : stats.keySet()) {
            double value = calculateStatValue(stats.get(stat), perfectQuality);
            String line = "§a+ ";
            line += stat.getType() == StatType.ADDITION ? ""+value : value+"%";
            line += " §7"+stat.getID();
            lore.add(line);
        }
        lore.add(" ");
        for (String desc : this.getDescription()) lore.add("§8§o"+desc);
        lore.add(" ");
        lore.add("§7Level: "+this.getLevelRequirement());
        meta.setLore(lore);
        
        // Handle NBT
        NBT.addString(meta, "item", itemName);
        NBT.addInt(meta, "perfectQuality", perfectQuality);
    
        item.setItemMeta(meta);
        return item;
    }
    
    @Override
    public String toString() {
        return this.getRarity().getID()+" "+this.getItemName();
    }
    
    /**
     * Used to calculate the exact value for a stat accounting for perfect quality
     *
     * @param value Initial value for the stat
     * @param perfectQuality The perfect quality value
     * @return Exact value for the stat
     */
    public static double calculateStatValue(int value, int perfectQuality) {
        double v = value * ((double) perfectQuality / 100 + 0.5);
        v = Math.round(v * 10.0) / 10.0;
        return v;
    }
    
    /**
     * Gets the damage dealt from a custom item.
     *
     * @param player The player dealing the damage
     * @param victim The entity getting attacked
     * @param item The item used
     * @return The value that needs to be dealt or 0 if the item is not a custom item.
     */
    public static double getDamage(Player player, LivingEntity victim, ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return 0;
    
        String key = NBT.getString(meta, "item");
        int perfectQuality = NBT.getInt(meta, "perfectQuality");
    
        CustomItem customItem = Core.getItems().get(key);
        if (customItem == null) return 0;
    
        if (customItem instanceof CustomWeapon && !(customItem instanceof BowItem)) {
            CustomWeapon weapon = (CustomWeapon) customItem;
            double strength = weapon.getStats().getOrDefault(Stat.STRENGTH, 0);
            strength += AureliumAPI.getStatLevel(player, com.archyx.aureliumskills.stats.Stat.STRENGTH);
            double add = CustomItem.calculateStatValue((int) Math.floor(strength), perfectQuality) * 0.25;
            return weapon.getDamage() + add + victim.getHealth() * 0.1;
        }
        return 0;
    }
}

