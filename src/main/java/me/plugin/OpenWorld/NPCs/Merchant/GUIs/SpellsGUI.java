package me.plugin.OpenWorld.NPCs.Merchant.GUIs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;

public class SpellsGUI {

    public static void openGUI(Player player) {
        Inventory gui = Bukkit.createInventory(player, 54, ChatColor.BLACK + "Merchant : Potions & Enchants");
        //Product Declaration
        ItemStack efficiency = new ItemStack(Material.ENCHANTED_BOOK); //5
        ItemStack silkTouch = new ItemStack(Material.ENCHANTED_BOOK); //None
        ItemStack fortune = new ItemStack(Material.ENCHANTED_BOOK); //3
        ItemStack looting = new ItemStack(Material.ENCHANTED_BOOK); //3
        ItemStack sharpness = new ItemStack(Material.ENCHANTED_BOOK); //5
        ItemStack sweepingEdge = new ItemStack(Material.ENCHANTED_BOOK); //3
        ItemStack power = new ItemStack(Material.ENCHANTED_BOOK); //5
        ItemStack protection = new ItemStack(Material.ENCHANTED_BOOK); //4

        // Define the blank spacer item
        ItemStack blankSpacer = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        // Define the back and quit buttons
        ItemStack backButton = new ItemStack(Material.ARROW);
        ItemStack quitButton = new ItemStack(Material.BARRIER);

        // Quit Button
        ItemMeta quitButtonItemMeta = quitButton.getItemMeta();
        quitButtonItemMeta.setDisplayName(ChatColor.RED + "Close");
        ArrayList<String> quitButtonLore = new ArrayList();
        quitButtonLore.add(ChatColor.DARK_GRAY + "Merchant");
        quitButtonLore.add("");
        quitButtonLore.add(ChatColor.DARK_PURPLE + "Click To Close");
        quitButtonItemMeta.setLore(quitButtonLore);
        quitButtonItemMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
        quitButton.setItemMeta(quitButtonItemMeta);

        // Back Button
        ItemMeta backButtonItemMeta = backButton.getItemMeta();
        backButtonItemMeta.setDisplayName(ChatColor.WHITE + "Back");
        ArrayList<String> backButtonLore = new ArrayList();
        backButtonLore.add(ChatColor.DARK_GRAY + "Merchant");
        backButtonLore.add("");
        backButtonLore.add(ChatColor.GRAY + "Click To Go Back");
        backButtonItemMeta.setLore(backButtonLore);
        backButtonItemMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
        backButton.setItemMeta(backButtonItemMeta);

        // Spacer
        ItemMeta gapMeta = blankSpacer.getItemMeta();
        gapMeta.setDisplayName(" ");
        gapMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
        blankSpacer.setItemMeta(gapMeta);


        //Meta Tag BS
        //Efficiency
        ItemMeta efficiencyMeta = efficiency.getItemMeta();
        efficiencyMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Efficiency 5");
        ArrayList<String> efficiencyLore = new ArrayList();
        efficiencyLore.add(ChatColor.DARK_PURPLE + "Type: " + ChatColor.DARK_GRAY + "Enchantment");
        efficiencyLore.add("");
        efficiencyLore.add(ChatColor.DARK_PURPLE + "Price: " + ChatColor.YELLOW + "1550");
        efficiencyMeta.setLore(efficiencyLore);
        efficiencyMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_POTION_EFFECTS});
        efficiencyMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
        efficiency.setItemMeta(efficiencyMeta);
        //silkTouch
        ItemMeta silkTouchMeta = silkTouch.getItemMeta();
        silkTouchMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Silk Touch");
        ArrayList<String> silkTouchLore = new ArrayList();
        silkTouchLore.add(ChatColor.DARK_PURPLE + "Type: " + ChatColor.DARK_GRAY + "Enchantment");
        silkTouchLore.add("");
        silkTouchLore.add(ChatColor.DARK_PURPLE + "Price: " + ChatColor.YELLOW + "500");
        silkTouchMeta.setLore(silkTouchLore);
        silkTouchMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_POTION_EFFECTS});
        silkTouchMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
        silkTouch.setItemMeta(silkTouchMeta);
        //fortune
        ItemMeta fortuneMeta = fortune.getItemMeta();
        fortuneMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Fortune 3");
        ArrayList<String> fortuneLore = new ArrayList();
        fortuneLore.add(ChatColor.DARK_PURPLE + "Type: " + ChatColor.DARK_GRAY + "Enchantment");
        fortuneLore.add("");
        fortuneLore.add(ChatColor.DARK_PURPLE + "Price: " + ChatColor.YELLOW + "1650");
        fortuneMeta.setLore(fortuneLore);
        fortuneMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_POTION_EFFECTS});
        fortuneMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
        fortune.setItemMeta(fortuneMeta);
        //looting
        ItemMeta lootingMeta = looting.getItemMeta();
        lootingMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Looting 3");
        ArrayList<String> lootingLore = new ArrayList();
        lootingLore.add(ChatColor.DARK_PURPLE + "Type: " + ChatColor.DARK_GRAY + "Enchantment");
        lootingLore.add("");
        lootingLore.add(ChatColor.DARK_PURPLE + "Price: " + ChatColor.YELLOW + "1350");
        lootingMeta.setLore(lootingLore);
        lootingMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_POTION_EFFECTS});
        lootingMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
        looting.setItemMeta(lootingMeta);
        //sharpness
        ItemMeta sharpnessMeta = sharpness.getItemMeta();
        sharpnessMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Sharpness 5");
        ArrayList<String> sharpnessLore = new ArrayList();
        sharpnessLore.add(ChatColor.DARK_PURPLE + "Type: " + ChatColor.DARK_GRAY + "Enchantment");
        sharpnessLore.add("");
        sharpnessLore.add(ChatColor.DARK_PURPLE + "Price: " + ChatColor.YELLOW + "1450");
        sharpnessMeta.setLore(sharpnessLore);
        sharpnessMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_POTION_EFFECTS});
        sharpnessMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
        sharpness.setItemMeta(sharpnessMeta);
        //sweepingEdge
        ItemMeta sweepingEdgeMeta = sweepingEdge.getItemMeta();
        sweepingEdgeMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Sweeping Edge 3");
        ArrayList<String> sweepingEdgeLore = new ArrayList();
        sweepingEdgeLore.add(ChatColor.DARK_PURPLE + "Type: " + ChatColor.DARK_GRAY + "Enchantment");
        sweepingEdgeLore.add("");
        sweepingEdgeLore.add(ChatColor.DARK_PURPLE + "Price: " + ChatColor.YELLOW + "1350");
        sweepingEdgeMeta.setLore(sweepingEdgeLore);
        sweepingEdgeMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_POTION_EFFECTS});
        sweepingEdgeMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
        sweepingEdge.setItemMeta(sweepingEdgeMeta);
        //power
        ItemMeta powerMeta = power.getItemMeta();
        powerMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Power 5");
        ArrayList<String> powerLore = new ArrayList();
        powerLore.add(ChatColor.DARK_PURPLE + "Type: " + ChatColor.DARK_GRAY + "Enchantment");
        powerLore.add("");
        powerLore.add(ChatColor.DARK_PURPLE + "Price: " + ChatColor.YELLOW + "1100");
        powerMeta.setLore(powerLore);
        powerMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_POTION_EFFECTS});
        powerMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
        power.setItemMeta(powerMeta);
        //protection
        ItemMeta protectionMeta = protection.getItemMeta();
        protectionMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Protection 4");
        ArrayList<String> protectionLore = new ArrayList();
        protectionLore.add(ChatColor.DARK_PURPLE + "Type: " + ChatColor.DARK_GRAY + "Enchantment");
        protectionLore.add("");
        protectionLore.add(ChatColor.DARK_PURPLE + "Price: " + ChatColor.YELLOW + "1500");
        protectionMeta.setLore(protectionLore);
        protectionMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_POTION_EFFECTS});
        protectionMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
        protection.setItemMeta(protectionMeta);

        //SettingGUI
        // Initialize the menu items array with a size of 54
        ItemStack[] menu_items = new ItemStack[54];

        // Populate the initial items
        menu_items[0] = efficiency;
        menu_items[1] = silkTouch;
        menu_items[2] = fortune;
        menu_items[3] = looting;
        menu_items[4] = sharpness;
        menu_items[5] = sweepingEdge;
        menu_items[6] = power;
        menu_items[7] = protection;

        // Determine the number of prepopulated slots
        int prepopulatedSlots = 0;
        for (ItemStack item : menu_items) {
            if (item != null) {
                prepopulatedSlots++;
            }
        }

        // Fill the remaining slots up to the bottom row with blank spacers
        int bottomRowStartIndex = 54 - 9;
        for (int i = prepopulatedSlots; i < bottomRowStartIndex; i++) {
            menu_items[i] = blankSpacer;
        }

        // Set the back button and quit button in the bottom row
        menu_items[bottomRowStartIndex] = backButton; // First slot of the bottom row
        menu_items[bottomRowStartIndex + 8] = quitButton; // Last slot of the bottom row

        // Fill the remaining slots in the bottom row with blank spacers
        for (int i = bottomRowStartIndex + 1; i < bottomRowStartIndex + 8; i++) {
            if (menu_items[i] == null) {
                menu_items[i] = blankSpacer;
            }
        }

        gui.setContents(menu_items);
        player.openInventory(gui);
    }
}
