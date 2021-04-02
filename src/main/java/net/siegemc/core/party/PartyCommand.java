package net.siegemc.core.party;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import net.siegemc.core.Core;
import net.siegemc.core.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//@CommandAlias("p|party")
//class ACFPartyCommand extends BaseCommand {
//
//    @Default
//    @Subcommand("help")
//    public void onHelp(Player player) {
//        player.sendMessage(Utils.parse("<dark_aqua>Party Help" +
//                "<aqua>/p help\n" +
//                "<aqua>/p list\n" +
//                "<aqua>/p invite <player>\n" +
//                "<aqua>/p accept <player>\n" +
//                "<aqua>/p kick <player>\n" +
//                "<aqua>/p promote <player>\n" +
//                "<aqua>/p leave\n"));
//        if (player.hasPermission("siege.party.admin")) {
//            player.sendMessage(Utils.parse("<red>/p forceleader [player]\n" +
//                    "<red>/p forcejoin <player>"));
//        }
//    }
//
//    @Subcommand("list")
//    public void onList(Player player) {
//        Party party = Core.getParty(player.getUniqueId());
//        if (party == null) {
//            player.sendMessage(Utils.parse("<red>You are not in a party!"));
//            return;
//        }
//        player.sendMessage(Utils.parse("<gray>All players in the party:\n" +
//                "<dark_gray>- <aqua>\" + party.getLeader().getName() + \" <gray><i>(Leader)"));
//        for (OfflinePlayer member : party.getMembers()) if (party.getLeader() != member) player.sendMessage("<gray>- <aqua>" + member.getName());
//    }
//
//    @Subcommand("invite")
//    @CommandCompletion("@openToInvite")
//    public void onInvite(Player player, OnlinePlayer target) {
//    }
//
//}

public class PartyCommand implements CommandExecutor, TabCompleter {

    // TODO(CONVERT TO ACF!!!!!!!!!)
    
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
            if (player.hasPermission("parties.admin")) {
                player.sendMessage("§c/" + cmd + " forceleader [player]");
                player.sendMessage("§c/" + cmd + " forcejoin <player>");
            }
        } else if (args[0].equalsIgnoreCase("list")) {
            if (party == null) {
                player.sendMessage("§cYou are not in a party!");
                return true;
            }
            player.sendMessage("§7All players in the party:");
            player.sendMessage("§8- §b" + party.getLeader().getName() + " §7§o(Leader)");
            for (OfflinePlayer member : party.getMembers()) if (party.getLeader() != member) player.sendMessage("§8- §b" + member.getName());
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
            if (invitee == player) {
                player.sendMessage("§cYou can not invite yourself!");
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
            if (party.getLeader() == kick) {
                player.sendMessage("§cYou can not kick yourself from the party!");
                return true;
            }
            party.removeMember(kick.getUniqueId());
            party.send(kick.getName() + " was kicked from the party!");
            if (kick.isOnline()) ((Player) kick).sendMessage("§cYou were kicked from the party!");
        } else if (args[0].equalsIgnoreCase("accept")
                || args[0].equalsIgnoreCase("join")) {
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
            party.send(promote.getName()+" has been promoted to party leader!");
        } else if (args[0].equalsIgnoreCase("leave")
                || args[0].equalsIgnoreCase("disband")) {
            if (party == null) {
                player.sendMessage("§cYou are currently not in a party!");
                return true;
            }
            if (args[0].equalsIgnoreCase("disband") && party.getLeader() != player) {
                player.sendMessage("§cYou are not the party leader!");
                return true;
            }
            party.leave(player);
            player.sendMessage("§cYou have left the party!");
            if (party.getMembers().size() == 1) {
                party.send("The party was disbanded since there was only 1 person left!");
                party.disband();
            }
        } else if (args[0].equalsIgnoreCase("forceleader")) {
            if (!player.hasPermission("parties.admin")) return true;
            if (party == null) {
                player.sendMessage("§cYou are currently not in a party!");
                return true;
            }
            if (args.length < 2) {
                party.setLeader(player.getUniqueId());
                player.sendMessage("§4[ADMIN] §7You made yourself the party leader!");
            } else {
                Player leader = Bukkit.getPlayer(args[1]);
                if (leader == null) {
                    player.sendMessage("§cUnknown player!");
                    return true;
                }
                party.setLeader(leader.getUniqueId());
                player.sendMessage("§4[ADMIN] §7You made " + leader.getName() + " the party leader!");
            }
        } else if (args[0].equalsIgnoreCase("forcejoin")) {
            if (!player.hasPermission("parties.admin")) return true;
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
                player.sendMessage("§c(Error) Well now, this was unexpected. Please contact a developer regarding this issue.");
                return true;
            }
            joining.removeInvite(player);
            joining.addMember(player.getUniqueId());
            joining.send(player.getName() + " has broken into the party!");
        } else {
            player.sendMessage("§cInvalid argument! Try using \"/"+cmd+" help\" for help!");
        }
        return true;
    }
    
    @SuppressWarnings("ConstantConditions")
    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length < 2 || !(sender instanceof Player)) return Arrays.asList("help", "list", "invite", "accept", "kick", "promote", "leave");
        Player player = (Player) sender;
        String argument = args[0].toLowerCase();
        List<String> complete = new ArrayList<>();
        if (argument.equals("invite") || argument.equals("accept")) {
            for (Player p : Bukkit.getOnlinePlayers()) complete.add(p.getDisplayName());
        } else if (Core.getParty(player.getUniqueId()) != null && (argument.equals("kick") || argument.equals("promote"))) {
            for (OfflinePlayer p : Core.getParty(player.getUniqueId()).getMembers()) complete.add(p.getName());
        }
        if (args.length > 2) complete.clear();
        return complete;
    }
}