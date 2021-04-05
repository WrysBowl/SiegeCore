package net.siegemc.core.portals

import net.siegemc.core.Core
import net.siegemc.core.dungeons.DungeonType
import net.siegemc.core.party.Party
import net.siegemc.core.utils.ConfigurationBase
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player
import java.io.File

open class PortalConfig(plugin: Core) : ConfigurationBase((File(plugin.dataFolder, "portal.yml"))) {

    fun teleportToCorresponding(player: Player): Boolean {
        val coordinateSection = configuration.getConfigurationSection("coords") ?: configuration.createSection("coords")
        val linkingSection =
            configuration.getConfigurationSection("relations") ?: configuration.createSection("relations")
        val location = linkingSection.getConfigurationSection(
            coordinateSection.getLong(
                serializeLocation(player.location.block.location)
            )
                .toString()
        ) ?: return false
        if (location.isSet("dungeon")) {
            val dungeonTypeName = location.getString("dungeon")
            val dungeonType = DungeonType.dungeonTypes.find { d -> dungeonTypeName == d.name } ?: return false
            for (dungeon in dungeonType.dungeons) {
                if (dungeon.listPlayers().contains(player)) {
                    val endLocation = dungeon.location.clone().add(dungeonType.spawnLocation)
                    player.teleport(endLocation)
                    Party.parties.forEach { party ->
                        if (party.leader == player)
                            party.members.forEach { member ->
                                if (member.isOnline)
                                    (member as Player).teleport(endLocation)
                            }
                        return true
                    }
                    return true
                }
            }
            val dungeon = dungeonType.nextAvailableDungeon()
            val endLocation = dungeon.location.clone().add(dungeonType.spawnLocation)
            player.teleport(endLocation)
            Party.parties.forEach { party ->
                if (party.leader == player)
                    party.members.forEach { member ->
                        if (member.isOnline)
                            (member as Player).teleport(endLocation)
                    }
                return true
            }
            return true
        } else {
            val actualLocation = Location(
                Bukkit.getWorld(location.getString("world")!!),
                location.getDouble("x"),
                location.getDouble("y"),
                location.getDouble("z")
            )
            player.teleport(actualLocation)
            return true
        }
    }

    private fun serializeLocation(loc: Location): String {
        return "${loc.x};${loc.y};${loc.z};${loc.world.name}"
    }

    fun addCoordinate(blockLoc: Location, endingLoc: Location) {
        val coordinateSection = configuration.getConfigurationSection("coords") ?: configuration.createSection("coords")
        val linkingSection =
            configuration.getConfigurationSection("relations") ?: configuration.createSection("relations")
        // Checks the existing locations to see if there's already a location like that
        for (key in linkingSection.getKeys(false)) {
            val location = linkingSection.getConfigurationSection(key) ?: linkingSection.createSection(key)
            if (location.isSet("dungeon")) continue
            val actualLocation = Location(
                Bukkit.getWorld(location.getString("world")!!),
                location.getDouble("x"),
                location.getDouble("y"),
                location.getDouble("z")
            )
            // If it found it then gg
            if (actualLocation == endingLoc) {
                coordinateSection.set(serializeLocation(blockLoc), key.toLong())
                return
            }
        }
        val index = linkingSection.getKeys(false).size
        coordinateSection.set(serializeLocation(blockLoc), index)
        val locationSection = linkingSection.createSection(index.toString()).apply {
            set("world", endingLoc.world.name)
            set("x", endingLoc.x)
            set("y", endingLoc.y)
            set("z", endingLoc.z)

        }
        linkingSection.set(index.toString(), locationSection)
        save()
    }

    fun addCoordinate(blockLoc: Location, dungeonType: DungeonType) {
        val coordinateSection = configuration.getConfigurationSection("coords") ?: configuration.createSection("coords")
        val linkingSection =
            configuration.getConfigurationSection("relations") ?: configuration.createSection("relations")
        // Checks the existing locations to see if there's already a location like that
        for (key in linkingSection.getKeys(false)) {
            val location = linkingSection.getConfigurationSection(key) ?: linkingSection.createSection(key)
            if (!location.isSet("dungeon")) continue
            if (dungeonType.name == location.getString("dungeon")) {
                coordinateSection.set(serializeLocation(blockLoc), key.toLong())
                return
            }
        }
        val index = linkingSection.getKeys(false).size
        coordinateSection.set(serializeLocation(blockLoc), index)
        val locationSection = linkingSection.createSection(index.toString()).apply {
            set("dungeon", dungeonType.name)
        }
        linkingSection.set(index.toString(), locationSection)
        save()
    }
}