package me.plugin.MainLobby.NPCs.Banker.GUIs;

import me.plugin.database;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class WithdrawGUI {

    public static void openGUI(Player player) {
        Inventory Takegui = Bukkit.createInventory(player, 9, ChatColor.BLACK + "Your Bank : Withdraw");
        ConfigurationSection playerData = database.get().getConfigurationSection(player.getUniqueId().toString());
        ConfigurationSection playerBankData = playerData.getConfigurationSection("Bank");
        ItemStack back = new ItemStack(Material.ARROW);
        ItemStack whole = new ItemStack(Material.DISPENSER, 64);
        ItemStack half = new ItemStack(Material.DISPENSER, 32);
        ItemStack amount = new ItemStack(Material.OAK_SIGN);
        ItemStack close = new ItemStack(Material.BARRIER);
        ItemStack gapFill = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta closeMeta = close.getItemMeta();
        closeMeta.setDisplayName(ChatColor.RED + "Close");
        ArrayList<String> closeLore = new ArrayList();
        closeLore.add(ChatColor.DARK_GRAY + "Your Bank");
        closeLore.add("");
        closeLore.add(ChatColor.DARK_PURPLE + "Click To Close");
        closeMeta.setLore(closeLore);
        closeMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
        close.setItemMeta(closeMeta);
        ItemMeta backMeta = back.getItemMeta();
        backMeta.setDisplayName(ChatColor.WHITE + "Back");
        ArrayList<String> backLore = new ArrayList();
        backLore.add(ChatColor.DARK_GRAY + "Your Bank");
        backLore.add("");
        backLore.add(ChatColor.GRAY + "Click To Go Back");
        backMeta.setLore(backLore);
        backMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
        back.setItemMeta(backMeta);
        ItemMeta gapMeta = gapFill.getItemMeta();
        gapMeta.setDisplayName(" ");
        gapMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
        gapFill.setItemMeta(gapMeta);
        ItemMeta wholeMeta = whole.getItemMeta();
        wholeMeta.setDisplayName(ChatColor.GREEN + "Whole Bank");
        ArrayList<String> wholeLore = new ArrayList();
        wholeLore.add(ChatColor.DARK_GRAY + "Your Bank");
        wholeLore.add("");
        wholeLore.add(ChatColor.GRAY + "Banked: " + ChatColor.YELLOW + String.format("%,d", playerBankData.get("Banked")));
        wholeLore.add(ChatColor.GRAY + "To Withdraw: " + ChatColor.YELLOW + String.format("%,d", playerBankData.get("Banked")));
        wholeLore.add("");
        wholeLore.add(ChatColor.GOLD + "Click To Withdraw");
        wholeMeta.setLore(wholeLore);
        wholeMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
        whole.setItemMeta(wholeMeta);
        ItemMeta halfMeta = half.getItemMeta();
        halfMeta.setDisplayName(ChatColor.GREEN + "Half Bank");
        ArrayList<String> halfLore = new ArrayList();
        halfLore.add(ChatColor.DARK_GRAY + "Your Bank");
        halfLore.add("");
        halfLore.add(ChatColor.GRAY + "Banked: " + ChatColor.YELLOW + String.format("%,d", playerBankData.get("Banked")));
        halfLore.add(ChatColor.GRAY + "To Withdraw: " + ChatColor.YELLOW + String.format("%,d", (Integer)playerBankData.get("Banked") / 2));
        halfLore.add("");
        halfLore.add(ChatColor.GOLD + "Click To Withdraw");
        halfMeta.setLore(halfLore);
        halfMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
        half.setItemMeta(halfMeta);
        ItemMeta amountMeta = amount.getItemMeta();
        amountMeta.setDisplayName(ChatColor.GREEN + "Specific Amount");
        ArrayList<String> amountLore = new ArrayList();
        amountLore.add(ChatColor.DARK_GRAY + "Your Bank");
        amountLore.add("");
        amountLore.add(ChatColor.GRAY + "Banked: " + ChatColor.YELLOW + String.format("%,d", playerBankData.get("Banked")));
        amountLore.add("");
        amountLore.add(ChatColor.GOLD + "Click To Withdraw An Amount");
        amountMeta.setLore(amountLore);
        amountMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
        amount.setItemMeta(amountMeta);
        ItemStack[] menu_items = new ItemStack[]{back, gapFill, whole, gapFill, half, gapFill, amount, gapFill, close};
        Takegui.setContents(menu_items);
        player.openInventory(Takegui);
    }

}
