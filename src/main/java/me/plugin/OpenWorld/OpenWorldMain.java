package me.plugin.OpenWorld;

import me.plugin.Main;
import me.plugin.database;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class OpenWorldMain implements Listener {

    private boolean checkItemType(ItemStack playerItem) {
        //Iron tools
        if (playerItem.getType().equals(Material.IRON_SWORD)) {

            return true;

        } else if (playerItem.getType().equals(Material.IRON_SHOVEL)) {

            return true;

        } else if (playerItem.getType().equals(Material.IRON_HOE)) {

            return true;

        } else if (playerItem.getType().equals(Material.IRON_PICKAXE)) {

            return true;

        } else if (playerItem.getType().equals(Material.IRON_AXE)) {

            return true;
            //Diamond Tools
        } else if (playerItem.getType().equals(Material.DIAMOND_SWORD)) {

            return true;

        } else if (playerItem.getType().equals(Material.DIAMOND_SHOVEL)) {

            return true;

        } else if (playerItem.getType().equals(Material.DIAMOND_HOE)) {

            return true;

        } else if (playerItem.getType().equals(Material.DIAMOND_PICKAXE)) {

            return true;

        } else if (playerItem.getType().equals(Material.DIAMOND_AXE)) {

            return true;
            //Netherite Tools
        } else if (playerItem.getType().equals(Material.NETHERITE_SWORD)) {

            return true;

        } else if (playerItem.getType().equals(Material.NETHERITE_SHOVEL)) {

            return true;

        } else if (playerItem.getType().equals(Material.NETHERITE_HOE)) {

            return true;

        } else if (playerItem.getType().equals(Material.NETHERITE_PICKAXE)) {

            return true;

        } else if (playerItem.getType().equals(Material.NETHERITE_AXE)) {

            return true;
            //Iron Armour
        } else if (playerItem.getType().equals(Material.IRON_HELMET)) {

            return true;

        } else if (playerItem.getType().equals(Material.IRON_CHESTPLATE)) {

            return true;

        } else if (playerItem.getType().equals(Material.IRON_LEGGINGS)) {

            return true;

        } else if (playerItem.getType().equals(Material.IRON_BOOTS)) {

            return true;
            //Diamond Armour
        } else if (playerItem.getType().equals(Material.DIAMOND_HELMET)) {

            return true;

        } else if (playerItem.getType().equals(Material.DIAMOND_CHESTPLATE)) {

            return true;

        } else if (playerItem.getType().equals(Material.DIAMOND_LEGGINGS)) {

            return true;

        } else if (playerItem.getType().equals(Material.DIAMOND_BOOTS)) {

            return true;
            //Netherite Armour
        } else if (playerItem.getType().equals(Material.NETHERITE_HELMET)) {

            return true;

        } else if (playerItem.getType().equals(Material.NETHERITE_CHESTPLATE)) {

            return true;

        } else if (playerItem.getType().equals(Material.NETHERITE_LEGGINGS)) {

            return true;

        } else if (playerItem.getType().equals(Material.NETHERITE_BOOTS)) {

            return true;

        } else {

            return false;

        }
    }

    @EventHandler
    private void onBlockBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();

        ConfigurationSection playerData = database.get().getConfigurationSection(player.getUniqueId().toString());

        if (player.getWorld().getName().equalsIgnoreCase("World") || player.getWorld().getName().equalsIgnoreCase("World_Nether")) {

            if (!e.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasEnchant(Enchantment.SILK_TOUCH)) {

                if (e.getBlock().getType().equals(Material.DIAMOND_ORE)) {
                    //Diamond Ore
                    playerData.set("Coins", (Integer) playerData.get("Coins") + 10);
                    Main.updateScoreboard(player);
                    database.save();

                } else if (e.getBlock().getType().equals(Material.EMERALD_ORE)) {
                    //Emerald Ore
                    playerData.set("Coins", (Integer) playerData.get("Coins") + 14);
                    Main.updateScoreboard(player);
                    database.save();

                } else if (e.getBlock().getType().equals(Material.GOLD_ORE)) {
                    //Gold Ore
                    playerData.set("Coins", (Integer) playerData.get("Coins") + 6);
                    Main.updateScoreboard(player);
                    database.save();

                } else if (e.getBlock().getType().equals(Material.IRON_ORE)) {
                    //Iron Ore
                    playerData.set("Coins", (Integer) playerData.get("Coins") + 4);
                    Main.updateScoreboard(player);
                    database.save();

                } else if (e.getBlock().getType().equals(Material.ANCIENT_DEBRIS)) {

                    playerData.set("Coins", (Integer) playerData.get("Coins") + 10);
                    Main.updateScoreboard(player);
                    database.save();

                }

            }

        }


//        ItemStack playerItem = player.getInventory().getItemInMainHand();
//        ItemMeta toolMeta = playerItem.getItemMeta();
//        if (!toolMeta.isUnbreakable() && checkItemType(playerItem) == true) {
//            toolMeta.setUnbreakable(true);
//            toolMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_UNBREAKABLE});
//            playerItem.setItemMeta(toolMeta);
//        }

    }

    @EventHandler
    private void onEnvironmentDamage(EntityDamageEvent e) {



    }

    @EventHandler
    private void onMobAttack(EntityDamageByEntityEvent e) {

        if (e.getDamager() instanceof Player) {

//            if (e.getEntity() instanceof LivingEntity && !(e.getEntity() instanceof Player)) {
//
//                // Get the damage as a double
//                double dblDamage = e.getDamage()/2;
//
//                // Convert the damage to an integer
//                int Damage = (int) dblDamage;
//
//                Player attacker = ((Player) e.getDamager()).getPlayer();
//
//                ConfigurationSection attackerData = database.get().getConfigurationSection(attacker.getUniqueId().toString());
//
//                Integer attackerCoins = (Integer) attackerData.get("Coins");
//
//                attackerData.set("Coins",  attackerCoins + Damage);
//
//                database.save();
//                Main.updateScoreboard(attacker);
//
//            }

            if (e.getEntity() instanceof Player) {

                CombatLogSystem.addPlayer(((Player) e.getDamager()).getPlayer());
                CombatLogSystem.addPlayer(((Player) e.getEntity()).getPlayer());

            }

        }

//        Player player = (Player) e.getDamager();
//        ItemStack playerItem = player.getInventory().getItemInMainHand();
//        ItemMeta toolMeta = playerItem.getItemMeta();
//        if (!toolMeta.isUnbreakable() && checkItemType(playerItem) == true) {
//            toolMeta.setUnbreakable(true);
//            toolMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_UNBREAKABLE});
//            playerItem.setItemMeta(toolMeta);
//        }
    }

    @EventHandler
    private void onMobKill(EntityDeathEvent e) {

        LivingEntity entity = e.getEntity();
        EntityType type = entity.getType();

        if (isHostile(type) && entity.getKiller() instanceof  Player) {

            Player killer = entity.getKiller();

            int maxHealth = getEntityMaxHealth(entity);

            int reward = maxHealth/10;

            ConfigurationSection killerData = database.get().getConfigurationSection(killer.getUniqueId().toString());

            Integer killerCoins = (Integer) killerData.get("Coins");

            killerData.set("Coins",  killerCoins + reward);

            database.save();
            Main.updateScoreboard(killer);

        }

    }

    @EventHandler
    private void onPlayerAttack(PlayerItemDamageEvent e) {
        Player player = e.getPlayer();
        ItemStack playerItem = e.getItem();
        ItemMeta toolMeta = playerItem.getItemMeta();
        if (!toolMeta.isUnbreakable() && checkItemType(playerItem) == true) {
            toolMeta.setUnbreakable(true);
            toolMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_UNBREAKABLE});
            playerItem.setItemMeta(toolMeta);
        }

    }

    @EventHandler
    private void onKill(PlayerDeathEvent e) {

        if (e.getEntity().getKiller() != null && e.getEntity().getKiller() instanceof Player) {

            Player killed = e.getEntity().getPlayer();
            Player killer = e.getEntity().getKiller();

            // Whitelist Worlds
            World overworld = Bukkit.getWorld("world");
            World nether = Bukkit.getWorld("world_nether");
            World worldend = Bukkit.getWorld("world_the_end");

            for (Player p : overworld.getPlayers()) {
                p.sendMessage(ChatColor.RED + "[SERVER] " + ChatColor.LIGHT_PURPLE + killer.getDisplayName() + ChatColor.RED + " killed " + ChatColor.DARK_PURPLE + killed.getDisplayName());
            }
            for (Player p : nether.getPlayers()) {
                p.sendMessage(ChatColor.RED + "[SERVER] " + ChatColor.LIGHT_PURPLE + killer.getDisplayName() + ChatColor.RED + " killed " + ChatColor.DARK_PURPLE + killed.getDisplayName());
            }
            for (Player p : worldend.getPlayers()) {
                p.sendMessage(ChatColor.RED + "[SERVER] " + ChatColor.LIGHT_PURPLE + killer.getDisplayName() + ChatColor.RED + " killed " + ChatColor.DARK_PURPLE + killed.getDisplayName());
            }

            ConfigurationSection killedData = database.get().getConfigurationSection(killed.getUniqueId().toString());
            ConfigurationSection killerData = database.get().getConfigurationSection(killer.getUniqueId().toString());

            Integer killedCoins = (Integer) killedData.get("Coins");
            Integer killerCoins = (Integer) killerData.get("Coins");

            if (killedCoins > 1000) {

                Integer spoilsDifference = killedCoins - 1000;
                Integer spoils = (Integer) spoilsDifference / 2;

                killerData.set("Coins",  killerCoins + spoils);
                killedData.set("Coins", killedCoins - spoils);

                database.save();
                Main.updateScoreboard(killed);
                Main.updateScoreboard(killer);

            }

        }

    }

    public static int getEntityMaxHealth(Entity entity) {
        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) entity;
            return (int) livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        }
        return 0; // Return 0 for non-living entities or handle as needed
    }

    private boolean isHostile(EntityType type) {
        // List of hostile mobs in Minecraft 1.17.1
        switch (type) {
            case ZOMBIE:
            case SKELETON:
            case CREEPER:
            case SPIDER:
            case CAVE_SPIDER:
            case ENDERMAN:
            case WITCH:
            case WITHER:
            case ENDER_DRAGON:
            case SLIME:
            case SILVERFISH:
            case PHANTOM:
            case PILLAGER:
            case VINDICATOR:
            case EVOKER:
//            case ILLUSIONER: // Unused but still part of the game
            case GUARDIAN:
            case ELDER_GUARDIAN:
            case HOGLIN:
            case PIGLIN:
            case PIGLIN_BRUTE:
            case DROWNED:
            case HUSK:
            case STRAY:
            case ZOMBIFIED_PIGLIN:
            case BLAZE:
            case GHAST:
            case MAGMA_CUBE:
            case SHULKER:
            case VEX:
//            case DOLPHIN: // Aggressive when attacked
            case RAVAGER:
//            case STRIDER: // Only hostile when ridden by Zombified Piglin
                return true;
            default:
                return false;
        }
    }

}
