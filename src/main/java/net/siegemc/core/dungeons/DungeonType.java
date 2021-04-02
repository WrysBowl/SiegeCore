package net.siegemc.core.dungeons;

import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import net.siegemc.core.Core;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.generator.ChunkGenerator;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

//
// The yaml syntax for the dungeons
//
// NAME: # The dungeon type's name
//  spawnOffset:
//      x: 1
//      y: 2
//      z: 3
//  schemPath: schematics/bad.schem
//  level: 5
//  distance: 500
//  boss:
//      x: 1
//      y: 2
//      z: 3
//  dungeons: # List of dungeons of that type
//      1:
//          # Dungeon stuff (see Dungeon.java)
//

public class DungeonType {
    public Clipboard schematic;
    public short dungeonDistance;
    public Location spawnLocation;
    public int dungeonLevel;
    public ArrayList<Dungeon> dungeons;
    //public Location bossLocation;
    //public MythicMob boss;

    public static HashSet<DungeonType> dungeonTypes = new HashSet<>();
    public static World world = Bukkit.getWorld("dungeons");

    public String name;
    public Integer index;

    public static DungeonType deserialize(ConfigurationSection section, String name) {
        ConfigurationSection spawnOffset = section.getConfigurationSection("spawnOffset");
        ConfigurationSection boss = section.getConfigurationSection("boss");
        Location relSpawnLoc = new Location(world, spawnOffset.getInt("x"), spawnOffset.getInt("y"), spawnOffset.getInt("z"));
        /*if (boss == null) {*/
            return new DungeonType(name, section.getString("schemPath"), section.getInt("level"), (short) section.getInt("distance"),
                    relSpawnLoc, null, null);
        /*}
        Location relBossLoc = new Location(world, boss.getInt("x"), boss.getInt("y"), boss.getInt("z"));
        DungeonType type = new DungeonType(name, section.getString("schemPath"), section.getInt("level"), (short) section.getInt("distance"),
                relSpawnLoc, relBossLoc, boss.getString("name"));
        return type;
         */
    }

    /**
     * Creates a new dungeon
     *
     * @param schemPath    The path to the schematic file (in the data folder of the plugin)
     * @param dungeonLevel The level of the dungeon
     * @param distance     The distance between each dungeon of the same type
     * @param relSpawnLoc  The relative player spawn location, relative to the dungeon schematic copy position and the location of the spawn point.
     * @param name         The dungeon's name
     */
    DungeonType(String name, String schemPath /*The file path of the schematic, relative to the resources folder */, int dungeonLevel, short distance /* Distance between each dungeon */, Location relSpawnLoc, Location relBossLoc, String bossName) {
        this.dungeonLevel = dungeonLevel;
        this.index = dungeonTypes.size();
        dungeonTypes.add(this);
        //this.boss = MythicMobs.inst().getMobManager().getMythicMob(bossName);
        this.spawnLocation = relSpawnLoc;
        //this.bossLocation = relBossLoc;
        this.name = name;
        try {
            schematic = SchematicPaster.loadSchematic(new FileInputStream(new File(Core.plugin().getDataFolder().getAbsolutePath(), schemPath)), ClipboardFormats.findByAlias("SPONGE")); // Sponge schematics as they're the latest ones
        } catch (IOException e) {
            Core.plugin().getLogger().severe("The dungeon schematic file wasn't loadable!");
            e.printStackTrace();
        }
        this.dungeonDistance = distance;
        if (world == null) {
            WorldCreator creator = new WorldCreator(name); // If the dungeons world is nonexistent it creates it and makes each new chunk generated empty
            creator.generateStructures(false).type(WorldType.FLAT).environment(World.Environment.NORMAL);
            creator.generator(new ChunkGenerator() {
                @NotNull
                @Override
                public ChunkData generateChunkData(@NotNull World world, @NotNull Random random, int x, int z, @NotNull BiomeGrid biome) {
                    return createChunkData(world); // This should set all blocks to air
                }

                @Override
                public boolean isParallelCapable() {
                    return true;
                } // Whether or not the generator can run parallel
            });
            world = creator.createWorld(); // Creates the world
        }
        ConfigurationSection dungeonCfg = DungeonConfig.getDungeons(this);
        dungeonCfg.getKeys(false).forEach(key -> {
            if (dungeonCfg.isConfigurationSection(key)) {
                ConfigurationSection section = dungeonCfg.getConfigurationSection(key);
                Integer k = Integer.valueOf(key);
                dungeons.add(k, Dungeon.deserialize(section, k, this));
            }
        });

    }


    /**
     * Removes all dungeons of the type
     */
    public void clear() {
        for (Dungeon dungeon : dungeons)
            dungeon.delete();
    }

    /**
     * Removes ALL the dungeons in the world.
     */
    public static void removeAll() {
        for (DungeonType dungeonType : dungeonTypes) {
            dungeonType.clear();
        }
    }

    /**
     * Finds the next avaliable dungeon and returns it
     *
     * @return Dungeon The next available dungeon
     */
    public Dungeon nextAvailableDungeon() {
        Dungeon available = null;
        int dungeonLength = dungeons.size();
        for (Dungeon dungeon : dungeons) {
            if (dungeon.listPlayers().size() < 1) {
                dungeon.reset();
                available = dungeon;
            }
        }
        if (available == null) {
            available = new Dungeon(this, dungeonLength + 1);
            available.reset();
            dungeons.add(available);
        }
        return available;
    }
}

