package net.siegemc.core;

import net.siegemc.core.Dungeons.Dungeon;
import net.siegemc.core.Dungeons.DungeonBosses;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class Testing {
    @Test
    public static void main(String[] args){
        System.out.println(DungeonBosses.dungeonBoss(1));
    }
}

