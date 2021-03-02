package net.siegemc.core.listeners;

import net.siegemc.core.Core;
import net.siegemc.core.items.ItemEnums.Drops;
import net.siegemc.core.utils.Levels;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;



public class BreakEvents implements Listener {

    @EventHandler
    public void breakEvent(BlockBreakEvent e) {
        Material block = e.getBlock().getType();
        BlockData blockData = e.getBlock().getBlockData();
        Location loc = e.getBlock().getLocation();
        Player player = e.getPlayer();

        if (player.getGameMode() == GameMode.CREATIVE) { return; }

        if (Drops.checkAllDrops(block) == null) {
            e.setCancelled(true);
            return;
        }

        e.setDropItems(false);

        Drops dropTable = new Drops();
        dropTable.giveBlockDrops(block, player);
        Short expReward = dropTable.getExpReward();

        if (expReward > 0) { Levels.addExp(player, expReward); } //Give exp reward

        for (ItemStack drop : dropTable.getItemRewards()) { //Loop through all drops
            e.getBlock().getWorld().dropItemNaturally(loc, drop);
        }

        Bukkit.getServer().getScheduler().runTaskLater(Core.plugin(), () -> {
            e.getBlock().setType(Material.BEDROCK);
        }, 1);

        //Will need to create a method of adding the blocks to a config file to prevent block loss in server crashes
        Bukkit.getServer().getScheduler().runTaskLater(Core.plugin(), () -> {
            loc.getBlock().setBlockData(blockData);
        }, dropTable.getBlockRegenDelay()); //Need to recheck to make sure regen time is properly made as a delay
    }
}
