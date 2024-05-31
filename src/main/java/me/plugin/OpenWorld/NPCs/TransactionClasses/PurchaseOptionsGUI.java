package me.plugin.OpenWorld.NPCs.TransactionClasses;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.util.Arrays;

public class PurchaseOptionsGUI {

    public static void open(Player player, String itemName, Material material, int price, PotionType potionType, Enchantment enchantmentType, int enchantmentLevel) {
        Inventory gui = Bukkit.createInventory(null, 9, ChatColor.BLACK + "Purchase Options");

        ItemStack singleItem = createPurchaseOptionItem(itemName, material, 1, price, potionType, enchantmentType, enchantmentLevel);
        ItemStack halfStackItem = createPurchaseOptionItem(itemName, material, 32, price * 32, potionType, enchantmentType, enchantmentLevel);
        ItemStack fullStackItem = createPurchaseOptionItem(itemName, material, 64, price * 64, potionType, enchantmentType, enchantmentLevel);

        gui.setItem(0, singleItem);
        gui.setItem(1, halfStackItem);
        gui.setItem(2, fullStackItem);
        gui.setItem(8, me.plugin.OpenWorld.NPCs.TransactionClasses.ItemUtils.createBackButton());

        me.plugin.OpenWorld.NPCs.TransactionClasses.ItemUtils.fillEmptySpacesWithGlass(gui);

        player.openInventory(gui);
    }

    private static ItemStack createPurchaseOptionItem(String itemName, Material material, int amount, int price, PotionType potionType, Enchantment enchantmentType, int enchantmentLevel) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(ChatColor.GREEN + itemName);
            meta.setLore(Arrays.asList(ChatColor.GRAY + "Amount: " + amount, ChatColor.GOLD + "Price: " + price + " coins"));
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

            if (material == Material.POTION && potionType != null) {
                PotionMeta potionMeta = (PotionMeta) meta;
                potionMeta.setBasePotionData(new PotionData(potionType));
                item.setItemMeta(potionMeta);
            } else if (material == Material.ENCHANTED_BOOK && enchantmentType != null) {
                EnchantmentStorageMeta enchantMeta = (EnchantmentStorageMeta) meta;
                enchantMeta.addStoredEnchant(enchantmentType, enchantmentLevel, true);
                item.setItemMeta(enchantMeta);
            } else {
                item.setItemMeta(meta);
            }
        }

        return item;
    }

}
