package me.plugin.OpenWorld.NPCs.Merchant.GUIs;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class CategoryGUI {

    public static void openGUI(Player player) {

        Inventory gui = Bukkit.createInventory(player, 9, ChatColor.BLACK + "The Merchant");
        ItemStack close = new ItemStack(Material.BARRIER);
        ItemStack building = new ItemStack(Material.BRICKS);
        ItemStack tools = new ItemStack(Material.WOODEN_SWORD);
        ItemStack spells = new ItemStack(Material.POTION);
        ItemStack misc = new ItemStack(Material.CHEST);
        ItemStack gapFill = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        //Close
        ItemMeta closeMeta = close.getItemMeta();
        closeMeta.setDisplayName(ChatColor.RED + "Close");
        ArrayList<String> closeLore = new ArrayList();
        closeLore.add(ChatColor.DARK_GRAY + "Open World Merchant");
        closeLore.add("");
        closeLore.add(ChatColor.DARK_PURPLE + "Click To Close");
        closeMeta.setLore(closeLore);
        closeMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
        close.setItemMeta(closeMeta);
        //Building
        ItemMeta buildingMeta = building.getItemMeta();
        buildingMeta.setDisplayName(ChatColor.WHITE + "Building Blocks");
        ArrayList<String> buildingLore = new ArrayList();
        buildingLore.add(ChatColor.DARK_GRAY + "Open World Merchant");
        buildingLore.add("");
        buildingLore.add(ChatColor.DARK_PURPLE + "Click To Open");
        buildingMeta.setLore(buildingLore);
        buildingMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
        building.setItemMeta(buildingMeta);
        //Tools
        ItemMeta toolsMeta = tools.getItemMeta();
        toolsMeta.setDisplayName(ChatColor.WHITE + "Armoury & Tools");
        ArrayList<String> toolsLore = new ArrayList();
        toolsLore.add(ChatColor.DARK_GRAY + "Open World Merchant");
        toolsLore.add("");
        toolsLore.add(ChatColor.DARK_PURPLE + "Click To Open");
        toolsMeta.setLore(toolsLore);
        toolsMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES});
        toolsMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
        tools.setItemMeta(toolsMeta);
        //Spells
        ItemMeta spellsMeta = spells.getItemMeta();
        spellsMeta.setDisplayName(ChatColor.WHITE + "Potions & Enchantments");
        ArrayList<String> spellsLore = new ArrayList();
        spellsLore.add(ChatColor.DARK_GRAY + "Open World Merchant");
        spellsLore.add("");
        spellsLore.add(ChatColor.DARK_PURPLE + "Click To Open");
        spellsMeta.setLore(spellsLore);
        spellsMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_POTION_EFFECTS});
        spellsMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
        spells.setItemMeta(spellsMeta);
        //Misc
        ItemMeta miscMeta = misc.getItemMeta();
        toolsMeta.setDisplayName(ChatColor.WHITE + "Miscellaneous");
        ArrayList<String> miscLore = new ArrayList();
        miscLore.add(ChatColor.DARK_GRAY + "Open World Merchant");
        miscLore.add("");
        miscLore.add(ChatColor.DARK_PURPLE + "Click To Open");
        miscMeta.setLore(miscLore);
        miscMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
        misc.setItemMeta(miscMeta);
        //GapFill
        ItemMeta gapMeta = gapFill.getItemMeta();
        gapMeta.setDisplayName(" ");
        gapMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
        gapFill.setItemMeta(gapMeta);
        //SettingGUI
        ItemStack[] menu_items = new ItemStack[]{building, gapFill, tools, gapFill, spells, gapFill, misc, gapFill, close};
        gui.setContents(menu_items);
        player.openInventory(gui);
    }

}
