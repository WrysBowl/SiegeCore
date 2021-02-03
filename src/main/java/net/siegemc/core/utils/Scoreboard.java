package net.siegemc.core.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;

public class Scoreboard {
    public void updateScoreboard(Player p) {
        org.bukkit.scoreboard.Scoreboard b = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective o = b.registerNewObjective("Gold", "", ChatColor.GOLD + "SiegeRPG");
        o.setDisplaySlot(DisplaySlot.SIDEBAR);
        o.getScore("").setScore(15);
        o.getScore(Utils.tacc(ChatColor.GOLD + "Profile " + VaultHook.perms.getPrimaryGroup(p) + " " + ChatColor.GRAY + p.getName())).setScore(14);
        double levelPercent = (double) (Levels.getExp(p)/Levels.getExpCeiling(p));
        o.getScore(ChatColor.GRAY + "  Level " + ChatColor.DARK_PURPLE + Levels.getLevel(p) + ChatColor.GRAY + "(" + ChatColor.LIGHT_PURPLE + levelPercent + "%" + ChatColor.GRAY + ")").setScore(13);
        o.getScore(ChatColor.GRAY + "  Gold " + ChatColor.YELLOW + VaultHook.econ.getBalance(p)).setScore(12);
        p.setScoreboard(b);
    }
}
