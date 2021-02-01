package net.siegemc.core.Dungeons;

import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import net.siegemc.core.Core;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.generator.ChunkGenerator;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public enum DungeonType {
    //Example: SWAMP_HILLS("swamp_hills.schem", (short) 300, 0, 1, 0)
    ;
    public Clipboard schematic;
    public short dungeonDistance;
    public Location spawnLocation;
    public int dungeonLevel;
    public DungeonTask[] dungeonTasks;
    public Entity dungeonBoss;
    public ArrayList<Dungeon> dungeons;

    /**
     * Creates a new dungeon
     * @param schemName The path to the schematic file (in the resources folder of the plugin)
     * @param dungeonLevel The level of the dungeon
     * @param dungeonDistance The distance between each dungeon of the same type
     * @param x The x offset from the dungeon schematic copy position and the location of the spawn point.
     * @param y The y offset from the dungeon schematic copy position and the location of the player
     * @param z The z offset from the dungeon schematic copy position and the location of the player
     * @param dungeonBoss Not going to lie idk, it was something Wrys was doing
     * @param dungeonTasks Not going to lie idk, it was something Wrys was doing
     */
    DungeonType(String schemName /*The file path of the schematic, relative to the resources folder */, int dungeonLevel, short dungeonDistance /* Distance between each dungeon */, int x, int y, int z /*If you were to paste the dungeon at 0 0 0 then the location of the spawn would be the x, y and z*/, Entity dungeonBoss, DungeonTask[] dungeonTasks) {
        this.dungeonLevel = dungeonLevel;
        this.dungeonTasks = dungeonTasks;
        this.dungeonBoss = dungeonBoss;
        try {
            schematic = SchematicPaster.loadSchematic(Core.plugin().getResource(schemName), ClipboardFormats.findByAlias("SPONGE")); // Sponge schematics as they're the latest ones
        } catch (IOException e) {
            Core.plugin().getLogger().severe("The dungeon schematic file wasn't loadable!");
            e.printStackTrace();
        }
        this.dungeonDistance = dungeonDistance;
        World world = Bukkit.getWorld("dungeons");
        if (world == null) {
            WorldCreator creator = new WorldCreator(name()); // If the dungeons world is nonexistent it creates it and makes each new chunk generated empty
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
        for (DungeonType dungeonType : DungeonType.values()) {
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
        }
        return available;
    }
}

