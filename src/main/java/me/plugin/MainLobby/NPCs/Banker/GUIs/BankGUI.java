package me.plugin.MainLobby.NPCs.Banker.GUIs;

import me.plugin.database;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class BankGUI {

    private static final Integer maximumBankSize = 100000;
    public static final Integer upgradeIncrements = 10000;

    private static boolean canUpgrade(Player player) {

        ConfigurationSection playerData = database.get().getConfigurationSection(player.getUniqueId().toString());
        ConfigurationSection playerBankData = playerData.getConfigurationSection("Bank");
        Integer CurrentCap = (Integer) playerBankData.get("MaxDepo");

        if (CurrentCap + upgradeIncrements > maximumBankSize) {

            return false;

        } else {

            return true;

        }

    }

    private static Integer getUpgradeCost(Player player) {

        ConfigurationSection playerData = database.get().getConfigurationSection(player.getUniqueId().toString());
        ConfigurationSection playerBankData = playerData.getConfigurationSection("Bank");
        Integer CurrentCap = (Integer) playerBankData.get("MaxDepo");

        Integer upgradeCost = (Integer) CurrentCap / 20;

        return upgradeCost;

    }

    public static void openGUI(Player player) {
        ConfigurationSection playerBankData = database.get().getConfigurationSection(player.getUniqueId().toString()).getConfigurationSection("Bank");
        Inventory gui = Bukkit.createInventory(player, 9, ChatColor.BLACK + "Your Bank");
        ItemStack close = new ItemStack(Material.BARRIER);
        ItemStack depo = new ItemStack(Material.HOPPER);
        ItemStack take = new ItemStack(Material.DISPENSER);
        ItemStack gapFill = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemStack upgradeBank = new ItemStack(Material.ENDER_CHEST);

        // Close Button
        ItemMeta closeMeta = close.getItemMeta();
        closeMeta.setDisplayName(ChatColor.RED + "Close");
        ArrayList<String> closeLore = new ArrayList();
        closeLore.add(ChatColor.DARK_GRAY + "Your Bank");
        closeLore.add("");
        closeLore.add(ChatColor.DARK_PURPLE + "Click To Close");
        closeMeta.setLore(closeLore);
        closeMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
        close.setItemMeta(closeMeta);

        //Deposit Button
        ItemMeta depoMeta = depo.getItemMeta();
        depoMeta.setDisplayName(ChatColor.GREEN + "Deposit");
        ArrayList<String> depoLore = new ArrayList();
        depoLore.add(ChatColor.DARK_GRAY + "Your Bank");
        depoLore.add(" ");
        depoLore.add(ChatColor.GRAY + "Banked: " + ChatColor.YELLOW + String.format("%,d", playerBankData.get("Banked")) + ChatColor.GRAY + "/" + String.format("%,d", playerBankData.get("MaxDepo")));
        depoLore.add(ChatColor.YELLOW + String.format("%,d", playerBankData.get("InterestRate")) + "% interest" + ChatColor.GRAY + " up to " + String.format("%,d", playerBankData.get("InterestCap")));
        depoMeta.setLore(depoLore);
        depoMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
        depo.setItemMeta(depoMeta);

        //Withdraw Button
        ItemMeta takeMeta = take.getItemMeta();
        takeMeta.setDisplayName(ChatColor.GREEN + "Withdraw");
        ArrayList<String> takeLore = new ArrayList();
        takeLore.add(ChatColor.DARK_GRAY + "Your Bank");
        takeLore.add(" ");
        takeLore.add(ChatColor.GRAY + "Withdraw a custom amount or");
        takeLore.add(ChatColor.GRAY + "use 'All' and '*' to withdraw entire ballance.");
        takeMeta.setLore(takeLore);
        takeMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
        take.setItemMeta(takeMeta);

        //Gap Fillers
        ItemMeta gapMeta = gapFill.getItemMeta();
        gapMeta.setDisplayName(" ");
        gapMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
        gapFill.setItemMeta(gapMeta);

        //Upgrade Bank
        ItemMeta upgradeBankMeta = upgradeBank.getItemMeta();
        upgradeBankMeta.setDisplayName(ChatColor.GREEN + "Upgrade");
        ArrayList<String> upgradeLore = new ArrayList();
        upgradeLore.add(ChatColor.DARK_GRAY + "Your Bank");
        upgradeLore.add("");
        if (canUpgrade(player)) {

            upgradeLore.add(ChatColor.DARK_PURPLE + "Price: " + ChatColor.YELLOW + getUpgradeCost(player));

        } else {

            upgradeLore.add(ChatColor.DARK_PURPLE  + "Bank is at max upgrade!");

        }
        upgradeBankMeta.setLore(upgradeLore);
        upgradeBankMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
        upgradeBank.setItemMeta(upgradeBankMeta);


        ItemStack[] menu_items = new ItemStack[]{upgradeBank, gapFill, gapFill, depo, gapFill, take, gapFill, gapFill, close};
        gui.setContents(menu_items);
        player.openInventory(gui);
    }

}
