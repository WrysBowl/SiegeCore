package net.siegemc.core.items.CreateItems;

public enum Rarity {
    COMMON("Common", "<gray>"),
    UNCOMMON("Uncommon", "<green>"),
    RARE("Rare", "<blue>"),
    EPIC("Epic", "<dark_purple>"),
    LEGENDARY("Legendary", "<gold>"),
    DEBUG("Debug", "<red>");
    
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
    
    public static Rarity getFromInt(String id) {
        for (Rarity rarity : values()) {
            if (rarity.getID().equalsIgnoreCase(id)) return rarity;
        }
        return Rarity.COMMON;
    }
}
