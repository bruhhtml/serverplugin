package me.plugin.OpenWorld.NPCs.TransactionClasses;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;

import java.util.Map;

public class ItemUtils {

    public static ItemStack createCleanItem(ItemStack item, int amount) {
        ItemStack cleanItem = new ItemStack(item.getType(), amount);

        if (item.getType() == Material.POTION) {
            PotionMeta cleanPotionMeta = (PotionMeta) cleanItem.getItemMeta();
            PotionMeta originalPotionMeta = (PotionMeta) item.getItemMeta();

            cleanPotionMeta.setBasePotionData(originalPotionMeta.getBasePotionData());
            cleanItem.setItemMeta(cleanPotionMeta);
        }

        if (item.getType() == Material.ENCHANTED_BOOK) {
            EnchantmentStorageMeta cleanEnchantMeta = (EnchantmentStorageMeta) cleanItem.getItemMeta();
            EnchantmentStorageMeta originalEnchantMeta = (EnchantmentStorageMeta) item.getItemMeta();

            Map<Enchantment, Integer> storedEnchants = originalEnchantMeta.getStoredEnchants();
            for (Map.Entry<Enchantment, Integer> entry : storedEnchants.entrySet()) {
                cleanEnchantMeta.addStoredEnchant(entry.getKey(), entry.getValue(), true);
            }

            cleanItem.setItemMeta(cleanEnchantMeta);
        }

        // No need to set display name and lore for non-potion items.
        return cleanItem;
    }

    public static ItemStack createBackButton() {
        ItemStack item = new ItemStack(Material.ARROW);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.RED + "Back");
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            item.setItemMeta(meta);
        }
        return item;
    }

    public static void fillEmptySpacesWithGlass(Inventory inventory) {
        ItemStack glassPane = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta glassMeta = glassPane.getItemMeta();
        if (glassMeta != null) {
            glassMeta.setDisplayName(" ");
            glassMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            glassPane.setItemMeta(glassMeta);
        }

        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, glassPane);
            }
        }
    }

}
