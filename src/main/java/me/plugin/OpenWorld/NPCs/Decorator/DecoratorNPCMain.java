package me.plugin.OpenWorld.NPCs.Decorator;

import me.plugin.OpenWorld.NPCs.TransactionClasses.ItemData;
import me.plugin.OpenWorld.NPCs.TransactionClasses.NPCUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Arrays;
import java.util.List;

public class DecoratorNPCMain implements Listener {

    private static final String npcName = "Decorator";

    private static final List<ItemData> items = Arrays.asList(
            // FENCES
            new ItemData("Oak Fence", Material.OAK_FENCE, "Fences", 10),
            new ItemData("Oak Fence Gate", Material.OAK_FENCE_GATE, "Fences", 10),
            new ItemData("Birch Fence", Material.BIRCH_FENCE, "Fences", 10),
            new ItemData("Birch Fence Gate", Material.BIRCH_FENCE_GATE, "Fences", 10),
            new ItemData("Spruce Fence", Material.SPRUCE_FENCE, "Fences", 10),
            new ItemData("Spruce Fence Gate", Material.SPRUCE_FENCE_GATE, "Fences", 10),
            new ItemData("Jungle Fence", Material.JUNGLE_FENCE, "Fences", 10),
            new ItemData("Jungle Fence Gate", Material.JUNGLE_FENCE_GATE, "Fences", 10),
            new ItemData("Dark Oak Fence", Material.DARK_OAK_FENCE, "Fences", 10),
            new ItemData("Dark Oak Fence Gate", Material.DARK_OAK_FENCE_GATE, "Fences", 10),
            new ItemData("Acacia Fence", Material.ACACIA_FENCE, "Fences", 10),
            new ItemData("Acacia Fence Gate", Material.ACACIA_FENCE_GATE, "Fences", 10),
            new ItemData("Mangrove Fence", Material.MANGROVE_FENCE_GATE, "Fences", 25),
            new ItemData("Mangrove Fence Gate", Material.MANGROVE_FENCE_GATE, "Fences", 25),
            new ItemData("Cherry Fence", Material.CHERRY_FENCE_GATE, "Fences", 30),
            new ItemData("Cherry Fence Gate", Material.CHERRY_FENCE_GATE, "Fences", 30),
            new ItemData("Bamboo Fence", Material.BAMBOO_FENCE_GATE, "Fences", 30),
            new ItemData("Bamboo Fence Gate", Material.BAMBOO_FENCE_GATE, "Fences", 30),
            new ItemData("Warped Fence", Material.WARPED_FENCE_GATE, "Fences", 45),
            new ItemData("Warped Fence Gate", Material.WARPED_FENCE_GATE, "Fences", 45),
            new ItemData("Crimson Fence", Material.CRIMSON_FENCE_GATE, "Fences", 45),
            new ItemData("Crimson Fence Gate", Material.CRIMSON_FENCE_GATE, "Fences", 45),
            new ItemData("Nether Brick Fence", Material.NETHER_BRICK_FENCE, "Fences", 10),

            // SIGNS
            new ItemData("Oak Sign", Material.OAK_SIGN, "Signs", 10),
            new ItemData("Oak Hanging Sign", Material.OAK_HANGING_SIGN, "Signs", 15),
            new ItemData("Birch Sign", Material.BIRCH_SIGN, "Signs", 10),
            new ItemData("Birch Hanging Sign", Material.BIRCH_HANGING_SIGN, "Signs", 15),
            new ItemData("Spruce Sign", Material.SPRUCE_SIGN, "Signs", 10),
            new ItemData("Spruce Hanging Sign", Material.SPRUCE_HANGING_SIGN, "Signs", 15),
            new ItemData("Jungle Sign", Material.JUNGLE_SIGN, "Signs", 10),
            new ItemData("Jungle Hanging Sign", Material.JUNGLE_HANGING_SIGN, "Signs", 15),
            new ItemData("Dark Oak Sign", Material.DARK_OAK_SIGN, "Signs", 10),
            new ItemData("Dark Oak Hanging Sign", Material.DARK_OAK_HANGING_SIGN, "Signs", 15),
            new ItemData("Acacia Sign", Material.ACACIA_SIGN, "Signs", 10),
            new ItemData("Acacia Hanging Sign", Material.ACACIA_HANGING_SIGN, "Signs", 15),
            new ItemData("Mangrove Sign", Material.MANGROVE_SIGN, "Signs", 25),
            new ItemData("Mangrove Hanging Sign", Material.MANGROVE_HANGING_SIGN, "Signs", 35),
            new ItemData("Cherry Sign", Material.CHERRY_SIGN, "Signs", 30),
            new ItemData("Cherry Hanging Sign", Material.CHERRY_HANGING_SIGN, "Signs", 45),
            new ItemData("Bamboo Sign", Material.BAMBOO_SIGN, "Signs", 30),
            new ItemData("Bamboo Hanging Sign", Material.BAMBOO_HANGING_SIGN, "Signs", 45),
            new ItemData("Warped Sign", Material.WARPED_SIGN, "Signs", 45),
            new ItemData("Warped Hanging Sign", Material.WARPED_HANGING_SIGN, "Signs", 60),
            new ItemData("Crimson Sign", Material.CRIMSON_SIGN, "Signs", 45),
            new ItemData("Crimson Hanging Sign", Material.CRIMSON_HANGING_SIGN, "Signs", 60)
    );

    public static List<ItemData> getItems() {
        return items;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        String title = ChatColor.stripColor(e.getView().getTitle());

        if (title.equalsIgnoreCase(npcName)) {
            NPCUtils.handleMerchantClick(e, player, npcName, NPCUtils.getNPCCategories(npcName));
        } else if (NPCUtils.getNPCCategories(npcName).contains(title)) {
            NPCUtils.handleCategoryClick(e, player, npcName);
        }
    }

}
