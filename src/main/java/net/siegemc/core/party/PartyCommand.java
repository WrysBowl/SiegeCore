package net.siegemc.core.party;

import net.siegemc.core.Core;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PartyCommand implements CommandExecutor {
    
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String cmd, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cYou can not use this command on console!");
            return true;
        }
        Player player = (Player) sender;
        Party party = Core.getParty(player.getUniqueId());
        
        if (args.length < 1 || args[0].equalsIgnoreCase("help")) {
            player.sendMessage("§b/" + cmd + " help");
            player.sendMessage("§b/" + cmd + " list");
            player.sendMessage("§b/" + cmd + " invite <player>");
            player.sendMessage("§b/" + cmd + " accept <player>");
            player.sendMessage("§b/" + cmd + " kick <player>");
            player.sendMessage("§b/" + cmd + " promote <player>");
            player.sendMessage("§b/" + cmd + " leave");
        } else if (args[0].equalsIgnoreCase("list")) {
            if (party == null) {
                player.sendMessage("§cYou are not in a party!");
                return true;
            }
            player.sendMessage("§7All players in the party:");
            player.sendMessage("§8- " + party.getLeader().getName() + " §7§o(Leader)");
            for (OfflinePlayer member : party.getMembers()) player.sendMessage("§8- " + member.getName());
        } else if (args[0].equalsIgnoreCase("invite")) {
            if (args.length < 2) {
                player.sendMessage("§c/" + cmd + " " + args[0] + " <player>");
                return true;
            }
            Player invitee = Bukkit.getPlayer(args[1]);
            if (invitee == null) {
                player.sendMessage("§cUnable to find this player!");
                return true;
            }
            if (Core.getParty(invitee.getUniqueId()) != null) {
                player.sendMessage("§cThis player is already in a party!");
                return true;
            }
            if (party == null) party = new Party(player);
            if (party.getLeader() != player) {
                player.sendMessage("§cYou need to be the party leader to do this!");
                return true;
            }
            if (party.isInvited(invitee)) {
                player.sendMessage("§cThis player is already invited to the party!");
                return true;
            }
            party.addInvite(invitee);
            party.send(invitee.getDisplayName() + " was invited to the party!");
            invitee.sendMessage("§7You were invited to §b" + player.getName() + "§7's party!");
            invitee.sendMessage("§7You can join use §b/party join "+player.getName()+"§7.");
        } else if (args[0].equalsIgnoreCase("kick")) {
            if (args.length < 2) {
                player.sendMessage("§c/" + cmd + " " + args[0] + " <player>");
                return true;
            }
            if (party == null) {
                player.sendMessage("§cYou are not currently in a party!");
                return true;
            }
            if (party.getLeader() != player) {
                player.sendMessage("§cYou need to be the party leader to do this!");
                return true;
            }
            OfflinePlayer kick = Bukkit.getOfflinePlayer(args[1]);
            if (!party.isMember(kick.getUniqueId())) {
                player.sendMessage("§cThis player is not a member of the party!");
                return true;
            }
            if (party.getLeader() != kick) {
                player.sendMessage("§cYou can not kick yourself from the party!");
                return true;
            }
            party.removeMember(kick.getUniqueId());
            party.send(kick.getName() + " was kicked from the party!");
            if (kick.isOnline()) ((Player) kick).sendMessage("§cYou were kicked from the party!");
        } else if (args[0].equalsIgnoreCase("accept")) {
            if (args.length < 2) {
                player.sendMessage("§c/" + cmd + " " + args[0] + " <player>");
                return true;
            }
            if (party != null) {
                player.sendMessage("§cYou are currently in a party!");
                return true;
            }
            Player invited = Bukkit.getPlayer(args[1]);
            if (invited == null || Core.getParties().get(invited.getUniqueId()) == null) {
                player.sendMessage("§cUnable to find this party! Make sure the player is online and is in a party.");
                return true;
            }
            Party joining = Core.getParty(invited.getUniqueId());
            if (joining == null) {
                player.sendMessage("§cAn unexpected error has occurred! Please contact an administrator regarding this. I wonder how this happened, regardless it's probably Elastios' fault!");
                return true;
            }
            if (!joining.isInvited(player)) {
                player.sendMessage("§cYou are not invited to this party!");
                return true;
            }
            joining.removeInvite(player);
            joining.addMember(player.getUniqueId());
            joining.send(player.getName() + " has joined the party!");
        } else if (args[0].equalsIgnoreCase("promote")) {
            if (args.length < 2) {
                player.sendMessage("§c/" + cmd + " " + args[0] + " <player>");
                return true;
            }
            if (party == null) {
                player.sendMessage("§cYou are currently in a party!");
                return true;
            }
            if (party.getLeader() != player) {
                player.sendMessage("§cYou are not the party leader!");
                return true;
            }
            Player promote = Bukkit.getPlayer(args[1]);
            if (promote == null) {
                player.sendMessage("§cThis player is not online!");
                return true;
            }
            if (!party.isMember(promote.getUniqueId())) {
                player.sendMessage("§cThis player is not in the party!");
                return true;
            }
            if (player == promote) {
                player.sendMessage("§cYou can not promote yourself!");
                return true;
            }
            party.setLeader(promote.getUniqueId());
        } else if (args[0].equalsIgnoreCase("leave")
                || args[0].equalsIgnoreCase("disband")) {
            if (party == null) {
                player.sendMessage("§cYou are currently in a party!");
                return true;
            }
            if (args[0].equalsIgnoreCase("disband") && party.getLeader() != player) {
                player.sendMessage("§cYou are not the party leader!");
                return true;
            }
            party.leave(player);
        } else {
            player.sendMessage("§cInvalid argument! Try using \"/"+cmd+" help\" for help!");
        }
        return true;
    }
    
}