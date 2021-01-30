package net.siegemc.core.items;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CustomItem {
    @Getter @Setter private ItemStack rawItem;
    @Getter @Setter private String description = "No description found for this item!";
    @Getter @Setter private Rarity rarity = Rarity.COMMON;
    @Getter @Setter private int levelRequirement = 0;
    @Getter private final String itemName;
    
    public CustomItem(String itemName, ItemStack rawItem) {
        this.itemName = itemName;
        setRawItem(rawItem);
    }
    
    public ItemStack get() {
        ItemStack item = rawItem;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return null;
        
        String color = "§" + this.getRarity().getColor();
        meta.setDisplayName(color+this.getItemName());
        
        List<String> lore = new ArrayList<>();
        lore.add(color+"§l"+this.getRarity().name().toUpperCase());
        lore.add(" ");
        // TODO: Add stats to lore
        lore.add(" ");
        lore.add("§8§o" + this.getDescription());
        lore.add(" ");
        lore.add("§8Requires level "+this.getLevelRequirement());
        meta.setLore(lore);
        
        item.setItemMeta(meta);
        return item;
    }
    
    @Override
    public String toString() {
        return this.getRarity().getID()+" "+this.getItemName();
    }
}

enum Rarity {
    COMMON("Common", "7"),
    UNCOMMON("Uncommon", "8"),
    RARE("Rare", "9"),
    EPIC("Epic", "5"),
    LEGENDARY("Legendary", "6"),
    DEBUG("Debug", "c");
    
    private final String color;
    private final String id;
    
    Rarity(String id, String string) {
        this.color = string;
        this.id = id;
    }
    
    public String getColor() {
        return this.color;
    }
    
    public String getID() {
        return this.id;
    }
}
