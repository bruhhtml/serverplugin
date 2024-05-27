package me.plugin.OpenWorld.NPCs.Merchant.GUIs;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class MiscGUI {
    public static void openGUI(Player player) {
        Inventory gui = Bukkit.createInventory(player, 54, ChatColor.BLACK + "Merchant : Miscellaneous");
        //Product Declaration
        ItemStack codFish = new ItemStack(Material.COD); //5
        ItemStack salmonFish = new ItemStack(Material.SALMON); //None
        ItemStack tropicalFish = new ItemStack(Material.TROPICAL_FISH); //3
        ItemStack catEgg = new ItemStack(Material.CAT_SPAWN_EGG);
        ItemStack slimeBall = new ItemStack(Material.SLIME_BALL);

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
        //Cod
        ItemMeta codFishMeta = codFish.getItemMeta();
        codFishMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Cod");
        ArrayList<String> codFishLore = new ArrayList();
        codFishLore.add(ChatColor.DARK_PURPLE + "Type: " + ChatColor.DARK_GRAY + "Miscellaneous");
        codFishLore.add("");
        codFishLore.add(ChatColor.DARK_PURPLE + "Price: " + ChatColor.YELLOW + "10");
        codFishMeta.setLore(codFishLore);
        codFishMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_POTION_EFFECTS});
        codFishMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
        codFish.setItemMeta(codFishMeta);
        //Salmon
        ItemMeta salmonFishMeta = salmonFish.getItemMeta();
        salmonFishMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Salmon");
        ArrayList<String> salmonFishLore = new ArrayList();
        salmonFishLore.add(ChatColor.DARK_PURPLE + "Type: " + ChatColor.DARK_GRAY + "Miscellaneous");
        salmonFishLore.add("");
        salmonFishLore.add(ChatColor.DARK_PURPLE + "Price: " + ChatColor.YELLOW + "15");
        salmonFishMeta.setLore(salmonFishLore);
        salmonFishMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_POTION_EFFECTS});
        salmonFishMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
        salmonFish.setItemMeta(salmonFishMeta);
        //Tropical Fish
        ItemMeta tropicalFishMeta = tropicalFish.getItemMeta();
        tropicalFishMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Tropical Fish");
        ArrayList<String> tropicalFishLore = new ArrayList();
        tropicalFishLore.add(ChatColor.DARK_PURPLE + "Type: " + ChatColor.DARK_GRAY + "Miscellaneous");
        tropicalFishLore.add("");
        tropicalFishLore.add(ChatColor.DARK_PURPLE + "Price: " + ChatColor.YELLOW + "3");
        tropicalFishMeta.setLore(tropicalFishLore);
        tropicalFishMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_POTION_EFFECTS});
        tropicalFishMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
        tropicalFish.setItemMeta(tropicalFishMeta);
        //Cat Spawn Egg
        ItemMeta catSpawnEggMeta = catEgg.getItemMeta();
        catSpawnEggMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Cat Spawner");
        ArrayList<String> catSpawnEggLore = new ArrayList();
        catSpawnEggLore.add(ChatColor.DARK_PURPLE + "Type: " + ChatColor.DARK_GRAY + "Miscellaneous");
        catSpawnEggLore.add("");
        catSpawnEggLore.add(ChatColor.DARK_PURPLE + "Price: " + ChatColor.YELLOW + "50");
        catSpawnEggMeta.setLore(catSpawnEggLore);
        catSpawnEggMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_POTION_EFFECTS});
        catSpawnEggMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
        catEgg.setItemMeta(catSpawnEggMeta);
        //Slime Balls
        ItemMeta slimeBallMeta = slimeBall.getItemMeta();
        slimeBallMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Slime Ball");
        ArrayList<String> slimeBallLore = new ArrayList();
        slimeBallLore.add(ChatColor.DARK_PURPLE + "Type: " + ChatColor.DARK_GRAY + "Miscellaneous");
        slimeBallLore.add("");
        slimeBallLore.add(ChatColor.DARK_PURPLE + "Price: " + ChatColor.YELLOW + "250");
        slimeBallMeta.setLore(slimeBallLore);
        slimeBallMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_POTION_EFFECTS});
        slimeBallMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
        slimeBall.setItemMeta(slimeBallMeta);

        //SettingGUI
        // Initialize the menu items array with a size of 54
        ItemStack[] menu_items = new ItemStack[54];

        // Populate the initial items
        menu_items[0] = codFish;
        menu_items[1] = salmonFish;
        menu_items[2] = tropicalFish;
        menu_items[3] = catEgg;
        menu_items[4] = slimeBall;

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
