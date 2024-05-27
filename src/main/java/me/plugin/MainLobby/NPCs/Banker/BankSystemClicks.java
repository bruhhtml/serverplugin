package me.plugin.MainLobby.NPCs.Banker;

import me.plugin.Main;
import me.plugin.MainLobby.NPCs.Banker.GUIs.BankGUI;
import me.plugin.MainLobby.NPCs.Banker.GUIs.DepositGUI;
import me.plugin.MainLobby.NPCs.Banker.GUIs.WithdrawGUI;
import me.plugin.MainLobby.NPCs.Banker.Systems.DepositSystem;
import me.plugin.MainLobby.NPCs.Banker.Systems.WithdrawSystem;
import me.plugin.database;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class BankSystemClicks implements Listener {

    public boolean checkUpgradable(Player player, ItemMeta meta) {

        List<String> lore = meta.getLore();

        if (lore != null && !lore.isEmpty()) {
            // Assume price is always on the third line (index 2)
            if (lore.size() > 2) {
                String priceLine = lore.get(2);
                String strippedPriceLine = ChatColor.stripColor(priceLine); // Remove ChatColor codes

                if (strippedPriceLine.equalsIgnoreCase("Bank is at max upgrade!")) {

                    return false;

                } else {

                    return true;

                }
            } else {
                player.sendMessage(ChatColor.RED + "[SERVER] " + ChatColor.RESET + "Lore is not in the expected format.");
            }
        } else {
            player.sendMessage(ChatColor.RED + "[SERVER] " + ChatColor.RESET + "There is an issue with this plugin: Lore is empty or null.");
        }
        return false;

    }

    //Get the price of the item from the item meta
    public int getUpgradePrice(Player player, ItemMeta meta) {

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

    public void finaliseUpgrade(Player player, Integer price) {

        ConfigurationSection playerData = database.get().getConfigurationSection(player.getUniqueId().toString());
        ConfigurationSection playerBankData = playerData.getConfigurationSection("Bank");
        Integer Coins = (Integer)playerData.get("Coins");
        playerData.set("Coins", Coins - price);
        playerBankData.set("MaxDepo", (Integer) playerBankData.get("MaxDepo") + BankGUI.upgradeIncrements);

        database.save();
        Main.updateScoreboard(player);

        player.sendMessage(ChatColor.LIGHT_PURPLE + "[BANKER] " + ChatColor.RED + " - £" + ChatColor.YELLOW + price);
        player.sendMessage(ChatColor.GREEN + "Upgrade Success!");

        BankGUI.openGUI(player);

    }

    @EventHandler
    public void clickEvent(InventoryClickEvent e) {
        Player player = (Player)e.getWhoClicked();
        if (e.getCurrentItem().getItemMeta().hasItemFlag(ItemFlag.HIDE_ENCHANTS)) {
            if (e.getView().getTitle().equalsIgnoreCase(ChatColor.BLACK + "Your Bank")) {
                switch (e.getCurrentItem().getType()) {
                    case BARRIER:
                        player.closeInventory();
                        break;
                    case DISPENSER:
                        WithdrawGUI.openGUI(player);
                        break;
                    case HOPPER:
                        DepositGUI.openGUI(player);
                        break;
                    case ENDER_CHEST:
                        if (checkUpgradable(player, e.getCurrentItem().getItemMeta())) {

                            Integer price = getUpgradePrice(player, e.getCurrentItem().getItemMeta());
                            if (checkCoins(player, price)) {

                                finaliseUpgrade(player, price);

                            }

                        }
                        break;
                }
                e.setCancelled(true);
            }

            ConfigurationSection playerData;
            ConfigurationSection playerBankData;
            if (e.getView().getTitle().equalsIgnoreCase(ChatColor.BLACK + "Your Bank : Deposit")) {
                playerData = database.get().getConfigurationSection(player.getUniqueId().toString());
                playerBankData = playerData.getConfigurationSection("Bank");
                switch (e.getCurrentItem().getType()) {
                    case BARRIER:
                        player.closeInventory();
                    case DISPENSER:
                    case HOPPER:
                    default:
                        break;
                    case ARROW:
                        BankGUI.openGUI(player);
                        break;
                    case HOPPER_MINECART:
                        if (e.getCurrentItem().getAmount() == 64) {
                            if (DepositSystem.DepoFull(player)) {
                                player.closeInventory();
                            }
                        } else if (e.getCurrentItem().getAmount() == 32 && DepositSystem.DepoHalf(player)) {
                            player.closeInventory();
                        }

                }
                e.setCancelled(true);
            }

            if (e.getView().getTitle().equalsIgnoreCase(ChatColor.BLACK + "Your Bank : Withdraw")) {
                playerData = database.get().getConfigurationSection(player.getUniqueId().toString());
                playerBankData = playerData.getConfigurationSection("Bank");
                switch (e.getCurrentItem().getType()) {
                    case BARRIER:
                        player.closeInventory();
                        break;
                    case DISPENSER:
                        if (e.getCurrentItem().getAmount() == 64) {
                            if (WithdrawSystem.TakeFull(player)) {
                                player.closeInventory();
                            }
                        } else if (e.getCurrentItem().getAmount() == 32 && WithdrawSystem.TakeHalf(player)) {
                            player.closeInventory();
                        }
                    case HOPPER:
                    default:
                        break;
                    case ARROW:
                        BankGUI.openGUI(player);
                }
                e.setCancelled(true);
            }


        }

    }

}
