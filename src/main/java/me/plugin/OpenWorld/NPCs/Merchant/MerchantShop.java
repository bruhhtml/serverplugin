package me.plugin.OpenWorld.NPCs.Merchant;

import me.plugin.OpenWorld.NPCs.TransactionClasses.ItemData;
import me.plugin.OpenWorld.NPCs.TransactionClasses.TransactionManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MerchantShop implements Listener {

    private static final List<ItemData> items = Arrays.asList(
            //Building
            new ItemData("Stone", Material.STONE, "Building", 2),
            new ItemData("Granite", Material.GRANITE, "Building", 5),
            new ItemData("Polished Granite", Material.POLISHED_GRANITE, "Building", 10),
            new ItemData("Diorite", Material.DIORITE, "Building", 5),
            new ItemData("Polished Diorite", Material.POLISHED_DIORITE, "Building", 10),
            new ItemData("Andesite", Material.ANDESITE, "Building", 5),
            new ItemData("Polished Andesite", Material.POLISHED_ANDESITE, "Building", 10),
            new ItemData("Stone Bricks", Material.STONE_BRICKS, "Building", 6),
            new ItemData("Chisled Stone Bricks", Material.CHISELED_STONE_BRICKS, "Building", 12),
            new ItemData("Deepslate", Material.DEEPSLATE, "Building", 10),
            new ItemData("Polished Deepslate", Material.POLISHED_DEEPSLATE, "Building", 30),
            new ItemData("Deepslate Bricks", Material.DEEPSLATE_BRICKS, "Building", 75),
            new ItemData("Scaffolding", Material.SCAFFOLDING, "Building", 50),

            //Misc
            new ItemData("Salmon", Material.SALMON, "Misc", 30),
            new ItemData("Tropical Fish", Material.TROPICAL_FISH, "Misc", 40),
            new ItemData("Cat Spawner", Material.CAT_SPAWN_EGG, "Misc", 500),
            new ItemData("Slime Ball", Material.SLIME_BALL, "Misc", 10),
            new ItemData("Wither Head", Material.WITHER_SKELETON_SKULL, "Misc", 1100),

            //Spells
            new ItemData("Potion of Strength", Material.POTION, "Spells", 200, PotionType.STRENGTH, null, 0),
            new ItemData("Potion of Healing", Material.POTION, "Spells", 200, PotionType.INSTANT_HEAL, null, 0),
            new ItemData("Potion of Speed", Material.POTION, "Spells", 200, PotionType.SPEED, null, 0),
            new ItemData("Potion of Harming", Material.POTION, "Spells", 200, PotionType.INSTANT_DAMAGE, null, 0),
            new ItemData("Protection IV", Material.ENCHANTED_BOOK, "Spells", 1100, null, Enchantment.PROTECTION_ENVIRONMENTAL, 4),
            new ItemData("Fire Protection IV", Material.ENCHANTED_BOOK, "Spells", 1250, null, Enchantment.PROTECTION_FIRE, 4),
            new ItemData("Feather Falling IV", Material.ENCHANTED_BOOK, "Spells", 750, null, Enchantment.PROTECTION_FALL, 4),
            new ItemData("Efficiency V", Material.ENCHANTED_BOOK, "Spells", 1250, null, Enchantment.DIG_SPEED, 5),
            new ItemData("Enchanted Golden Apple", Material.ENCHANTED_GOLDEN_APPLE, "Spells", 1000),

            //Tools
            new ItemData("Diamond Sword", Material.DIAMOND_SWORD, "Tools", 500),
            new ItemData("Iron Pickaxe", Material.IRON_PICKAXE, "Tools", 200),
            new ItemData("Diamond Helmet", Material.DIAMOND_HELMET, "Tools", 300)
    );

    public static Inventory createMerchantGUI() {
        Inventory gui = Bukkit.createInventory(null, 27, ChatColor.BLACK + "Merchant");

        gui.addItem(createCategoryItem("Building", Material.BRICKS));
        gui.addItem(createCategoryItem("Spells", Material.ENCHANTED_BOOK));
        gui.addItem(createCategoryItem("Tools", Material.DIAMOND_SWORD));
        gui.addItem(createCategoryItem("Misc", Material.CHEST));

        fillEmptySpacesWithGlass(gui);

        return gui;
    }

    public static void fillEmptySpacesWithGlass(Inventory inventory) {
        ItemStack glassPane = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta glassMeta = glassPane.getItemMeta();
        glassMeta.setDisplayName(" ");
        glassPane.setItemMeta(glassMeta);

        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, glassPane);
            }
        }
    }

    private static ItemStack createCategoryItem(String categoryName, Material material) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + categoryName);
        meta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
        item.setItemMeta(meta);
        return item;
    }

    public static Inventory createCategoryGUI(String categoryName) {
        Inventory gui = Bukkit.createInventory(null, 54, ChatColor.BLACK + categoryName);

        for (ItemData itemData : items) {
            if (itemData.getCategory().equalsIgnoreCase(categoryName)) {
                gui.addItem(itemData.toItemStack());
            }
        }

        fillEmptySpacesWithGlass(gui);

        return gui;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        Inventory clickedInventory = e.getClickedInventory();
        ItemStack clickedItem = e.getCurrentItem();

        // Check if the click happened within one of the merchant UIs
        if (clickedInventory != null && clickedItem != null && clickedItem.getItemMeta().getItemFlags().contains(ItemFlag.HIDE_ENCHANTS)) {
            e.setCancelled(true); // Cancel the event to prevent interactions with the merchant UIs
            String title = ChatColor.stripColor(e.getView().getTitle());
            // Handle clicks within the merchant UIs
            if (title.equalsIgnoreCase("Merchant")) {
                handleMerchantClick(e, player);
                e.setCancelled(true);
            } else if (Arrays.asList("Building", "Spells", "Tools", "Misc").contains(title)) {
                handleCategoryClick(e, player);
                e.setCancelled(true);
            } else if (title.equalsIgnoreCase("Purchase Options")) {
                handlePurchaseClick(e, player);
                e.setCancelled(true);
            }
        }
    }

    private void handleMerchantClick(InventoryClickEvent e, Player player) {
        if (e.getCurrentItem() != null && e.getCurrentItem().getItemMeta() != null) {
            String displayName = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
            switch (displayName) {
                case "Building":
                    player.openInventory(createCategoryGUI("Building"));
                    break;
                case "Spells":
                    player.openInventory(createCategoryGUI("Spells"));
                    break;
                case "Tools":
                    player.openInventory(createCategoryGUI("Tools"));
                    break;
                case "Misc":
                    player.openInventory(createCategoryGUI("Misc"));
                    break;
                case " ":
                    break;
            }
        } else {
            player.sendMessage(ChatColor.RED + "[SERVER] " + ChatColor.RESET + "There is an issue with this plugin: Item or ItemMeta is null.");
        }
    }

    private void handleCategoryClick(InventoryClickEvent e, Player player) {
        if (e.getCurrentItem() != null && e.getCurrentItem().getItemMeta() != null) {
            ItemStack currentItem = e.getCurrentItem();
            String displayName = ChatColor.stripColor(currentItem.getItemMeta().getDisplayName());
            int price = getPrice(displayName);
            PotionType potionType = getPotionType(displayName);
            Enchantment enchantmentType = getEnchantmentType(displayName);
            int enchantmentLevel = getEnchantmentLevel(displayName);

            if (price > 0) {
                openPurchaseOptionsGUI(player, displayName, currentItem.getType(), price, potionType, enchantmentType, enchantmentLevel);
            } else {
                player.sendMessage(ChatColor.RED + "[SERVER] " + ChatColor.RESET + "This item cannot be purchased.");
            }
        } else {
            player.sendMessage(ChatColor.RED + "[SERVER] " + ChatColor.RESET + "There is an issue with this plugin: Item or ItemMeta is null.");
        }
    }

    private void handlePurchaseClick(InventoryClickEvent e, Player player) {
        ItemStack clickedItem = e.getCurrentItem();
        if (clickedItem == null) {
            return;
        }

        String itemName = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName());
        int itemQuantity = clickedItem.getAmount();
        int price = getPriceFromItems(itemName, itemQuantity);

        ItemStack cleanItem = createCleanItem(clickedItem, itemQuantity);

        boolean transactionResult = TransactionManager.finalizeTransaction(player, price, cleanItem);

        if (transactionResult) {
            player.sendMessage("Transaction successful!");
        } else {
            player.sendMessage("Transaction failed: Inventory full or not enough currency.");
        }
    }

    private int getPriceFromItems(String itemName, int itemQuantity) {
        for (ItemData item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item.getPrice() * itemQuantity;
            }
        }
        return -1;
    }

    public static void openEnchantmentSelectionGUI(Player player) {
        Inventory gui = Bukkit.createInventory(null, 9, ChatColor.BLACK + "Choose Enchantment");

        ItemStack enchantmentItem = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta enchantmentMeta = enchantmentItem.getItemMeta();
        enchantmentMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        enchantmentMeta.setDisplayName(ChatColor.GREEN + "Buy Enchantment");
        enchantmentItem.setItemMeta(enchantmentMeta);
        gui.setItem(0, enchantmentItem);

        player.openInventory(gui);
    }

    private int getPrice(String itemName) {
        for (ItemData itemData : items) {
            if (itemData.getName().equalsIgnoreCase(itemName)) {
                return itemData.getPrice();
            }
        }
        return -1;
    }

    private PotionType getPotionType(String itemName) {
        for (ItemData item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item.getPotionType();
            }
        }
        return null;
    }

    private Enchantment getEnchantmentType(String itemName) {
        for (ItemData item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item.getEnchantmentType();
            }
        }
        return null;
    }

    private int getEnchantmentLevel(String itemName) {
        for (ItemData item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item.getEnchantmentLevel();
            }
        }
        return 0;
    }

    private void openPurchaseOptionsGUI(Player player, String itemName, Material material, int price, PotionType potionType, Enchantment enchantmentType, int enchantmentLevel) {
        Inventory gui = Bukkit.createInventory(null, 9, ChatColor.BLACK + "Purchase Options");

        ItemStack singleItem = createPurchaseOptionItem(itemName, material, 1, price, potionType, enchantmentType, enchantmentLevel);
        ItemStack halfStackItem = createPurchaseOptionItem(itemName, material, 32, price * 32, potionType, enchantmentType, enchantmentLevel);
        ItemStack fullStackItem = createPurchaseOptionItem(itemName, material, 64, price * 64, potionType, enchantmentType, enchantmentLevel);

        gui.setItem(0, singleItem);
        gui.setItem(1, halfStackItem);
        gui.setItem(2, fullStackItem);

        fillEmptySpacesWithGlass(gui);

        player.openInventory(gui);
    }

    private ItemStack createPurchaseOptionItem(String itemName, Material material, int amount, int price, PotionType potionType, Enchantment enchantmentType, int enchantmentLevel) {
        ItemStack item = new ItemStack(material, amount);
        if (material == Material.POTION && potionType != null) {
            PotionMeta potionMeta = (PotionMeta) item.getItemMeta();
            potionMeta.setDisplayName(ChatColor.GREEN + itemName);
            potionMeta.setBasePotionData(new PotionData(potionType));
            potionMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
            potionMeta.setLore(Arrays.asList(
                    ChatColor.GRAY + "Amount: " + amount,
                    ChatColor.GOLD + "Price: " + price + " coins"
            ));
            item.setItemMeta(potionMeta);
        } else if (material == Material.ENCHANTED_BOOK && enchantmentType != null) {
            EnchantmentStorageMeta enchantMeta = (EnchantmentStorageMeta) item.getItemMeta();
            enchantMeta.setDisplayName(ChatColor.GREEN + itemName);
            enchantMeta.addStoredEnchant(enchantmentType, enchantmentLevel, true);
            enchantMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
            enchantMeta.setLore(Arrays.asList(
                    ChatColor.GRAY + "Amount: " + amount,
                    ChatColor.GOLD + "Price: " + price + " coins"
            ));
            item.setItemMeta(enchantMeta);
        } else {
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.GREEN + itemName);
            meta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
            meta.setLore(Arrays.asList(
                    ChatColor.GRAY + "Amount: " + amount,
                    ChatColor.GOLD + "Price: " + price + " coins"
            ));
            item.setItemMeta(meta);
        }
        return item;
    }

    private ItemStack createCleanItem(ItemStack item, int amount) {
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
                Enchantment enchantment = entry.getKey();
                int level = entry.getValue();
                cleanEnchantMeta.addStoredEnchant(enchantment, level, true);
                // Do something with the enchantment and its level
            }

            cleanItem.setItemMeta(cleanEnchantMeta);

        }

        // No need to set display name and lore for non-potion items.
        return cleanItem;
    }

}
