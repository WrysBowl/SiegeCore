package net.siegemc.core.informants;

import net.siegemc.core.utils.Utils;
import net.siegemc.core.utils.VaultHook;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Tablist {
    public void tablistUpdate() {
        for (Player p : Bukkit.getOnlinePlayers()) {

            // TODO(CHANGE TO MINIMESSAGE)

            String header = Utils.tacc("\n" +
                    "&6&lSiegeRPG\n" +
                    "&6&oplay.SiegeRPG.net" +
                    "&f\n" +
                    "");

            String footer = Utils.tacc("\n" +
                    "&6Discord: &7/discord\n" +
                    "&6Website: &7/website\n" +
                    "&7There are &6&n"+Bukkit.getOnlinePlayers().size()+"&7 players online!\n" +
                    "");
            String tabName = Utils.tacc(VaultHook.perms.getPrimaryGroup(p) + " " + ChatColor.GRAY + p.getName());
            p.setPlayerListName(tabName);
            p.setPlayerListHeader(header);
            p.setPlayerListFooter(footer);
        }
    }
}
