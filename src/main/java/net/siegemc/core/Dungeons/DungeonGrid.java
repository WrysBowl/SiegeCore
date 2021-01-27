package net.siegemc.core.Dungeons;

import org.bukkit.Location;
import java.util.ArrayList;

public class DungeonGrid { //How to delete schematics?

    static ArrayList<Location> dungeonGrid = new ArrayList<Location>(); //Dungeon grid of locations


    public static void dungeonAddLoc(){ //add schematic and coordinate of the next available grid point to the array list

    }
    public static void dungeonDelLoc(){ //deletes the grid point at the index of the parameter of the array list

    }
    public static void dungeonDelGrid(){ //deletes all dungeons and stored grid points
        dungeonGrid.clear();
    }
    public static void dungeonViewLoc(){ //returns the location of the grid point in the array

    }
    public static void dungeonViewNextLoc(){ //returns the location of the grid point in the array

    }
    public ArrayList<Location> dungeonViewGrid(){ //returns all the grid points of active dungeons
        return dungeonGrid;
    }

}