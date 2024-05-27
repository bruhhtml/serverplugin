package me.plugin.OpenWorld.NPCs.Merchant;

import me.plugin.Main;

import me.plugin.OpenWorld.NPCs.Merchant.GUIs.CategoryGUI;
import me.plugin.OpenWorld.NPCs.Merchant.GUIs.MiscGUI;
import me.plugin.OpenWorld.NPCs.Merchant.GUIs.SpellsGUI;
import me.plugin.database;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ShopSystemClicks implements Listener {

    //Get the price of the item from the item meta
    public int getPrice(Player player, ItemMeta meta) {

        List<String> lore = meta.getLore();

        if (lore != null && !lore.isEmpty()) {
            // Assume price is always on the third line (index 2)
            if (lore.size() > 2) {
                String priceLine = lore.get(2);
                String strippedPriceLine = ChatColor.stripColor(priceLine); // Remove ChatColor codes
                String[] parts = strippedPriceLine.split(" ");

                if (parts.length > 1) {
                    try {
                        Integer price = Integer.valueOf(parts[1]);
//                                    player.sendMessage(ChatColor.GREEN + "[SERVER] " + ChatColor.RESET + "Price: " + price);
                        return price;
                    } catch (NumberFormatException ex) {
                        player.sendMessage(ChatColor.RED + "[SERVER] " + ChatColor.RESET + "Invalid price format.");
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "[SERVER] " + ChatColor.RESET + "Price information missing.");
                }
            } else {
                player.sendMessage(ChatColor.RED + "[SERVER] " + ChatColor.RESET + "Lore is not in the expected format.");
            }
        } else {
            player.sendMessage(ChatColor.RED + "[SERVER] " + ChatColor.RESET + "There is an issue with this plugin: Lore is empty or null.");
        }
        return 0;
    }

    //Check that the user has enough coins in order to make the purchase
    public boolean checkCoins(Player player, Integer price) {

        ConfigurationSection playerData = database.get().getConfigurationSection(player.getUniqueId().toString());
        Integer Coins = (Integer)playerData.get("Coins");
        if (Coins >= price) {

            return true;

        } else {

            player.sendMessage(ChatColor.RED + "[SERVER] " + ChatColor.RESET + "You need more coins to do that.");

            return false;

        }

    }

    public boolean addItem(Player player, ItemStack item) {

        World world = Bukkit.getWorld("world");
        Map<Integer, ItemStack> remainingItems = player.getInventory().addItem(item);
        if (remainingItems.isEmpty()) {
//            player.sendMessage(ChatColor.GREEN + "You have purchased a " + item.getType().toString().replace('_', ' ').toLowerCase() + "!");
            return true; // Indicate success
        } else {
            player.sendMessage(ChatColor.RED + "[SERVER] " + ChatColor.RESET + "Your inventory is full.");
            return false; // Indicate failure
        }
    }

    public void finaliseTransaction(Player player, Integer price) {

        ConfigurationSection playerData = database.get().getConfigurationSection(player.getUniqueId().toString());
        Integer Coins = (Integer)playerData.get("Coins");
        playerData.set("Coins", Coins - price);

        database.save();
        Main.updateScoreboard(player);

        player.sendMessage(ChatColor.LIGHT_PURPLE + "[MERCHANT] " + ChatColor.RED + " - £" + ChatColor.YELLOW + price);
        player.sendMessage(ChatColor.GREEN + "Transaction Success!");

    }

    @EventHandler
    public void clickEvent(InventoryClickEvent e) {
        Player player = (Player)e.getWhoClicked();
        if (e.getCurrentItem().getItemMeta().hasItemFlag(ItemFlag.HIDE_ENCHANTS)) {
            if (e.getView().getTitle().equalsIgnoreCase(ChatColor.BLACK + "The Merchant")) {
                // Handle different types of clicked items
                switch (e.getCurrentItem().getType()) {
                    case BARRIER:
                        // Close the inventory when a BARRIER item is clicked
                        player.closeInventory();
                        break;
                    case POTION:
                        // Open the SpellsGUI when a POTION item is clicked
                        SpellsGUI.openGUI(player);
                        break;
                    case CHEST:
                        // Open the MiscGUI when a CHEST item is clicked
                        MiscGUI.openGUI(player);
                    // Add additional cases as needed for other item types
                }
                // Cancel the event to prevent further processing
                e.setCancelled(true);
            }

            if (e.getView().getTitle().equalsIgnoreCase(ChatColor.BLACK + "Merchant : Potions & Enchants")) {
                if (e.getCurrentItem() != null && e.getCurrentItem().getItemMeta() != null) {
                    if (e.getCurrentItem().getType() == Material.BARRIER) {
                        player.closeInventory();
                    } else if (e.getCurrentItem().getType() == Material.ARROW) {
                        CategoryGUI.openGUI(player);

                    } else if (e.getCurrentItem().getType() == Material.GRAY_STAINED_GLASS_PANE) {

                    } else {
                        ItemMeta meta = e.getCurrentItem().getItemMeta();
                        Integer price = getPrice(player, meta);

                        ItemStack enchantedBook = new ItemStack(Material.ENCHANTED_BOOK);
                        EnchantmentStorageMeta enchantedBookMeta = (EnchantmentStorageMeta) enchantedBook.getItemMeta();
                        switch (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName().toString()).toString()) {
                            //Efficiency 5
                            case "Efficiency 5":
                                if (enchantedBookMeta != null) {
                                    // Add the Efficiency V enchantment
                                    enchantedBookMeta.addStoredEnchant(Enchantment.DIG_SPEED, 5, true);
                                    // Set the meta back to the item
                                    enchantedBook.setItemMeta(enchantedBookMeta);
                                }
                                if (checkCoins(player, price) && addItem(player, enchantedBook)) {

                                    finaliseTransaction(player, price);

                                }
                                break;
                            //SilkTouch
                            case "Silk Touch":
                                if (enchantedBookMeta != null) {
                                    // Add the Efficiency V enchantment
                                    enchantedBookMeta.addStoredEnchant(Enchantment.SILK_TOUCH, 1, true);
                                    // Set the meta back to the item
                                    enchantedBook.setItemMeta(enchantedBookMeta);
                                }
                                if (checkCoins(player, price) && addItem(player, enchantedBook)) {

                                    finaliseTransaction(player, price);

                                }
                                break;
                            //Fortune 3
                            case "Fortune 3":
                                if (enchantedBookMeta != null) {
                                    // Add the Efficiency V enchantment
                                    enchantedBookMeta.addStoredEnchant(Enchantment.LOOT_BONUS_BLOCKS, 3, true);
                                    // Set the meta back to the item
                                    enchantedBook.setItemMeta(enchantedBookMeta);
                                }
                                if (checkCoins(player, price) && addItem(player, enchantedBook)) {

                                    finaliseTransaction(player, price);

                                }
                                break;
                            //Looting 3
                            case "Looting 3":
                                if (enchantedBookMeta != null) {
                                    // Add the Efficiency V enchantment
                                    enchantedBookMeta.addStoredEnchant(Enchantment.LOOT_BONUS_MOBS, 3, true);
                                    // Set the meta back to the item
                                    enchantedBook.setItemMeta(enchantedBookMeta);
                                }
                                if (checkCoins(player, price) && addItem(player, enchantedBook)) {

                                    finaliseTransaction(player, price);

                                }
                                break;
                            //Sharpness 5
                            case "Sharpness 5":
                                if (enchantedBookMeta != null) {
                                    // Add the Efficiency V enchantment
                                    enchantedBookMeta.addStoredEnchant(Enchantment.DAMAGE_ALL, 5, true);
                                    // Set the meta back to the item
                                    enchantedBook.setItemMeta(enchantedBookMeta);
                                }
                                if (checkCoins(player, price) && addItem(player, enchantedBook)) {

                                    finaliseTransaction(player, price);

                                }
                                break;
                            //Sweeping Edge 3
                            case "Sweeping Edge 3":
                                if (enchantedBookMeta != null) {
                                    // Add the Efficiency V enchantment
                                    enchantedBookMeta.addStoredEnchant(Enchantment.SWEEPING_EDGE, 3, true);
                                    // Set the meta back to the item
                                    enchantedBook.setItemMeta(enchantedBookMeta);
                                }
                                if (checkCoins(player, price) && addItem(player, enchantedBook)) {

                                    finaliseTransaction(player, price);

                                }
                                break;
                            //Power 5
                            case "Power 5":
                                if (enchantedBookMeta != null) {
                                    // Add the Efficiency V enchantment
                                    enchantedBookMeta.addStoredEnchant(Enchantment.ARROW_KNOCKBACK, 5, true);
                                    // Set the meta back to the item
                                    enchantedBook.setItemMeta(enchantedBookMeta);
                                }
                                if (checkCoins(player, price) && addItem(player, enchantedBook)) {

                                    finaliseTransaction(player, price);

                                }
                                break;
                            //Protection 4
                            case "Protection 4":
                                if (enchantedBookMeta != null) {
                                    // Add the Efficiency V enchantment
                                    enchantedBookMeta.addStoredEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
                                    // Set the meta back to the item
                                    enchantedBook.setItemMeta(enchantedBookMeta);
                                }
                                if (checkCoins(player, price) && addItem(player, enchantedBook)) {

                                    finaliseTransaction(player, price);

                                }
                                break;
                    }

                    }
                } else {
                    player.sendMessage(ChatColor.RED + "[SERVER] " + ChatColor.RESET + "There is an issue with this plugin: Item or ItemMeta is null.");
                }
                // Cancel the event to prevent further processing
                e.setCancelled(true);
            }

            if (e.getView().getTitle().equalsIgnoreCase(ChatColor.BLACK + "Merchant : Miscellaneous")) {
                if (e.getCurrentItem() != null && e.getCurrentItem().getItemMeta() != null) {
                    if (e.getCurrentItem().getType() == Material.BARRIER) {
                        player.closeInventory();
                    } else if (e.getCurrentItem().getType() == Material.ARROW) {
                        CategoryGUI.openGUI(player);

                    } else if (e.getCurrentItem().getType() == Material.GRAY_STAINED_GLASS_PANE) {

                    } else {
                        ItemMeta meta = e.getCurrentItem().getItemMeta();
                        Integer price = getPrice(player, meta);

//                    ItemStack enchantedBook = new ItemStack(Material.ENCHANTED_BOOK);
//                    EnchantmentStorageMeta enchantedBookMeta = (EnchantmentStorageMeta) enchantedBook.getItemMeta();
                        switch (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName().toString()).toString()) {
                            // Cod
                            case "Cod":
                                ItemStack codSaleItem = new ItemStack(Material.COD);
                                if (checkCoins(player, price) && addItem(player, codSaleItem)) {

                                    finaliseTransaction(player, price);

                                }
                                break;
                            // Salmon
                            case "Salmon":
                                ItemStack salmonSaleItem = new ItemStack(Material.SALMON);
                                if (checkCoins(player, price) && addItem(player, salmonSaleItem)) {

                                    finaliseTransaction(player, price);

                                }
                                break;
                            // Tropical Fish
                            case "Tropical Fish":
                                ItemStack  tropicalFishSaleItem = new ItemStack(Material.TROPICAL_FISH);
                                if (checkCoins(player, price) && addItem(player, tropicalFishSaleItem)) {

                                    finaliseTransaction(player, price);

                                }
                                break;
                            // Cat Spawner
                            case "Cat Spawner":
                                ItemStack  catSpawnEggSaleItem = new ItemStack(Material.CAT_SPAWN_EGG);
                                if (checkCoins(player, price) && addItem(player, catSpawnEggSaleItem)) {

                                    finaliseTransaction(player, price);

                                }
                                break;
                            case "Slime Ball":
                                ItemStack  slimeBallSaleItem = new ItemStack(Material.SLIME_BALL);
                                if (checkCoins(player, price) && addItem(player, slimeBallSaleItem)) {

                                    finaliseTransaction(player, price);

                                }
                                break;
                        }
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "[SERVER] " + ChatColor.RESET + "There is an issue with this plugin: Item or ItemMeta is null.");
                }
                // Cancel the event to prevent further processing
                e.setCancelled(true);
            }

        }
    }

}
