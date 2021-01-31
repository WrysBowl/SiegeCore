package net.siegemc.core.items;

import net.siegemc.core.Core;
import net.siegemc.core.utils.Utils;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class SpawnItemCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            sender.sendMessage(Utils.tacc("&7Reloading item configurations..."));
            ItemConfig.fetchItems();
            sender.sendMessage(Utils.tacc("&aSuccessfully reloaded all item configurations!"));
            return true;
        }
        
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cThis command can only be executed ingame!");
            return true;
        }
        Player player = (Player) sender;
        
        Inventory inventory = Bukkit.createInventory(null, 9*6, "All Items");
        for (CustomItem item : Core.getItems().values()) {
            if (args.length > 0 && NumberUtils.isNumber(args[0])) { inventory.addItem(item.get(Integer.parseInt(args[0]))); }
            else { inventory.addItem(item.get()); }
        }
        
        player.openInventory(inventory);
        return true;
    }
}
