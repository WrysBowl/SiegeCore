package net.siegemc.core.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

public class Test implements Listener, Cancellable {

    @EventHandler
    public void onMobSpawn(EntitySpawnEvent entity) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage("Spawned Mob");
        }
        if (MobLevel((entity.getLocation())) != 0){ //makes sure it is a spawning area
            //spawn the mob
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendMessage("It works!");
            }
        }
        else {
            setCancelled(true);
        }
    }
    public int MobLevel(Location loc) {
        loc.setY(0);
        Block block = loc.getBlock();
        if(block.getType() == Material.GRAY_WOOL){
            return 1;
        }
        else if(block.getType() == Material.RED_WOOL) {
            return 2;
        }
        else if(block.getType() == Material.GREEN_WOOL) {
            return 3;
        }
        return 0;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public void setCancelled(boolean cancel) {

    }
}
