package me.plugin.OpenWorld.NPCs.TransactionClasses;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionType;

import java.util.Arrays;

public class ItemData {
    private final String name;
    private final Material material;
    private final String category;
    private final int price;
    private final PotionType potionType;

    private final Enchantment enchantmentType;

    private final int enchantmentLevel;

    public ItemData(String name, Material material, String category, int price) {
        this(name, material, category, price, null, null, 0);
    }

    public ItemData(String name, Material material, String category, int price, PotionType potionType, Enchantment enchantmentType, int enchantmentLevel) {
        this.name = name;
        this.material = material;
        this.category = category;
        this.price = price;
        this.potionType = potionType;
        this.enchantmentType = enchantmentType;
        this.enchantmentLevel = enchantmentLevel;
    }

    public String getName() {
        return name;
    }

    public Material getMaterial() {
        return material;
    }

    public String getCategory() {
        return category;
    }

    public int getPrice() {
        return price;
    }

    public PotionType getPotionType() {
        return potionType;
    }

    public Enchantment getEnchantmentType() {

        return enchantmentType;

    }

    public int getEnchantmentLevel() {

        return enchantmentLevel;

    }

    public ItemStack toItemStack() {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + name);
        meta.setLore(Arrays.asList(ChatColor.GRAY + category, ChatColor.GOLD + "Price: " + price + " coins"));
        meta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
        item.setItemMeta(meta);
        return item;
    }
}