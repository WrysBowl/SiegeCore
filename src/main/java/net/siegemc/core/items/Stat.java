package net.siegemc.core.items;

public enum Stat {
    STRENGTH("Strength", StatType.ADDITION),
    REGENERATION("Regeneration", StatType.ADDITION),
    LUCK("Luck", StatType.ADDITION),
    WISDOM("Wisdom", StatType.ADDITION),
    HEALTH("Health", StatType.ADDITION),
    TOUGHNESS("Toughness", StatType.ADDITION);
    
    private final String id;
    private final StatType type;
    
    Stat(String id, StatType type) {
        this.type = type;
        this.id = id;
    }
    
    public static Stat getFromID(String id) {
        for (Stat stat : values()) {
            if (stat.getID().equalsIgnoreCase(id)) return stat;
        }
        return null;
    }
    
    public StatType getType() {
        return this.type;
    }
    
    public String getID() {
        return this.id;
    }
}

enum StatType {
    ADDITION,
    PERCENTAGE;
}