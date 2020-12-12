package net.siegemc.core;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class Profile implements TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You can't run this command!");
        } else {
            Player p = (Player) sender;
            ChestGui gui = new ChestGui(3, Utils.tacc("&6Profile"));
            OutlinePane backgroundPane = new OutlinePane(0, 0, 9, 3, Pane.Priority.LOWEST);
            ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
            backgroundPane.addItem(new GuiItem(item));
            backgroundPane.setRepeat(true);
            gui.addPane(backgroundPane);
            OutlinePane navigationPane = new OutlinePane(2, 1, 5, 1);
            PersistentDataContainer container = p.getPersistentDataContainer();
            Integer currentProfile = currentProfile(p);
            Integer availableN = availableProfileCount(p);
            for (int i = 0; i < 5; i++) {
                if (container.has(Utils.namespacedKey("profiles." + String.valueOf(i)), PersistentDataType.TAG_CONTAINER)) {
                    PersistentDataContainer profile = container.get(Utils.namespacedKey("profiles." + String.valueOf(i)), PersistentDataType.TAG_CONTAINER);
                    ItemStack profItem = new ItemStack(Material.PLAYER_HEAD);
                    profItem.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                    ItemMeta meta = profItem.getItemMeta();
                    meta.setDisplayName(Utils.tacc("&6 Profile #" + i + 1));
                    meta.setLore(Collections.singletonList(Utils.tacc(String.format(
                            "&8Clan: %s\n" +
                                    "&8Class: %s\n" +
                                    "&8Level: &6%s\n" +
                                    "&8Balance: &6%s\n"
                                    + "&8Click to select this profile!"
                            )
                    )));
                    profItem.setItemMeta(meta);
                    if (i == currentProfile) {
                        profItem.addEnchantment(Enchantment.LUCK, 0);
                        navigationPane.addItem(new GuiItem(profItem));
                    } else {
                        GuiItem profileItem = new GuiItem(profItem, click -> {
                            click.getWhoClicked().sendMessage("WIP");
                        });
                        navigationPane.addItem(profileItem);
                    }
                } else if (availableN > i) {
                    ItemStack profItem = new ItemStack(Material.SKELETON_SKULL);
                    ItemMeta meta = profItem.getItemMeta();
                    meta.setDisplayName(Utils.tacc("&aAvailable Profile"));
                    meta.setLore(Collections.singletonList(Utils.tacc(
                            "&8Click to create a new profile."
                    )));
                    profItem.setItemMeta(meta);
                    GuiItem profileItem = new GuiItem(profItem, click -> {
                        // Create a new profile
                    });
                    navigationPane.addItem(profileItem);
                } else {
                    ItemStack profItem = new ItemStack(Material.WITHER_SKELETON_SKULL);
                    ItemMeta meta = profItem.getItemMeta();
                    meta.setDisplayName(Utils.tacc("&cLocked Profile"));
                    meta.setLore(Collections.singletonList(Utils.tacc(
                            "&8Additional slots can be bought in the store."
                    )));
                    profItem.setItemMeta(meta);
                    GuiItem profileItem = new GuiItem(profItem, click -> {
                        // Create a new profile
                    });
                    navigationPane.addItem(profileItem);
                }
            }
            gui.addPane(navigationPane);
            gui.show(p);
        }
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length > 5) return Collections.singletonList("You found an easter egg!");
        return null;
    }

    public static Integer currentProfile(Player player) {
        if (player.getPersistentDataContainer().has(Utils.namespacedKey("profiles.current"), PersistentDataType.INTEGER))
            return player.getPersistentDataContainer().get(Utils.namespacedKey("profiles.current"), PersistentDataType.INTEGER);
        else return null;

    }

    public static boolean profileExists(Player player, Integer profileID) {
        return player.getPersistentDataContainer().has(Utils.namespacedKey("profiles." + profileID + ".clan"), PersistentDataType.INTEGER);
    }

    public static int availableProfileCount(Player player) {
        return player.getPersistentDataContainer().getOrDefault(Utils.namespacedKey("profiles.available"), PersistentDataType.INTEGER, 2);
    }


    public static String getClan(Player player, Integer profileID) {
        if (profileID == null) profileID = currentProfile(player);
        if (profileID == null) return "N/A";
        Clans clan = Clans.values()[player.getPersistentDataContainer().get(Utils.namespacedKey("profiles." + profileID + ".clan"), PersistentDataType.INTEGER)];
        return clan.color + clan.name();
    }

    public static String getClass(Player player, Integer profileID) {
        if (profileID == null) profileID = currentProfile(player);
        if (profileID == null) return "N/A";
        Classes cls = Classes.values()[player.getPersistentDataContainer().get(Utils.namespacedKey("profiles." + profileID + ".class"), PersistentDataType.INTEGER)];
        return cls.color + cls.name();
    }

    public static int getBalance(Player player, Integer profileID) {
        if (profileID == null) profileID = currentProfile(player);
        if (profileID == null) return 0;
        return player.getPersistentDataContainer().get(Utils.namespacedKey("profiles." + profileID + ".balance"), PersistentDataType.INTEGER);
    }

    public static int getBank(Player player, Integer profileID) {
        Integer currentProfile = currentProfile(player);
        if (currentProfile == null) return 0;
        return player.getPersistentDataContainer().get(Utils.namespacedKey("profiles." + currentProfile + ".bank"), PersistentDataType.INTEGER);
    }

    public static int getLevel(Player player, Integer profileID) {
        if (profileID == null) profileID = currentProfile(player);
        if (profileID == null) return 0;
        return player.getPersistentDataContainer().getOrDefault(Utils.namespacedKey("profiles." + profileID + ".level"), PersistentDataType.INTEGER, 0);
    }

    public static int getExp(Player player, Integer profileID) {
        if (profileID == null) profileID = currentProfile(player);
        if (profileID == null) return 0;
        return player.getPersistentDataContainer().getOrDefault(Utils.namespacedKey("profiles." + profileID + ".exp"), PersistentDataType.INTEGER, 0);
    }

    public static PlayerInventory getInventory(Player player, Integer profileID) {
        if (profileID == null)
            profileID = currentProfile(player);
        if (profileID == null) return null;
        PlayerInventory inventory = (PlayerInventory) Bukkit.createInventory(player, InventoryType.PLAYER);
        Integer invSize = player.getPersistentDataContainer().get(Utils.namespacedKey("profiles." + profileID + ".inv.size"), PersistentDataType.INTEGER);
        for (int i = 0; i < invSize; i++) {
            ItemStack item = ItemStack.deserializeBytes(player.getPersistentDataContainer().get(Utils.namespacedKey("profiles." + profileID + ".inv." + i), PersistentDataType.BYTE_ARRAY));
            inventory.setItem(i, item);
        }
        return inventory;
    }


    public static Integer getStat(Player player, Stats stat, Integer profileID) {
        if (profileID == null)
            profileID = currentProfile(player);
        if (profileID == null) return 0;
        return player.getPersistentDataContainer().get(Utils.namespacedKey("profiles." + profileID + ".stat." + stat.ordinal()), PersistentDataType.INTEGER);
    }

    public static void setInventory(Player player, Integer profileID, PlayerInventory inv) {
        if (profileID == null)
            profileID = currentProfile(player);
        player.getPersistentDataContainer().set(Utils.namespacedKey("profiles." + profileID + ".inv.size"), PersistentDataType.INTEGER, inv.getSize());
        for (int i = 0; i < inv.getSize(); i++) {
            player.getPersistentDataContainer().set(Utils.namespacedKey("profiles." + profileID + ".inv." + i), PersistentDataType.BYTE_ARRAY, inv.getItem(i).serializeAsBytes());
        }
    }

    enum Stats {
        Wisdom,
        Strength,
        Intelligence,
        Stamina,
        Dexterity
    }

    enum Clans {
        Knights("&c"),
        Crusaders("&9");
        public String color;

        Clans(String color) {
            this.color = color;
        }
    }

    enum Classes {
        Warrior("&c", "- Deal quick melee damage\n&7- Naturally a tank"),
        Archer("&b", "&7- Long ranged fast attacks\n&7- Naturally high critical rate"),
        Mage("&d", "&7- Long ranged, but slow attacks\n&7- Deals area damage"),
        Shaman("&a", "&7- Deal quick melee damage\n&7- Buff/debuff targets\n&7- Skill heavy");
        public String color;
        public String description;

        Classes(String color, String description) {
            this.color = color;
            this.description = description;
        }
    }
}


