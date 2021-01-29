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
    @Getter private final UUID partyID = UUID.randomUUID();
    @Getter private final List<UUID> invited = new ArrayList<>();
    private final List<UUID> members = new ArrayList<>();
    private UUID leader = null;
    
    public Party(Player leader) {
        this.setLeader(leader.getUniqueId());
    }
    
    public OfflinePlayer getLeader() { return Bukkit.getOfflinePlayer(leader); }
    
    public void addMember(UUID memberUUID) {
        members.add(memberUUID);
    }
    
    public void removeMember(UUID memberUUID) {
        members.remove(memberUUID);
    }
    
    public List<OfflinePlayer> getMembers() {
        List<OfflinePlayer> list = new ArrayList<>();
        list.add(getLeader());
        for (UUID uuid : members) list.add(Bukkit.getOfflinePlayer(uuid));
        return list;
    }
    
    public boolean isMember(UUID playerUUID) { return members.contains(playerUUID) || leader == playerUUID; }
    
    public void addInvite(Player invitee) {
        invited.add(invitee.getUniqueId());
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!isInvited(invitee)) return;
                removeInvite(invitee);
                send(invitee.getName()+" did not join the party!");
                invitee.sendMessage("§cParty invite from "+getLeader().getName()+" has expired!");
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
        this.removeMember(leader);
        Core.getParties().put(leader, this);
    }
    
    public void disband() {
        Core.getParties().remove(leader);
        this.members.clear();
        this.invited.clear();
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
}
