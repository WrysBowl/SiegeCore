package net.siegemc.core.informants;

import net.siegemc.core.Core;
import net.siegemc.core.party.Party;
import net.siegemc.core.utils.Utils;
import net.siegemc.core.utils.VaultHook;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;

import java.util.List;
import java.util.UUID;

public class Scoreboard {
    public void updateScoreboard(Player p) {
        org.bukkit.scoreboard.Scoreboard b = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective o = b.registerNewObjective("Title", "", Utils.tacc("&6SiegeRPG &7(" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers() + ")"));
        o.setDisplaySlot(DisplaySlot.SIDEBAR);
        //double levelPercent = (double) (Levels.getExp(p)/Levels.getExpCeiling(p)); //for score #13
        o.getScore(" ").setScore(15);
        o.getScore(Utils.tacc("&6Profile &7" + p.getName())).setScore(14);
        //o.getScore(Utils.tacc("&7╠ Level &5" + Levels.getLevel(p) + ChatColor.GRAY + "(" + ChatColor.LIGHT_PURPLE + levelPercent + "%" + ChatColor.GRAY + ")")).setScore(13);
        o.getScore(Utils.tacc("&7╠ &eGold " + (int) VaultHook.econ.getBalance(p) + "⛁")).setScore(12);
        o.getScore("  ").setScore(11);


        if (Core.getParty(p.getUniqueId()) == null) { // if player is not in dungeon or party
            o.getScore(Utils.tacc("&7╠ &4Strength " + Stats.getStrength(p))).setScore(10);
            o.getScore(Utils.tacc("&7╠ &cHealth " + Stats.getHealth(p))).setScore(9);
            o.getScore(Utils.tacc("&7╠ &6Regen " + Stats.getRegeneration(p))).setScore(8);
            o.getScore(Utils.tacc("&7╠ &aLuck " + Stats.getLuck(p))).setScore(7);
            o.getScore(Utils.tacc("&7╠ &dWisdom " + Stats.getWisdom(p))).setScore(6);
            o.getScore(Utils.tacc("&7╠ &9Tough " + Stats.getToughness(p))).setScore(5);
            p.setScoreboard(b);
        } else if (Core.getParty(p.getUniqueId()) != null) {
            Party party = Core.getParty(p.getUniqueId());
            List<UUID> UUIDMembers = party.getMembersRaw();

            for (UUID uuid : UUIDMembers) {
                Bukkit.getPlayer(UUID.fromString(String.valueOf(uuid))).getDisplayName();
                o.getScore(Utils.tacc("&7╠ &6" + +Stats.getStrength(p))).setScore(10);
            }
        }

    }
}
