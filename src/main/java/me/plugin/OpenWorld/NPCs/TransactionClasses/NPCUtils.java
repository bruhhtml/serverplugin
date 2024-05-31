package me.plugin.OpenWorld.NPCs.TransactionClasses;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class NPCUtils {

    public static Inventory openNPCGUI(String npcName, List<String> categories, List<Material> materials) {
        Inventory gui = Bukkit.createInventory(null, 27, ChatColor.BLACK + npcName);
        for (int i = 0; i < categories.size(); i++) {
            gui.addItem(createCategoryItem(categories.get(i), materials.get(i)));
        }
        me.plugin.OpenWorld.NPCs.TransactionClasses.ItemUtils.fillEmptySpacesWithGlass(gui);
        return gui;
    }

    private static ItemStack createCategoryItem(String categoryName, Material material) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + categoryName);
        meta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
        item.setItemMeta(meta);
        return item;
    }

    public static Inventory createCategoryGUI(String categoryName, List<ItemData> items) {
        Inventory gui = Bukkit.createInventory(null, 54, ChatColor.BLACK + categoryName);
        for (ItemData itemData : items) {
            if (itemData.getCategory().equalsIgnoreCase(categoryName)) {
                gui.addItem(itemData.toItemStack());
            }
        }
        gui.setItem(53, me.plugin.OpenWorld.NPCs.TransactionClasses.ItemUtils.createBackButton());
        me.plugin.OpenWorld.NPCs.TransactionClasses.ItemUtils.fillEmptySpacesWithGlass(gui);
        return gui;
    }

    public static void handleMerchantClick(InventoryClickEvent e, Player player, String npcName, List<String> categories) {
        String displayName = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
        e.setCancelled(true);
        player.sendMessage(npcName + ": " + displayName);
        if ("Back".equals(displayName)) {
            player.closeInventory();
        } else {
            for (String category : categories) {
                if (category.equals(displayName)) {
                    player.openInventory(createCategoryGUI(category, getNPCItems(npcName)));
                    return;
                }
            }
        }
    }

    public static void handleCategoryClick(InventoryClickEvent e, Player player, String npcName) {
        String displayName = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
        if ("Back".equals(displayName)) {
            player.openInventory(openNPCGUI(npcName, getNPCCategories(npcName), getNPCMaterials(npcName)));
        } else {
            ItemStack currentItem = e.getCurrentItem();
            int price = getPrice(displayName, getNPCItems(npcName));
            if (price > 0) {
                me.plugin.OpenWorld.NPCs.TransactionClasses.PurchaseOptionsGUI.open(player, displayName, currentItem.getType(), price, null, null, 0);
            } else {
                player.sendMessage(ChatColor.RED + "[SERVER] " + ChatColor.RESET + "This item cannot be purchased.");
            }
        }
    }

    public static List<ItemData> getNPCItems(String npcName) {
        if ("Decorator".equals(npcName)) {
            return me.plugin.OpenWorld.NPCs.Decorator.DecoratorNPCMain.getItems();
        } else if ("Builder".equals(npcName)) {
            return me.plugin.OpenWorld.NPCs.Builder.BuilderNPCMain.getItems();
        }
        return null;
    }

    public static List<String> getNPCCategories(String npcName) {
        if ("Decorator".equals(npcName)) {
            return Arrays.asList("Fences", "Signs");
        } else if ("Builder".equals(npcName)) {
            return Arrays.asList("Blocks", "Nature");
        }
        return null;
    }

    public static List<Material> getNPCMaterials(String npcName) {
        if ("Decorator".equals(npcName)) {
            return Arrays.asList(Material.OAK_FENCE, Material.OAK_SIGN);
        } else if ("Builder".equals(npcName)) {
            return Arrays.asList(Material.BRICKS, Material.OAK_SAPLING);
        }
        return null;
    }

    private static int getPrice(String itemName, List<ItemData> items) {
        for (ItemData itemData : items) {
            if (itemData.getName().equalsIgnoreCase(itemName)) {
                return itemData.getPrice();
            }
        }
        return -1;
    }
}
