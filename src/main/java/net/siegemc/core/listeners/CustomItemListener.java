package net.siegemc.core.listeners;

import com.archyx.aureliumskills.api.AureliumAPI;
import net.siegemc.core.Core;
import net.siegemc.core.items.CreateItems.*;
import net.siegemc.core.utils.NBT;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"ConstantConditions", "deprecation"})
public class CustomItemListener implements Listener {

//    Yes, I know this class is very ugly
//    Feel free to change it if you want in the future :shrug:
    
    List<Player> cooldown = new ArrayList<>();
    
    @EventHandler(priority = EventPriority.HIGH)
    public void onDamage(EntityDamageByEntityEvent event) {
        // Strength
        if (event.getDamager() instanceof Arrow) {
            Arrow arrow = (Arrow) event.getDamager();
            double damage = NBT.getDouble(arrow, "damage");
            int perfectQuality = NBT.getInt(arrow, "perfectQuality");
            int strength = NBT.getInt(arrow, "strength");
            if (damage == 0) return;
            
            double add = CustomItem.calculateStatValue((int) Math.floor(strength), perfectQuality) * 0.25;
            event.setDamage(damage+add);
        } else if (event.getDamager() instanceof Player) {
            Player attacker = (Player) event.getDamager();
    
            ItemStack item = attacker.getInventory().getItemInMainHand();
            if (CustomItem.getType(item) == null || (CustomItem.getType(item) instanceof BowItem)) return;
            ItemMeta meta = item.getItemMeta();
            if (meta instanceof Damageable) {
                if (((Damageable) meta).getDamage() != 0) event.setCancelled(true);
            }
            double damage = CustomItem.getDamage(attacker, (LivingEntity) event.getEntity(), item);
            
            if(damage != 0) event.setDamage(damage);
            doAxeCooldown(attacker);
        }
        
        // Toughness
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            double toughness = 0;
    
            toughness += getStat(player.getInventory().getItemInMainHand(), Stat.TOUGHNESS, 0);
            toughness += getStat(player.getInventory().getHelmet(), Stat.TOUGHNESS, 0);
            toughness += getStat(player.getInventory().getChestplate(), Stat.TOUGHNESS, 0);
            toughness += getStat(player.getInventory().getLeggings(), Stat.TOUGHNESS, 0);
            toughness += getStat(player.getInventory().getBoots(), Stat.TOUGHNESS, 0);
            
            toughness += AureliumAPI.getStatLevel(player, com.archyx.aureliumskills.stats.Stat.TOUGHNESS);
            
            toughness = toughness * 0.3;
            event.setDamage(event.getDamage()-toughness);
        }
    }
    
    @EventHandler
    public void onLeftClick(PlayerInteractEvent event) {
        if (!event.getAction().equals(Action.LEFT_CLICK_BLOCK)
                && !event.getAction().equals(Action.LEFT_CLICK_AIR)) return;
        doAxeCooldown(event.getPlayer());
    }
    
    @EventHandler
    public void onOffhandSwap(PlayerSwapHandItemsEvent event) {
        updateHealth(event.getPlayer());
    }
    
    private void doAxeCooldown(Player player) {
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item == null) return;
        int slot = player.getInventory().getHeldItemSlot();
        if (!(CustomItem.getType(item) instanceof AxeItem)) return;
        ItemMeta meta = item.getItemMeta();
        if (!(meta instanceof Damageable)) return;
        Damageable damageable = (Damageable) meta;
        if (damageable.getDamage() != 0) return;
        if (CustomItem.getType(item) instanceof AxeItem) {
            AxeItem axeItem = (AxeItem) CustomItem.getType(item);
            new BukkitRunnable() {
                int i = 0;
                @Override
                public void run() {
                    i++;
                    double section = (double) item.getType().getMaxDurability() / (double) axeItem.getCooldown();
                    int progress = (int) Math.ceil(section * i);
                    if (section > item.getType().getMaxDurability()) progress = item.getType().getMaxDurability();
                    damageable.setDamage(item.getType().getMaxDurability() - progress);
                    item.setItemMeta((ItemMeta) damageable);
                    player.getInventory().setItem(slot, item);
                    if (i > axeItem.getCooldown()) {
                        damageable.setDamage(0);
                        item.setItemMeta((ItemMeta) damageable);
                        this.cancel();
                    }
                }
            }.runTaskTimer(Core.plugin(), 0, 1);
        }
    }
    
    @EventHandler
    public void onDamage(PlayerItemDamageEvent event) {
        if (CustomItem.getType(event.getItem()) == null) return;
        event.setCancelled(true);
    }
    
    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) return;
        ItemStack item = event.getItemDrop().getItemStack();
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;
        if (!(meta instanceof Damageable)) return;
        Damageable damageable = (Damageable) meta;
        if (damageable.getDamage() != 0) {
            event.setCancelled(true);
            event.getPlayer().sendMessage("§cUnable to drop an item that's on cooldown!");
        }
    }
    
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (player.getGameMode().equals(GameMode.CREATIVE)) return;
        if (event.getSlot() == (Integer) event.getSlot()) return;
        ItemStack item = event.getClickedInventory().getItem(event.getSlot());
        if (item == null) return;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;
        if (!(meta instanceof Damageable)) return;
        Damageable damageable = (Damageable) meta;
        if (damageable.getDamage() != 0) {
            event.setCancelled(true);
            player.sendMessage("§cUnable to drop an item that's on cooldown!");
        }
    }
    
    @EventHandler
    public void onShoot(EntityShootBowEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
    
        ItemStack item = player.getInventory().getItemInMainHand();
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;
    
        String key = NBT.getString(meta, "item");
        int perfectQuality = NBT.getInt(meta, "perfectQuality");
        if (key == null) return;
        
        CustomItem customItem = Core.getItems().get(key);
        if (customItem == null) return;
        if (customItem instanceof BowItem) {
            BowItem weapon = (BowItem) customItem;
            NBT.addDouble(event.getProjectile(), "damage", weapon.getDamage());
            NBT.addInt(event.getProjectile(), "perfectQuality", perfectQuality);
            NBT.addInt(event.getProjectile(), "strength", (int) (weapon.getStats().getOrDefault(Stat.STRENGTH, 0) + AureliumAPI.getStatLevel(player, com.archyx.aureliumskills.stats.Stat.STRENGTH)));
        }
    }
    
    @EventHandler
    public void onHotbarChange(PlayerItemHeldEvent event) {
        updateHealth(event.getPlayer());
    }
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked().getInventory() == null || event.getClickedInventory() == null) return;
        if (!event.getClickedInventory().equals(event.getWhoClicked().getInventory())) return;
        updateHealth((Player) event.getWhoClicked());
    }
    
    public void updateHealth(Player player) {
        new BukkitRunnable() {
            @SuppressWarnings("deprecation")
            @Override
            public void run() {
                double cHealth = player.getHealth();
                double health = getAllStats(player, Stat.HEALTH, 0);
                
                player.setMaxHealth(20);
                player.setMaxHealth(health+20);
                
                if (cHealth > health+20) cHealth = health+20;
                player.setHealth(cHealth);
            }
        }.runTaskLaterAsynchronously(Core.plugin(), 2);
    }
    
    public double getStat(ItemStack item, Stat stat) {
        return getStat(item, stat, 0);
    }
    
    public double getStat(ItemStack item, Stat stat, int def) {
        if (item == null) return def;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return def;
    
        String key = NBT.getString(meta, "item");
        int perfectQuality = NBT.getInt(meta, "perfectQuality");
        if (key == null) return def;
    
        CustomItem cItem = Core.getItems().get(key);
        int health = cItem.getStats().getOrDefault(stat, def);
        return CustomItem.calculateStatValue(health, perfectQuality) * 2.5;
    }
    
    public double getAllStats(Player player, Stat stat, int def) {
        double res = 0;
        res += getStat(player.getInventory().getItemInMainHand(), stat);
        res += getStat(player.getInventory().getHelmet(), stat);
        res += getStat(player.getInventory().getChestplate(), stat);
        res += getStat(player.getInventory().getLeggings(), stat);
        res += getStat(player.getInventory().getBoots(), stat);
        res += getStat(player.getInventory().getItemInOffHand(), stat);
        return res == 0 ? def : res;
    }
    
    @SuppressWarnings("deprecation")
    @EventHandler
    public void onConsume(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        
        if (!(CustomItem.getType(item) instanceof FoodItem)) return;
        FoodItem foodItem = (FoodItem) CustomItem.getType(item);
        event.setCancelled(true);
        player.getInventory().getItemInMainHand().setAmount(item.getAmount()-1);
        player.setFoodLevel(player.getFoodLevel()+foodItem.getFeed());
        double health = player.getHealth()+foodItem.getHeal();
        if (health > player.getMaxHealth()) health = player.getMaxHealth();
        player.setHealth(health);
    }
    
    @EventHandler
    public void onRegen(EntityRegainHealthEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        if (event.getRegainReason().equals(EntityRegainHealthEvent.RegainReason.EATING)) event.setCancelled(true);
        
        Player player = (Player) event.getEntity();
        
        double regen = getAllStats(player, Stat.REGENERATION, 1);
        regen += AureliumAPI.getStatLevel(player, com.archyx.aureliumskills.stats.Stat.REGENERATION);
    
        if (event.getRegainReason().equals(EntityRegainHealthEvent.RegainReason.SATIATED)) { regen = regen * 0.14; }
        else if (player.getFoodLevel() == 10) { regen = regen * 0.12; }
        else { regen = regen * 0.1; }
        event.setAmount(regen);
    }
    
    @EventHandler
    public void onDeathDrops(EntityDeathEvent event) {
        if (event.isCancelled() || event.getDrops().size() == 0 || !(event.getEntity().getKiller() instanceof Player)) return;
        Player player = event.getEntity().getKiller();
        
        double dropChance = getAllStats(player, Stat.LUCK, 0);
    
        dropChance += AureliumAPI.getStatLevel(player, com.archyx.aureliumskills.stats.Stat.LUCK);
        dropChance = dropChance * 0.5;
        
        // TODO: Finish later.
    }
    
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (!(event.getAction().equals(Action.RIGHT_CLICK_AIR)) &&
            !(event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) return;
        
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item == null) return;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;
        
        String key = NBT.getString(meta, "item");
        CustomItem customItem = Core.getItems().get(key);
        if (customItem == null) return;
        
        if (customItem instanceof WandItem) {
            WandItem wand = (WandItem) customItem;
            if (AureliumAPI.getMana(player) < wand.getManaRequired()) return;
            int range = wand.getRange();
            Location loc;
            Entity entity = player.getTargetEntity(range);
            if (entity == null || entity.isDead()) {
                Block block = player.getTargetBlock(range);
                if (block == null) return;
                loc = block.getLocation();
            }
            else { loc = entity.getLocation().add(0, entity.getHeight(), 0); }
            if (cooldown.contains(player)) return;
            cooldown.add(player);
            drawParticles(player.getLocation().add(0, player.getEyeHeight(), 0), loc, 255, 0, 255);
            for (LivingEntity e : loc.getNearbyLivingEntities(wand.getDamageRadius())) {
                if (e instanceof Player || e instanceof ArmorStand) continue;
                DamageIndicators.showIndicator(e, (int) Math.round(CustomItem.getDamage(player, e, item)), false);
                e.damage(CustomItem.getDamage(player, e, item));
                NBT.addString(e, "attacker", NBT.serializePlayer(player));
            }
            AureliumAPI.setMana(player, AureliumAPI.getMana(player) - wand.getManaRequired());
            new BukkitRunnable() {
                @Override
                public void run() { cooldown.remove(player); }
            }.runTaskLaterAsynchronously(Core.plugin(), 2);
        }
    }
    
    private void drawParticles(Location aL, Location bL, int r, int g, int b) {
        new Thread(() -> {
            int i = 0;
            if (aL.getWorld() == null || bL.getWorld() == null || !aL.getWorld().equals(bL.getWorld())) return;
            double distance = aL.distance(bL);
            Vector p1 = aL.toVector();
            Vector p2 = bL.toVector();
            Vector vector = p2.clone().subtract(p1).normalize().multiply(0.2);
            double length = 0;
            for (; length < distance; p1.add(vector)) {
                i++;
                Location loc = p1.toLocation(aL.getWorld());
                aL.getWorld().spawnParticle(Particle.REDSTONE, loc, 0, 0, 0, 0, 1, new Particle.DustOptions(Color.fromRGB(r,g,b), 1));
                length += 0.2;
                try {
                    if (i % 10 == 0) Thread.sleep(50);
                } catch (InterruptedException ignored) {}
            }
        }).start();
        
    }
}
