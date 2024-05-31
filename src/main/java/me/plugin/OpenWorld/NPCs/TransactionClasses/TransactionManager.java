package me.plugin.OpenWorld.NPCs.TransactionClasses;

import me.plugin.Main;
import me.plugin.database;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class TransactionManager {

    public static boolean finalizeTransaction(Player player, int price, ItemStack item) {
        // Check if the player has enough currency to make the purchase
        if (hasEnoughCurrency(player, price)) {
            // Deduct the price from the player's currency
            deductCurrency(player, price);

            // Attempt to add the purchased item to the player's inventory
            if (!addItemToInventory(player.getInventory(), item)) {
                // Inventory is full, refund the player's currency
                refundCurrency(player, price);
                return false; // Transaction failed due to full inventory
            }

            player.sendMessage(ChatColor.GREEN + "Transaction Success!");
            player.sendMessage(ChatColor.LIGHT_PURPLE + "[" + ChatColor.RESET + "MERCHANT" + ChatColor.LIGHT_PURPLE + "]" + ChatColor.RED + " - " + ChatColor.RESET + price + ChatColor.YELLOW + " Coins");

            // Transaction successful
            return true;
        } else {
            // Not enough currency, transaction failed
            player.sendMessage(ChatColor.RED + "Transaction Failed!");
            return false;
        }
    }

    private static boolean hasEnoughCurrency(Player player, int price) {

        int playerCash = database.get().getInt(player.getUniqueId().toString() + ".Coins", 0);

        // Implement logic to check if the player has enough currency
        // You can use a player's balance or any other currency system
        // For example:
        return playerCash >= price;
    }

    private static void deductCurrency(Player player, int price) {
        // Implement logic to deduct currency from the player
        // For example:
        // player.withdraw(price);
        // This would deduct 'price' from the player's balance

        int playerCash = database.get().getInt(player.getUniqueId().toString() + ".Coins", 0);

        database.get().set(player.getUniqueId().toString() + ".Coins", playerCash - price);

        database.save();

        Main.updateScoreboard(player);

    }

    private static void refundCurrency(Player player, int amount) {
        // Implement logic to refund currency to the player
        // For example:
        // player.deposit(amount);
        // This would add 'amount' to the player's balance

        int playerCash = database.get().getInt(player.getUniqueId().toString() + ".Coins", 0);

        database.get().set(player.getUniqueId().toString() + ".Coins", playerCash + amount);

        database.save();

        Main.updateScoreboard(player);

    }

    private static boolean addItemToInventory(Inventory inventory, ItemStack item) {
        // Attempt to add the item to the player's inventory
        // Check if there is space in the inventory
        if (inventory.firstEmpty() != -1) {
            // Inventory has space, add the item
            inventory.addItem(item);
            return true;
        } else {
            // Inventory is full
            return false;
        }
    }
}

