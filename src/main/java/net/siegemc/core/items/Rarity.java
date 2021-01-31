package net.siegemc.core.items;

public enum Rarity {
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
