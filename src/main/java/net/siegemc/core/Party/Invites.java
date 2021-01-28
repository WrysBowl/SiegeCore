package net.siegemc.core.Party;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Invites {
    private static HashMap<String, List<UUID>> invites = new HashMap<>();

    public static void addInvitee(String party, Player player){
        List<UUID> list = invites.getOrDefault(party, new ArrayList<>());
        list.add(player.getUniqueId());
        invites.put(party, list);
    }
}
