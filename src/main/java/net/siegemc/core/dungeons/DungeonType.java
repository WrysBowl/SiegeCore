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
//      Something that Wrys needs to figure out.
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
    public static HashSet<DungeonType> dungeonTypes;
    public String name;
    public Integer index;

    public static DungeonType deserialize(ConfigurationSection section, String name) {
        ConfigurationSection spawnOffset = section.getConfigurationSection("spawnOffset");
        ConfigurationSection boss = section.getConfigurationSection("boss");
        DungeonType type = new DungeonType(section.getString("schemPath"), section.getInt("level"), (short) section.getInt("distance"),
                spawnOffset.getInt("x"), spawnOffset.getInt("y"), spawnOffset.getInt("z"), name);
        return type;
    }

    /**
     * Creates a new dungeon
     *
     * @param schemPath    The path to the schematic file (in the data folder of the plugin)
     * @param dungeonLevel The level of the dungeon
     * @param distance     The distance between each dungeon of the same type
     * @param x            The x offset from the dungeon schematic copy position and the location of the spawn point.
     * @param y            The y offset from the dungeon schematic copy position and the location of the player
     * @param z            The z offset from the dungeon schematic copy position and the location of the player
     * @param name         The dungeon's name
     */
    DungeonType(String schemPath /*The file path of the schematic, relative to the resources folder */, int dungeonLevel, short distance /* Distance between each dungeon */, int x, int y, int z /*If you were to paste the dungeon at 0 0 0 then the location of the spawn would be the x, y and z*/, String name) {
        this.dungeonLevel = dungeonLevel;
        this.index = dungeonTypes.size();
        dungeonTypes.add(this);
        this.name = name;
        try {
            schematic = SchematicPaster.loadSchematic(new FileInputStream(new File(Core.plugin().getDataFolder().getAbsolutePath(), schemPath)), ClipboardFormats.findByAlias("SPONGE")); // Sponge schematics as they're the latest ones
        } catch (IOException e) {
            Core.plugin().getLogger().severe("The dungeon schematic file wasn't loadable!");
            e.printStackTrace();
        }
        this.dungeonDistance = distance;
        World world = Bukkit.getWorld("dungeons");
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
        spawnLocation = new Location(world, x, y, z);
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

