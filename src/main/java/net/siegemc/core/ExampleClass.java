package net.siegemc.core;

import net.siegemc.core.Dungeons.Dungeon;
import org.junit.Test;

public class ExampleClass {
    @Test
    public static void main(String[] args){
        String d = new Dungeon(1).getBossName();
        System.out.println(d);
    }
}
