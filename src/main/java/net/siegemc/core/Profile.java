package net.siegemc.core;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
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
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collections;
import java.util.List;

public class Profile implements TabExecutor {
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
            @Nullable
            Byte currentProfile = getCurrentProfile(p);
            Byte availableN = availableProfileCount(p);
            if (availableN == null) availableN = 2;

            for (byte i = 0; i < 5; ++i) {
                ItemStack profItem;
                ItemMeta meta;
                GuiItem profileItem;
                Boolean profileExists = profileExists(p, i);
                if (profileExists != null && profileExists) {
                    profItem = new ItemStack(Material.PLAYER_HEAD);
                    profItem.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                    meta = profItem.getItemMeta();
                    meta.setDisplayName(Utils.tacc("&6 Profile #" + i + "1"));
                    meta.setLore(Collections.singletonList(Utils.tacc(String.format("&8Clan: %s\n&8Class: %s\n&8Level: &6%s\n&8Bank: &6%s\n&8Click to select this profile!", getGuild(p, i), getClass(p, i), getLevel(p, i), getBank(p, i)))));
                    profItem.setItemMeta(meta);
                    if (currentProfile != null && i == currentProfile) {
                        profItem.addEnchantment(Enchantment.LUCK, 0);
                        navigationPane.addItem(new GuiItem(profItem));
                    } else {
                        byte finalI = i;
                        profileItem = new GuiItem(profItem, (click) -> {
                            if (currentProfile != null)
                                saveInventory(p, currentProfile, p.getInventory());
                            p.getInventory().clear();
                            PlayerInventory newInv = getInventory(p, finalI);
                            for (int i1 = 0; i1 < newInv.getSize(); i1++) {
                                p.getInventory().setItem(i1, newInv.getItem(i1));
                            }
                            setCurrentProfile(p, finalI);
                        });
                        navigationPane.addItem(profileItem);
                        p.sendMessage(Utils.tacc(String.format("&6Successfully swapped to profile #%s (from #%s)", i, currentProfile)));
                        return true;
                    }
                } else {
                    if (availableN > i) {
                        profItem = new ItemStack(Material.SKELETON_SKULL);
                        meta = profItem.getItemMeta();
                        meta.setDisplayName(Utils.tacc("&aAvailable Profile"));
                        meta.setLore(Collections.singletonList(Utils.tacc("&8Click to create a new profile.")));
                        profItem.setItemMeta(meta);
                        profileItem = new GuiItem(profItem, (click) -> {
                            click.setCancelled(true);
                            Player pl = (Player) click.getWhoClicked();
                            pl.closeInventory();
                            Connection con = DbManager.getConnection();
                            if (con == null) {
                                pl.sendMessage(Utils.tacc("&4Error: &cThe server was unable to create a new profile!"));
                            } else {
                                ChestGui classGUI = new ChestGui(3, Utils.tacc("&6Profile"));
                                classGUI.addPane(backgroundPane);
                                StaticPane classPane = new StaticPane(0, 0, 9, 1, Pane.Priority.HIGHEST);
                                ItemStack warriorItem = new ItemStack(Material.PLAYER_HEAD);
                                SkullMeta warriorMeta = (SkullMeta) warriorItem.getItemMeta();
                                warriorItem.setItemMeta(skullFromBase64(pl.getPlayerProfile(), warriorMeta, Classes.Warrior.base64));
                                classPane.addItem(new GuiItem(warriorItem), 1, 1);
                                classGUI.addPane(classPane);
                                gui.show(pl);
                                /*
                                String sql = "INSERT INTO profileData (uuid, profileID, class) VALUES (?, ?, ?)";

                                try {
                                    PreparedStatement statement = con.prepareStatement(sql);
                                    statement.setString(1, p.getUniqueId().toString());
                                    statement.setByte(2, finalI1);
                                    statement.setString(3, "/*TODO*//*");
                                    statement.executeUpdate();
                                    DbManager.releaseConnection(con);
                                } catch (Exception e) {
                                    p.sendMessage(Utils.tacc("&4Error: &cThe server was unable to create a new profile!"));
                                    p.closeInventory();
                                    e.printStackTrace();
                                }
                                */
                            }
                            DbManager.releaseConnection(con);
                        });
                    } else {
                        profItem = new ItemStack(Material.WITHER_SKELETON_SKULL);
                        meta = profItem.getItemMeta();
                        meta.setDisplayName(Utils.tacc("&cLocked Profile"));
                        meta.setLore(Collections.singletonList(Utils.tacc("&8Additional slots can be bought in the store.\n")));
                        profItem.setItemMeta(meta);
                        profileItem = new GuiItem(profItem, (click) -> {
                            click.setCancelled(true);
                        });
                    }
                    navigationPane.addItem(profileItem);
                }
            }

            gui.addPane(navigationPane);
            gui.show(p);
        }

        return true;
    }

    private SkullMeta skullFromBase64(PlayerProfile prof, SkullMeta meta, String b64) {
        prof.clearProperties();
        prof.setProperty(new ProfileProperty("textures", b64));
        meta.setPlayerProfile(prof);
        return meta;
    }

    @Nullable
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return args.length > 5 ? Collections.singletonList("You found an easter egg!") : null;
    }

    public static Byte getCurrentProfile(Player player) {
        return player.getPersistentDataContainer().has(Utils.namespacedKey("profiles.current"), PersistentDataType.BYTE) ? (Byte) player.getPersistentDataContainer().get(Utils.namespacedKey("profiles.current"), PersistentDataType.BYTE) : null;
    }

    public static void setCurrentProfile(Player player, Byte profileID) {
        player.getPersistentDataContainer().set(Utils.namespacedKey("profiles.current"), PersistentDataType.BYTE, profileID);
    }

    public static Boolean profileExists(Player player, byte profileID) {
        Connection con = DbManager.getConnection();
        if (con == null)
            return null;
        else {
            String sql = "SELECT profileID FROM profileData WHERE uuid=? AND profileID=?";
            try {
                PreparedStatement statement = con.prepareStatement(sql);
                statement.setString(1, player.getUniqueId().toString());
                statement.setByte(2, profileID);
                ResultSet result = statement.executeQuery();
                result.last();
                boolean r = result.getRow() >= 1;
                DbManager.releaseConnection(con);
                return r;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public static Byte availableProfileCount(Player player) {
        Connection con = DbManager.getConnection();
        if (con == null)
            return null;
        else {
            String sql = "SELECT availableProfiles FROM userData WHERE uuid=?";

            try {
                PreparedStatement statement = con.prepareStatement(sql);
                statement.setString(1, player.getUniqueId().toString());
                ResultSet result = statement.executeQuery();
                DbManager.releaseConnection(con);
                return result.getFetchSize() < 1 ? 2 : result.getByte(0);
            } catch (Exception e) {
                e.printStackTrace();
                return 2;
            }
        }
    }

    public static String getGuild(Player player, Byte profileID) {
        if (profileID == null)
            profileID = getCurrentProfile(player);

        if (profileID == null)
            return "N/A";
        else {
            Connection con = DbManager.getConnection();
            if (con == null)
                return null;
            else {
                String sql = "SELECT guild FROM profileData WHERE uuid=? AND profileID=?";

                try {
                    PreparedStatement statement = con.prepareStatement(sql);
                    statement.setString(1, player.getUniqueId().toString());
                    statement.setByte(2, profileID);
                    ResultSet result = statement.executeQuery();
                    DbManager.releaseConnection(con);
                    return result.getString(1);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
    }

    public static void setGuild(Player player, Byte profileID, String guildName) {
        if (profileID == null)
            profileID = getCurrentProfile(player);
        if (profileID == null) return;
        Connection con = DbManager.getConnection();
        if (con == null)
            return;
        else {
            String sql = "UPDATE profileData SET guild=? WHERE uuid=? AND profileID=?";
            try {
                PreparedStatement statement = con.prepareStatement(sql);
                statement.setString(1, guildName);
                statement.setString(2, player.getUniqueId().toString());
                statement.setByte(3, profileID);
                statement.executeUpdate();
                DbManager.releaseConnection(con);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static String getClass(Player player, Byte profileID) {
        if (profileID == null)
            profileID = getCurrentProfile(player);
        if (profileID == null)
            return "N/A";
        else {
            Connection con = DbManager.getConnection();
            if (con == null)
                return null;
            else {
                String sql = "SELECT class FROM profileData WHERE uuid=? AND profileID=?";

                try {
                    PreparedStatement statement = con.prepareStatement(sql);
                    statement.setString(1, player.getUniqueId().toString());
                    statement.setByte(2, profileID);
                    ResultSet result = statement.executeQuery();
                    DbManager.releaseConnection(con);
                    Profile.Classes clss = Profile.Classes.values()[result.getShort(1)];
                    String color = clss.color;
                    return color + clss.name();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
    }

    public static void setClass(Player player, Byte profileID, Classes cls) {
        if (profileID == null)
            profileID = getCurrentProfile(player);
        if (profileID == null) return;
        Connection con = DbManager.getConnection();
        if (con == null)
            return;
        else {
            String sql = "UPDATE profileData SET class=? WHERE uuid=? AND profileID=?";
            try {
                PreparedStatement statement = con.prepareStatement(sql);
                statement.setShort(1, (short) cls.ordinal());
                statement.setString(2, player.getUniqueId().toString());
                statement.setByte(3, profileID);
                statement.executeUpdate();
                DbManager.releaseConnection(con);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static int getBalance(Player player, Byte profileID) {
        if (profileID == null)
            profileID = getCurrentProfile(player);


        if (profileID == null)
            return 0;
        else {
            Connection con = DbManager.getConnection();
            if (con == null)
                return 0;
            else {
                String sql = "SELECT purse FROM profileData WHERE uuid=? AND profileID=?";

                try {
                    PreparedStatement statement = con.prepareStatement(sql);
                    statement.setString(1, player.getUniqueId().toString());
                    statement.setByte(2, profileID);
                    ResultSet result = statement.executeQuery();
                    DbManager.releaseConnection(con);
                    return result.getInt(1);
                } catch (Exception e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        }
    }

    public static void setBalance(Player player, Byte profileID, int balance) {
        if (profileID == null)
            profileID = getCurrentProfile(player);
        if (profileID == null) return;
        Connection con = DbManager.getConnection();
        if (con == null)
            return;
        else {
            String sql = "UPDATE profileData SET balance=? WHERE uuid=? AND profileID=?";
            try {
                PreparedStatement statement = con.prepareStatement(sql);
                statement.setInt(1, balance);
                statement.setString(2, player.getUniqueId().toString());
                statement.setByte(3, profileID);
                statement.executeUpdate();
                DbManager.releaseConnection(con);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static int getBank(Player player, Byte profileID) {
        if (profileID == null)
            profileID = getCurrentProfile(player);


        if (profileID == null)
            return 0;
        else {
            Connection con = DbManager.getConnection();
            if (con == null)
                return 0;
            else {
                String sql = "SELECT bank FROM profileData WHERE uuid=? AND profileID=?";

                try {
                    PreparedStatement statement = con.prepareStatement(sql);
                    statement.setString(1, player.getUniqueId().toString());
                    statement.setByte(2, profileID);
                    ResultSet result = statement.executeQuery();
                    DbManager.releaseConnection(con);
                    return result.getInt(1);
                } catch (Exception e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        }
    }

    public static void setBank(Player player, Byte profileID, int bank) {
        if (profileID == null)
            profileID = getCurrentProfile(player);
        if (profileID == null) return;
        Connection con = DbManager.getConnection();
        if (con == null)
            return;
        else {
            String sql = "UPDATE profileData SET bank=? WHERE uuid=? AND profileID=?";
            try {
                PreparedStatement statement = con.prepareStatement(sql);
                statement.setInt(1, bank);
                statement.setString(2, player.getUniqueId().toString());
                statement.setByte(3, profileID);
                statement.executeUpdate();
                DbManager.releaseConnection(con);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static int getLevel(Player player, Byte profileID) {
        if (profileID == null)
            profileID = getCurrentProfile(player);


        if (profileID == null)
            return 0;
        else {
            Connection con = DbManager.getConnection();
            if (con == null)
                return 0;
            else {
                String sql = "SELECT level FROM profileData WHERE uuid=? AND profileID=?";

                try {
                    PreparedStatement statement = con.prepareStatement(sql);
                    statement.setString(1, player.getUniqueId().toString());
                    statement.setByte(2, profileID);
                    ResultSet result = statement.executeQuery();
                    DbManager.releaseConnection(con);
                    return result.getShort(1);
                } catch (Exception e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        }
    }

    public static void setLevel(Player player, Byte profileID, short level) {
        if (profileID == null)
            profileID = getCurrentProfile(player);
        if (profileID == null) return;
        Connection con = DbManager.getConnection();
        if (con == null)
            return;
        else {
            String sql = "UPDATE profileData SET level=? WHERE uuid=? AND profileID=?";
            try {
                PreparedStatement statement = con.prepareStatement(sql);
                statement.setShort(1, level);
                statement.setString(2, player.getUniqueId().toString());
                statement.setByte(3, profileID);
                statement.executeUpdate();
                DbManager.releaseConnection(con);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static int getExp(Player player, Byte profileID) {
        if (profileID == null)
            profileID = getCurrentProfile(player);


        if (profileID == null)
            return 0;
        else {
            Connection con = DbManager.getConnection();
            if (con == null)
                return 0;
            else {
                String sql = "SELECT experience FROM profileData WHERE uuid=? AND profileID=?";

                try {
                    PreparedStatement statement = con.prepareStatement(sql);
                    statement.setString(1, player.getUniqueId().toString());
                    statement.setByte(2, profileID);
                    ResultSet result = statement.executeQuery();
                    DbManager.releaseConnection(con);
                    return result.getInt(1);
                } catch (Exception e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        }
    }

    public static void setExp(Player player, Byte profileID, int experience) {
        if (profileID == null)
            profileID = getCurrentProfile(player);
        if (profileID == null) return;
        Connection con = DbManager.getConnection();
        if (con == null)
            return;
        else {
            String sql = "UPDATE profileData SET experience=? WHERE uuid=? AND profileID=?";
            try {
                PreparedStatement statement = con.prepareStatement(sql);
                statement.setInt(1, experience);
                statement.setString(2, player.getUniqueId().toString());
                statement.setByte(3, profileID);
                statement.executeUpdate();
                DbManager.releaseConnection(con);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static PlayerInventory getInventory(Player player, Byte profileID) {
        if (profileID == null)
            profileID = getCurrentProfile(player);
        if (profileID == null)
            return null;
        else {
            Connection con = DbManager.getConnection();
            if (con == null)
                return null;
            else {
                String sql = "SELECT inventory FROM profileData WHERE uuid=? AND profileID=?";

                try {
                    PreparedStatement statement = con.prepareStatement(sql);
                    statement.setString(1, player.getUniqueId().toString());
                    statement.setByte(2, profileID);
                    ResultSet result = statement.executeQuery();
                    DbManager.releaseConnection(con);
                    JSONParser parser = new JSONParser();
                    JSONArray obj = (JSONArray) parser.parse(result.getString(0));
                    PlayerInventory inv = (PlayerInventory) Bukkit.createInventory(player, InventoryType.PLAYER);

                    for (int i = 0; i < obj.size(); i++) {
                        ItemStack stack = ItemStack.deserializeBytes((byte[]) obj.get(i));
                        inv.setItem(i, stack);
                    }

                    return inv;
                } catch (Exception e1) {
                    e1.printStackTrace();
                    return null;
                }
            }
        }
    }

    public static void saveInventory(Player player, Byte profileID, PlayerInventory inv) {
        if (profileID == null)
            profileID = getCurrentProfile(player);
        if (profileID == null)
            return;
        Connection con = DbManager.getConnection();
        if (con == null)
            return;
        String sql = "UPDATE profileData SET inventory=? WHERE uuid=? AND profileID=?";

        try {
            JSONArray obj = new JSONArray();
            for (int i = 0; i < inv.getSize(); i++) {
                byte[] stack = inv.getItem(i).serializeAsBytes();
                obj.set(i, stack);
            }
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, obj.toJSONString());
            statement.setString(2, player.getUniqueId().toString());
            statement.setByte(3, profileID);
            statement.executeUpdate();
            DbManager.releaseConnection(con);


        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public static Integer getStat(Player player, Stats stat, Byte profileID) {
        if (profileID == null)
            profileID = getCurrentProfile(player);


        if (profileID == null)
            return 0;
        else {
            Connection con = DbManager.getConnection();
            if (con == null)
                return 0;
            else {
                String sql = "SELECT " + stat.name().toLowerCase() + " FROM profileData WHERE uuid=? AND profileID=?";

                try {
                    PreparedStatement statement = con.prepareStatement(sql);
                    statement.setString(1, player.getUniqueId().toString());
                    statement.setByte(2, profileID);
                    ResultSet result = statement.executeQuery();
                    DbManager.releaseConnection(con);
                    return result.getInt(1);
                } catch (Exception e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        }
    }

    public static void setStat(Player player, Byte profileID, Stats stat, int amount) {
        if (profileID == null)
            profileID = getCurrentProfile(player);
        if (profileID == null) return;
        Connection con = DbManager.getConnection();
        if (con != null) {
            String sql = "UPDATE profileData SET " + stat.name().toLowerCase() + "=? WHERE uuid=? AND profileID=?";
            try {
                PreparedStatement statement = con.prepareStatement(sql);
                statement.setInt(1, amount);
                statement.setString(2, player.getUniqueId().toString());
                statement.setByte(3, profileID);
                statement.executeUpdate();
                DbManager.releaseConnection(con);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    enum Classes {
        Warrior("&c", "- Deal quick melee damage\n&7- Naturally a tank", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTM5M2NlYzllYmJjNDFiMDk0ZTBkYmI3OWEzNzA1MzFmMmZiNjYyMjY2YzczNDNiYzg1ZDAxYjY2OGNkMDZmOCJ9fX0="),
        Archer("&b", "&7- Long ranged fast attacks\n&7- Naturally high critical rate", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTkzZWU1YmIwYzdmYWNjYTBmM2RmZTA5NDMwYzViODRhOTBlNjU4OGQwYTEwOTlkYTg1YjZlYWViODk1OGY5YSJ9fX0="),
        Mage("&d", "&7- Long ranged, but slow attacks\n&7- Deals area damage", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjkzMTk1MjU4MjVmMWYzMDcyN2ViOTQwZDNhMDY0MjZiYzRjZWMwN2ZiZDgwYWY1Y2QxNDZlM2ViMzg3OWY2OCJ9fX0="),
        Shaman("&a", "&7- Deal quick melee damage\n&7- Buff/debuff targets\n&7- Skill heavy", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2YyODE5Mjk1YzAxZTEyYTI1YTRjYWJjNzYwYTY5MDBjODM0MDFhZWQxZTI0N2Q0NGI2YjkyOTJkYzZjMTBkYSJ9fX0=");

        public String color;
        public String description;
        public String base64;

        Classes(String color, String description, String base64) {
            this.color = color;
            this.description = description;
            this.base64 = base64;
        }
    }

    enum Stats {
        Wisdom,
        Strength,
        Intelligence,
        Stamina,
        Dexterity;
    }
}
