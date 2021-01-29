package net.siegemc.core.dungeons;

import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import net.siegemc.core.Core;
import net.siegemc.core.Utils;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
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

    public void teleportPlayer(Player p) { // Teleport a player to the most recent available dungeon (is going to be transferred to Dungeon.class in the future
        ConfigurationSection dungeons = Core.plugin().getConfig().getConfigurationSection("dungeons");
        if (dungeons == null) {
            dungeons = Core.plugin().getConfig().createSection("dungeons");
        }
        ConfigurationSection section = dungeons.getConfigurationSection(name());
        if (section == null) {
            section = dungeons.createSection(name());
        }
        int currentKey = 0;
        for (String key : section.getKeys(false)) {
            currentKey = Integer.parseInt(key);
            ConfigurationSection sec = section.getConfigurationSection(key);
            if (sec.getBoolean("free", false)) {
                currentKey--;
                break;
            }
            ;
        }
        currentKey++;
        ConfigurationSection sec = section.getConfigurationSection(String.valueOf(currentKey));
        Location schemLocation = new Location(Bukkit.getWorld(name()), dungeonDistance * currentKey /*This is for each dungeon's distance*/, 128, 500 * ordinal() /* This is for each dungeon type's distance */);
        try {
            SchematicPaster.pasteSchematic(this.schematic, schemLocation, "SPONGE", false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            SchematicPaster.pasteSchematic(schematic, schemLocation, "SPONGE", true);
        } catch (IOException e) {
            p.sendMessage(Utils.tacc("&cAn error was encountered while loading the dungeon!"));
            Core.plugin().getLogger().severe("The dungeon schematic file wasn't loadable!");
            e.printStackTrace();
        } catch (WorldEditException e) {
            p.sendMessage(Utils.tacc("&cAn error was encountered while restarting the dungeon"));
            Core.plugin().getLogger().severe("The dungeon schematic file wasn't able to get pasted!");
            e.printStackTrace();
        }
        sec.set("free", false);
        sec.set("players", new String[]{p.getUniqueId().toString()});
        p.teleport(schemLocation.add(spawnLocation));
    }
}

