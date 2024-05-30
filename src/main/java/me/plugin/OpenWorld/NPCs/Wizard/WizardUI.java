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
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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

        // Add a placeholder book in the last slot
        ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta meta = book.getItemMeta();
        meta.setDisplayName("Enchant/Disenchant");
        book.setItemMeta(meta);
        gui.setItem(8, book);

        // Fill gaps with gray stained glass panes
        ItemStack gapFill = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta gapMeta = gapFill.getItemMeta();
        gapMeta.setDisplayName(" ");
        gapMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
        gapFill.setItemMeta(gapMeta);

        for (int i = 0; i < gui.getSize(); i++) {
            if (i != 0 && i != 8) {
                gui.setItem(i, gapFill);
            }
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
            int slot = event.getRawSlot();
            Inventory inventory = event.getInventory();
            ItemStack clickedItem = event.getCurrentItem();

            if (slot == 0) {
                // Allow player to place or remove item in the first slot
                event.setCancelled(false);
            } else if (slot == 8 && clickedItem != null && clickedItem.getType() == Material.ENCHANTED_BOOK) {
                // Handle enchanting/disenchanting when the book is clicked
                ItemStack itemToEnchant = inventory.getItem(0);
                if (itemToEnchant != null && itemToEnchant.getType() != Material.AIR) {
                    handleEnchantingDisenchanting(player, itemToEnchant);
                } else {
                    player.sendMessage("Please place an item in the first slot to enchant or disenchant!");
                }
            }
        }
    }

    private void handleEnchantingDisenchanting(Player player, ItemStack item) {
        int removeCost = calculateRemoveEnchantCost(item);
        Enchantment randomEnchantment = getRandomEnchantment();
        int addCost = enchantmentCosts.getOrDefault(randomEnchantment, 0);
        int totalCost = removeCost + addCost;

        if (hasEnoughCoins(player, totalCost)) {
            // Deduct coins for both removing and adding enchantments
            deductCoins(player, totalCost);

            // Remove existing enchantments
            removeEnchantments(item);

            // Apply new enchantment
            item.addUnsafeEnchantment(randomEnchantment, 1);
            player.sendMessage("Item enchanted successfully with " + randomEnchantment.getKey().getKey() + " for " + totalCost + " coins!");
        } else {
            player.sendMessage("You don't have enough coins to purchase these enchantments!");
        }

        // Close the GUI
        player.closeInventory();
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

    private int calculateRemoveEnchantCost(ItemStack item) {
        int cost = 0;
        if (item != null && item.hasItemMeta()) {
            for (Map.Entry<Enchantment, Integer> entry : item.getEnchantments().entrySet()) {
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

    private Enchantment getEnchantmentFromDisplayName(String displayName) {
        for (Enchantment enchantment : enchantmentCosts.keySet()) {
            if (enchantment.getKey().getKey().equals(displayName)) {
                return enchantment;
            }
        }
        return null;
    }
}
