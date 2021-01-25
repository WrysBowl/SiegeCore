package net.siegemc.core.Dungeons;

import io.lumine.xikage.mythicmobs.api.bukkit.BukkitAPIHelper;
import io.lumine.xikage.mythicmobs.api.exceptions.InvalidMobTypeException;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.Arrays;

public class DungeonMobs { //NOT IN USE ANYMORE. USING RANDOM MOB SPAWNING
    enum dungeonMythicMobInternalNames {
        Bandit, BanditArcher, Blob, ScorchingBlob, Goblin, Ogre, InfectedDigger, ZombifiedDigger
    }
    //getDungeonMobs is used to see what mobs the spawners in the schematic can spawn
    public ArrayList<String> getDungeonMobs(int level) { //get dungeon mob based on the dungeon level
        ArrayList<String> mobs = new ArrayList<String>();
        if (level <= 5) {
            mobs.add(dungeonMythicMobInternalNames.Blob.toString());
        }
        if (level >= 3 && level <= 8) {
            mobs.add(dungeonMythicMobInternalNames.ScorchingBlob.toString());
        }
        if (level >= 6 && level <= 11) {
            mobs.add(dungeonMythicMobInternalNames.Goblin.toString());
        }
        if (level >= 9 && level <= 14) {
            mobs.add(dungeonMythicMobInternalNames.InfectedDigger.toString());
        }
        if (level >= 12 && level <= 17) {
            mobs.add(dungeonMythicMobInternalNames.ZombifiedDigger.toString());
        }
        if (level >= 15 && level <= 20) {
            mobs.add(dungeonMythicMobInternalNames.Bandit.toString());
        }
        if (level >= 18 && level <= 23) {
            mobs.add(dungeonMythicMobInternalNames.BanditArcher.toString());
        }
        return mobs;
    }

    public void spawnDungeonMob(String mobName, Location loc) throws InvalidMobTypeException { //don't know if this is good or not
        try {
            Entity spawnedMob = new BukkitAPIHelper().spawnMythicMob(mobName, loc);
        } catch (InvalidMobTypeException e) {
            throw new InvalidMobTypeException("MythicMob type " + mobName + " is invalid.");
        }
    }

}