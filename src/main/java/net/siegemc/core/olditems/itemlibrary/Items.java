package net.siegemc.core.olditems.itemlibrary;

import net.siegemc.core.utils.Utils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class Items {

    // HashMap with every reagent and its material
    // To add a reagent simply add the name and material to this hashmap
    // What is automatically produced:
    //          5 Upgrade and 5 Dismantle recipes
    // Assumed:
    //          Each reagent has exactly 5 tiers and requires a quantity of 8 to upgrade, as well as
    // receiving 4 upon dismantle
    // For additional functionality contact Tony942316
    public static HashMap<String, org.bukkit.Material> ReagentsToMaterials = new HashMap<String, org.bukkit.Material>() {{
        put("DIRT_CLUMP", Material.DARK_OAK_BUTTON);
        put("SEED", Material.WHEAT_SEEDS);
        put("TURF", Material.GREEN_CARPET);
        put("MOSS", Material.GREEN_DYE);
        put("MOSSY_DIRT", Material.GRASS_BLOCK);
        put("PEBBLE", Material.STONE_BUTTON);
        put("STONE", Material.STONE);
        put("VINE", Material.VINE);
        put("WOOD", Material.SPRUCE_PLANKS);
        put("METAL_SCRAP", Material.NETHERITE_SCRAP);
        put("REFINED_METAL", Material.IRON_INGOT);
        put("SLIME", Material.SLIME_BALL);
        put("MAGMA", Material.MAGMA_CREAM);
        put("ECTOPLASM", Material.SLIME_BALL);
        put("BONE", Material.BONE);
        put("WOOL", Material.WHITE_WOOL);
        put("LEATHER", Material.LEATHER);
        put("FEATHER", Material.FEATHER);
        put("DRUMSTICK", Material.CHICKEN);
    }};

    public enum Tools { //Used to check when to set gamemode to adventure/survival
        WOODEN_PICKAXE, WOODEN_AXE, WOODEN_HOE, WOODEN_SHOVEL,
        STONE_PICKAXE, STONE_AXE, STONE_HOE, STONE_SHOVEL,
        GOLDEN_PICKAXE, GOLDEN_AXE, GOLDEN_HOE, GOLDEN_SHOVEL,
        DIAMOND_PICKAXE, DIAMOND_AXE, DIAMOND_HOE, DIAMOND_SHOVEL,
        NETHERITE_PICKAXE, NETHERITE_AXE, NETHERITE_HOE, NETHERITE_SHOVEL;
    }
    public static Tools checkTools(Material toolType) { //Search the Tools enum
        try {
            return Tools.valueOf(toolType.toString());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public static ItemStack searchMaterialLibrary(String itemName, Integer tier, Integer amount) {
        String[] colors = {"7", "a", "9", "5", "6"}; // Colors
        // Check if item exists
        if (ReagentsToMaterials.get(itemName) == null) {
            return null;
        }
        else {
            String name = "&" + colors[tier - 1] + reagentStrNorm(itemName) + " &7" + stringRepeat("✪", tier); // "Build" relevant string
            return Utils.createItem(ReagentsToMaterials.get(itemName), Utils.tacc(name), false, amount);
        }
    }

    // Quick write!!!! INEFFICIENT!!!!!
    public static String stringRepeat(String str, int times) {
        String ans = ""; // init return variable
        // loop n times
        for (int i = 0; i < times; i++) {
            ans = ans + str; // concatenate
        }
        return ans;
    }

    // Quick write!!!! INEFFICIENT!!!!!
    public static String reagentStrNorm(String str) {
        String newStr = ""; // init return variable
        newStr += str.charAt(0); // First letter is always capital
        // Loop through whole string
        for (int i = 1; i < str.length(); i++) {
            // Check for underscore which denotes space
            if (str.charAt(i) == '_') {
                newStr += " " + str.charAt(i + 1); // Letter after underscore is capital
                i++; // Skip over next letter (already done on prev line)
            }
            else {
                newStr += Character.toLowerCase(str.charAt(i)); // Lowercase the letter
            }
        }
        return newStr;
    }
}
