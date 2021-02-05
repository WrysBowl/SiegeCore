package net.siegemc.core.informants;

import net.siegemc.core.stats.Stats;
import net.siegemc.core.utils.Utils;
import net.siegemc.core.utils.VaultHook;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;

public class Scoreboard {
    org.bukkit.scoreboard.Scoreboard b = Bukkit.getScoreboardManager().getNewScoreboard();
    Objective o = b.registerNewObjective("Title", "", Utils.tacc("&6SiegeRPG &7(" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers() + ")"));
    public void updateScoreboardRegular(Player p) {
        o.setDisplaySlot(DisplaySlot.SIDEBAR);
        //double levelPercent = (double) (Levels.getExp(p)/Levels.getExpCeiling(p)); //for score #13
        o.getScore(" ").setScore(15);
        o.getScore(Utils.tacc("&6Profile &7" + p.getName())).setScore(14);
        //o.getScore(Utils.tacc("&7╠ Level &5" + Levels.getLevel(p) + ChatColor.GRAY + "(" + ChatColor.LIGHT_PURPLE + levelPercent + "%" + ChatColor.GRAY + ")")).setScore(13);
        o.getScore(Utils.tacc("&7╠ &eGold " + (int) VaultHook.econ.getBalance(p) + "⛁")).setScore(12);
        p.setScoreboard(b);
    }
    public void updateScoreboardNoParty(Player p) {
        o.setDisplaySlot(DisplaySlot.SIDEBAR);
        o.getScore(Utils.tacc("&7╠ &cStrength " + Stats.getStrength(p))).setScore(11);
        p.setScoreboard(b);
    }
}
