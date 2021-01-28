package net.siegemc.core.Party;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.UUID;

public class Party implements CommandExecutor {
    private ArrayList<UUID> players = new ArrayList<UUID>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length >= 1) {
                String inviteeName = args[0];
                Player invitee = Bukkit.getPlayer(inviteeName);

                if (invitee != null) {
                    if (players.contains(invitee.getUniqueId())) {
                        sender.sendMessage("That player is already in your party!");
                    } else {
                        players.add(invitee.getUniqueId());
                    }

                    return true;
                }
            }
        }
        return false;
    }
}
