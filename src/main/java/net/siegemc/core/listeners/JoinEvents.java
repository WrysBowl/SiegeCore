package net.siegemc.core.listeners;

import net.siegemc.core.Core;
import net.siegemc.core.dungeons.Dungeon;
import net.siegemc.core.dungeons.DungeonType;
import net.siegemc.core.utils.DbManager;
import net.siegemc.core.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JoinEvents implements Listener {
    @EventHandler
    public void connectEvent(AsyncPlayerPreLoginEvent e) {
        new BukkitRunnable() { // We create a runnable to run asynchronously (on another thread, not the main one, so that the server won't lag if this one does)
            @SuppressWarnings("ConstantConditions")
            @Override
            public void run() {
                String ip = e.getAddress().getHostAddress();
                Connection conn = DbManager.getConnection(); // Get the connection from the pool
                // Add user ips to the db (So that we can in the future find all alts of an user)
                try {
                    PreparedStatement stat = conn.prepareStatement("SELECT * FROM ipData WHERE uuid=? AND ip=?");
                    stat.setString(1, e.getUniqueId().toString());
                    stat.setString(2, ip);
                    ResultSet set = stat.executeQuery();
                    if (set.getFetchSize() < 1) {
                        PreparedStatement statement = conn.prepareStatement("INSERT INTO ipData (uuid, ip) VALUES (?, ?)");
                        statement.setString(1, e.getUniqueId().toString());
                        statement.setString(2, ip);
                        statement.executeUpdate();
                    }
                    // Add the user to the db if he doesn't exist
                    PreparedStatement userData = conn.prepareStatement("INSERT INTO userData (uuid) VALUES (?)");
                    userData.setString(1, e.getUniqueId().toString());
                    userData.executeUpdate();
                } catch (SQLException ignored) {
                }
                DbManager.releaseConnection(conn); // Release the connection so it can be used by others
            }
        }.runTaskAsynchronously(Core.plugin());
    }

    @EventHandler
    public void joinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PersistentDataContainer container = player.getPersistentDataContainer();
        PersistentDataContainer dungeonContainer = container.get(Utils.namespacedKey("dungeon"), PersistentDataType.TAG_CONTAINER);
        if (dungeonContainer != null) {
            String dungeonType = dungeonContainer.get(Utils.namespacedKey("type"), PersistentDataType.STRING);
            if (dungeonType != null) {
                DungeonType type = DungeonType.valueOf(dungeonType);
                Integer index = dungeonContainer.get(Utils.namespacedKey("index"), PersistentDataType.INTEGER);
                if (type.dungeons.contains(index)) {
                    Dungeon dungeon = type.dungeons.get(index);

                    if (!dungeon.listPlayers().contains(player)) {
                        container.set(Utils.namespacedKey("dungeon"), PersistentDataType.TAG_CONTAINER, container.getAdapterContext().newPersistentDataContainer());
                        player.teleport(Core.spawnLocation);
                    } else if (player.getWorld().getName() != "dungeons" || player.getLocation().distance(dungeon.location) > type.dungeonDistance)
                        player.teleport(dungeon.location.add(type.spawnLocation));

                }
            }
        }
    }
}

