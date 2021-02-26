package net.siegemc.core.items.CreateItems;

public enum Rarity {
    COMMON("Common", "7"),
    UNCOMMON("Uncommon", "a"),
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
    
    public static Rarity getFromInt(String id) {
        for (Rarity rarity : values()) {
            if (rarity.getID().equalsIgnoreCase(id)) return rarity;
        }
        return Rarity.COMMON;
    }
}
