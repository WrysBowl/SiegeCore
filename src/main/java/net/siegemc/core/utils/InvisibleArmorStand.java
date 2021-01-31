package net.siegemc.core.utils;

import org.bukkit.entity.ArmorStand;
import org.bukkit.util.Consumer;

public class InvisibleArmorStand implements Consumer<ArmorStand> {
    
    public InvisibleArmorStand() {}
    
    @Override
    public void accept(ArmorStand as) {
        as.setVisible(false);
        as.setInvulnerable(true);
        as.setSmall(true);
        as.setRemoveWhenFarAway(true);
        as.setGravity(false);
        as.setCollidable(false);
        as.setMarker(true);
    }
}