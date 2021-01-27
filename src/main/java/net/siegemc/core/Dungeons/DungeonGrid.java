package net.siegemc.core.Dungeons;

import java.util.Map.Entry;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.HashMap;

public class DungeonGrid {
    private static final HashMap<Location, Dungeon> dungeonGrid = new HashMap<>(); // Dungeon grid of locations
    public static int maxGridPoints = 25; // Example size

    // Call when updating the tasks or other for the dungeon Object.
    public void setDungeonEntry(Location location, Dungeon dungeon) { // Sets an existing entry dungeon object to something new. Used for updating tasks etc.
        if (!dungeonGrid.containsKey(location)) return;
        dungeonGrid.replace(location, dungeon); // Not 100% sure if this overwrites properly. PLEASE CHECK
        // Alternative dungeonGrid.replace(location, olddungeon, newdungeon);
    }

    // Call when needing to create a dungeon.
    public Location dungeonCreate(Dungeon dungeon){ //add schematic and coordinate of the next available grid point to the array list
        int Index = dungeonGrid.isEmpty() ? 1 : dungeonGrid.size();

        int locationX = (Index / 5) * 500;
        int locationZ = (Index % 5) * 500;

        Location location = new Location(Bukkit.getServer().getWorld("Dungeons"), locationX, 120, locationZ); // "Dungeons" is a placeholder for the world name.
        if (!dungeonGrid.isEmpty()) {
            int X;
            for (int i = 0; i <= dungeonGrid.size(); i++) {
                if (dungeonGrid.containsKey(location)) {
                    X = i / 5;
                    location.set(X * 500, 120, (i % 5) * 500);
                }
                else { break; }
            }
        } // Could do with a more efficient method than this for loop... Replace if possible
        dungeonGrid.put(location, dungeon);
        return location;
        //SchematicPaster.pasteSchematic("", location, "", true); // FILL THE PARAMS HERE!!! <<< Don't forget to uncomment this.
        //This information for the schematic needs to be filled.
    }

    // Call once a dungeon has been completed.
    public void dungeonDelete(Location location){ //deletes the grid point via the Location object
        if (dungeonGrid.isEmpty()) return;
        if (!dungeonGrid.containsKey(location)) return;
        dungeonGrid.remove(location);
    }
    // Call when you feel like being a meanie and deleting everyone's dungeon data.
    public void dungeonDeleteEntireGrid(){ //deletes all dungeons and stored grid points
        dungeonGrid.clear();
    }

    // Call if you need the dungeon location (For NPC location etc) Returns an Object not a Location.
    public Location getDungeonEntry(Dungeon dungeon){ //returns the location via the dungeon object
        if (dungeonGrid.isEmpty()) { return null; }
        for (Entry<Location, Dungeon> singleEntry : dungeonGrid.entrySet()) {
            if (singleEntry.getValue().equals(dungeon)) { return singleEntry.getKey(); }
        }
        return null;
    }

    // Call for the amount of active dungeons, not required. All methods depend on the grid being not empty.
    public int getDungeonGrid(){ //returns the amount of active dungeons from the grid (All dungeons from 1 to Index are active currently).
        if (dungeonGrid.isEmpty()) return 0;
        return dungeonGrid.keySet().size();
    }
}