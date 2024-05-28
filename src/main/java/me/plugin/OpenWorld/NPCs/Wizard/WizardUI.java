package me.plugin.OpenWorld.NPCs.Wizard;

import me.plugin.database;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class WizardUI implements Listener {

    private static Map<Enchantment, Integer> enchantmentCosts = null;

    private static final int enchantmentRemovalCost = 50; // Example cost for removing any enchantment

    public WizardUI(Map<Enchantment, Integer> enchantmentCosts) {
        this.enchantmentCosts = enchantmentCosts;
    }

    public static void openWizardGUI(Player player) {
        // Create wizard GUI
        Inventory gui = Bukkit.createInventory(null, 9, "Wizard's Enchantments");

        // Populate GUI with enchantments and costs
        for (Map.Entry<Enchantment, Integer> entry : enchantmentCosts.entrySet()) {
            ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
            item.addUnsafeEnchantment(entry.getKey(), 1);
            item.getItemMeta().setDisplayName(String.valueOf(entry.getKey().getKey()));
            gui.addItem(item);
        }

        // Open GUI for the player
        player.openInventory(gui);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();

        if (event.getView().getTitle().equals("Wizard's Enchantments")) {
            event.setCancelled(true);
            ItemStack clickedItem = event.getCurrentItem();

            if (clickedItem != null && clickedItem.getType() != Material.AIR) {
                // Get the item selected by the player
                ItemStack selected = player.getItemInHand();

                if (selected != null && selected.getType() != Material.AIR) {
                    Enchantment enchantment = getRandomEnchantment();
                    int addCost = enchantmentCosts.getOrDefault(enchantment, 0);
                    int removeCost = calculateRemoveEnchantCost(selected);

                    // Total cost including both adding and removing enchantments
                    int totalCost = addCost + removeCost;

                    if (hasEnoughCoins(player, totalCost)) {
                        // Deduct coins for both removing and adding enchantments
                        deductCoins(player, totalCost);

                        // Remove existing enchantments
                        removeEnchantments(selected);

                        // Apply new enchantment
                        selected.addUnsafeEnchantment(enchantment, 1);
                        player.sendMessage("Item enchanted successfully with " + enchantment.getKey().getKey() + " for " + totalCost + " coins!");
                    } else {
                        player.sendMessage("You don't have enough coins to purchase these enchantments!");
                    }

                    // Close the GUI
                    player.closeInventory();
                } else {
                    player.sendMessage("Please select an item to enchant!");
                }
            }
        }
    }

    private boolean hasEnoughCoins(Player player, int cost) {
        ConfigurationSection playerData = database.get().getConfigurationSection(player.getUniqueId().toString());
        int playerCoins = playerData.getInt("Coins");
        return playerCoins >= cost;
    }

    private void deductCoins(Player player, int cost) {
        ConfigurationSection playerData = database.get().getConfigurationSection(player.getUniqueId().toString());
        int playerCoins = playerData.getInt("Coins");
        playerData.set("Coins", playerCoins - cost);
        database.save();
    }

    private Enchantment getEnchantmentFromDisplayName(String displayName) {
        for (Enchantment enchantment : enchantmentCosts.keySet()) {
            if (enchantment.getKey().getKey().equals(displayName)) {
                return enchantment;
            }
        }
        return null;
    }

    private int calculateRemoveEnchantCost(ItemStack item) {
        int cost = 0;
        if (item != null && item.hasItemMeta()) {
            for (Map.Entry<Enchantment, Integer> entry : item.getEnchantments().entrySet()) {
                // Assuming all enchantment removals cost the same
                cost += enchantmentRemovalCost;
            }
        }
        return cost;
    }

    private void removeEnchantments(ItemStack item) {
        if (item != null && item.hasItemMeta()) {
            for (Enchantment enchantment : item.getEnchantments().keySet()) {
                item.removeEnchantment(enchantment);
            }
        }
    }

    private Enchantment getRandomEnchantment() {
        Enchantment[] enchantments = Enchantment.values();
        return enchantments[new Random().nextInt(enchantments.length)];
    }

}
