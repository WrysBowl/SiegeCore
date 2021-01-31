package net.siegemc.core.party;

import lombok.Getter;
import net.siegemc.core.Core;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Party {
    @Getter private UUID partyID = UUID.randomUUID();
    @Getter private final List<UUID> invited = new ArrayList<>();
    private List<UUID> members = new ArrayList<>();
    private UUID leader = null;
    
    public Party(Player leader) {
        this.setLeader(leader.getUniqueId());
        save();
    }
    
    public Party(UUID partyID) {
        String leader = Core.getPartyConfig().getString("party."+partyID.toString()+".leader");
        List<String> members = Core.getPartyConfig().getStringList("party."+partyID.toString()+".members");
        if (leader == null) {
            Core.plugin().getLogger().warning("Failed to fetch data for party ID "+partyID.toString());
            return;
        }
        
        UUID partyLeader = UUID.fromString(leader);
        List<UUID> membersConverted = new ArrayList<>();
        members.forEach((String u) -> membersConverted.add(UUID.fromString(u)));
        
        this.partyID = partyID;
        this.setLeader(partyLeader);
        for (UUID uuid : membersConverted) addMember(uuid);
    }
    
    public OfflinePlayer getLeader() { return Bukkit.getOfflinePlayer(leader); }
    
    public void addMember(UUID memberUUID) {
        members.add(memberUUID);
        save();
    }
    
    public void removeMember(UUID memberUUID) {
        members.remove(memberUUID);
        save();
    }
    
    public List<OfflinePlayer> getMembers() {
        List<OfflinePlayer> list = new ArrayList<>();
        list.add(getLeader());
        for (UUID uuid : members) list.add(Bukkit.getOfflinePlayer(uuid));
        return list;
    }
    
    public List<UUID> getMembersRaw() {
        List<UUID> list = new ArrayList<>();
        list.add(leader);
        list.addAll(members);
        return list;
    }
    
    public boolean isMember(UUID playerUUID) {
        boolean isIn = getMembersRaw().contains(playerUUID);
        return isIn;
    }
    
    public void addInvite(Player invitee) {
        invited.add(invitee.getUniqueId());
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!isInvited(invitee)) return;
                removeInvite(invitee);
                send(invitee.getName()+" did not join the party!");
                invitee.sendMessage("§cParty invite from "+getLeader().getName()+" has expired!");
                if (members.size() == 0) {
                    send("The party was disbanded since there was only 1 person left!");
                    disband();
                }
            }
        }.runTaskLaterAsynchronously(Core.plugin(), 1200);
    }
    
    public void removeInvite(Player invitee) {
        invited.remove(invitee.getUniqueId());
    }
    
    public boolean isInvited(Player invitee) { return invited.contains(invitee.getUniqueId()); }
    
    public void setLeader(UUID leader) {
        if (this.leader != null) {
            Core.getParties().remove(this.leader);
            this.addMember(this.leader);
        }
        this.leader = leader;
        if (members.contains(leader)) this.removeMember(leader);
        Core.getParties().put(leader, this);
        save();
    }
    
    public void disband() {
        Core.getParties().remove(leader);
        this.members.clear();
        this.invited.clear();
        Core.getPartyConfig().set("party."+partyID.toString(), null);
        Core.plugin().savePartyData();
    }
    
    public void leave(Player player) {
        if (isMember(player.getUniqueId())) removeMember(player.getUniqueId());
        if (getLeader() == player) disband();
        send(player.getName()+" left the party!");
    }
    
    public void send(String message) {
        for(OfflinePlayer player : getMembers()) {
            if (player.isOnline()) {
                ((Player) player).sendMessage("§b[PARTY] §7"+message);
            }
        }
    }
    
    public void save(boolean save) {
        List<String> membersString = new ArrayList<>();
        members.forEach((UUID u) -> membersString.add(u.toString()));
        Core.getPartyConfig().set("party."+partyID.toString()+".id", getPartyID().toString());
        Core.getPartyConfig().set("party."+partyID.toString()+".leader", leader.toString());
        Core.getPartyConfig().set("party."+partyID.toString()+".members", membersString);
        if (save) Core.plugin().savePartyData();
    }
    
    public void save() {
        save(false);
    }
}
