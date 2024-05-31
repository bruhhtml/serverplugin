package me.plugin.OpenWorld.NPCs.Builder;

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

public class BuilderNPCMain implements Listener {

    private static final String npcName = "Builder";

    private static final List<ItemData> items = Arrays.asList(
            // BLOCKS
            new ItemData("Cobblestone", Material.COBBLESTONE, "Blocks", 2),
            new ItemData("Cobblestone Wall", Material.COBBLESTONE_WALL, "Blocks", 4),
            new ItemData("Mossy Cobblestone", Material.MOSSY_COBBLESTONE, "Blocks", 4),
            new ItemData("Mossy Cobblestone Wall", Material.MOSSY_COBBLESTONE_WALL, "Blocks", 6),
            new ItemData("Stone", Material.STONE, "Blocks", 3),
            new ItemData("Smooth Stone", Material.SMOOTH_STONE, "Blocks", 6),
            new ItemData("Stone Bricks", Material.STONE_BRICKS, "Blocks", 10),
            new ItemData("Stone Brick Wall", Material.STONE_BRICK_WALL, "Blocks", 12),
            new ItemData("Chiseled Stone Bricks", Material.CHISELED_STONE_BRICKS, "Blocks", 15),
            new ItemData("Mossy Stone Bricks", Material.MOSSY_STONE_BRICKS, "Blocks", 17),
            new ItemData("Mossy Stone Brick Wall", Material.MOSSY_STONE_BRICK_WALL, "Blocks", 20),
            new ItemData("Deepslate", Material.DEEPSLATE, "Blocks", 75),
            new ItemData("Cobbled Deepslate", Material.COBBLED_DEEPSLATE, "Blocks", 50),
            new ItemData("Cobbled Deepslate Wall", Material.COBBLED_DEEPSLATE_WALL, "Blocks", 55),
            new ItemData("Polished Deepslate", Material.POLISHED_DEEPSLATE, "Blocks", 60),
            new ItemData("Polished Deepslate Wall", Material.POLISHED_DEEPSLATE_WALL, "Blocks", 65),
            new ItemData("Deepslate Bricks", Material.DEEPSLATE_BRICKS, "Blocks", 80),
            new ItemData("Deepslate Brick Wall", Material.DEEPSLATE_BRICK_WALL, "Blocks", 85),
            new ItemData("Deepslate Tiles", Material.DEEPSLATE_TILES, "Blocks", 90),
            new ItemData("Deepslate Tile Wall", Material.DEEPSLATE_TILE_WALL, "Blocks", 95),
            new ItemData("Chiseled Deepslate", Material.CHISELED_DEEPSLATE, "Blocks", 110),
            new ItemData("Reinforced Deepslate", Material.REINFORCED_DEEPSLATE, "Blocks", 200),
            new ItemData("Diorite", Material.DIORITE, "Blocks", 4),
            new ItemData("Diorite Wall", Material.DIORITE_WALL, "Blocks", 8),
            new ItemData("Polished Diorite", Material.POLISHED_DIORITE, "Blocks", 10),
            new ItemData("Andesite", Material.ANDESITE, "Blocks", 4),
            new ItemData("Andesite Wall", Material.ANDESITE_WALL, "Blocks", 8),
            new ItemData("Polished Andesite", Material.POLISHED_ANDESITE, "Blocks", 10),
            new ItemData("Granite", Material.GRANITE, "Blocks", 4),
            new ItemData("Granite Wall", Material.GRANITE_WALL, "Blocks", 8),
            new ItemData("Polished Granite", Material.POLISHED_GRANITE, "Blocks", 10),
            new ItemData("Tuff", Material.TUFF, "Blocks", 70),
            new ItemData("Calcite", Material.CALCITE, "Blocks", 100),
            new ItemData("Bricks", Material.BRICKS, "Blocks", 2),
            new ItemData("Brick Wall", Material.BRICK_WALL, "Blocks", 4),
            new ItemData("Block Of Quartz", Material.QUARTZ_BLOCK, "Blocks", 6),
            new ItemData("Smooth Quartz Block", Material.SMOOTH_QUARTZ, "Blocks", 10),
            new ItemData("Chiseled Quartz Block", Material.CHISELED_QUARTZ_BLOCK, "Blocks", 14),
            new ItemData("Quartz Pillar", Material.QUARTZ_PILLAR, "Blocks", 16),
            new ItemData("Quartz Bricks", Material.QUARTZ_BRICKS, "Blocks", 8),

            // NATURE
            new ItemData("Dirt", Material.DIRT, "Nature", 1),
            new ItemData("Grass Block", Material.GRASS_BLOCK, "Nature", 1),
            new ItemData("Mycelium", Material.MYCELIUM, "Nature", 5),
            new ItemData("Podzol", Material.PODZOL, "Nature", 5),
            new ItemData("Coarse Dirt", Material.COARSE_DIRT, "Nature", 5),
            new ItemData("Rooted Dirt", Material.ROOTED_DIRT, "Nature", 5),
            new ItemData("Oak Log", Material.OAK_LOG, "Nature", 2),
            new ItemData("Oak Planks", Material.OAK_PLANKS, "Nature", 4),
            new ItemData("Birch Log", Material.BIRCH_LOG, "Nature", 2),
            new ItemData("Birch Planks", Material.BIRCH_PLANKS, "Nature", 4),
            new ItemData("Spruce Log", Material.SPRUCE_LOG, "Nature", 2),
            new ItemData("Spruce Planks", Material.SPRUCE_PLANKS, "Nature", 4),
            new ItemData("Jungle Log", Material.JUNGLE_LOG, "Nature", 6),
            new ItemData("Jungle Planks", Material.JUNGLE_PLANKS, "Nature", 8),
            new ItemData("Dark Oak Log", Material.DARK_OAK_LOG, "Nature", 2),
            new ItemData("Dark Planks", Material.DARK_OAK_PLANKS, "Nature", 4),
            new ItemData("Acacia Log", Material.ACACIA_LOG, "Nature", 8),
            new ItemData("Acacia Planks", Material.ACACIA_PLANKS, "Nature", 10),
            new ItemData("Mangrove Log", Material.MANGROVE_LOG, "Nature", 8),
            new ItemData("Mangrove Planks", Material.MANGROVE_PLANKS, "Nature", 10),
            new ItemData("Cherry Log", Material.CHERRY_LOG, "Nature", 6),
            new ItemData("Cherry Plankls", Material.CHERRY_PLANKS, "Nature", 8),
            new ItemData("Block Of Bamboo", Material.BAMBOO_BLOCK, "Nature", 2),
            new ItemData("Bamboo Planks", Material.BAMBOO_PLANKS, "Nature", 4),
            new ItemData("Bamboo Mosaic", Material.BAMBOO_MOSAIC, "Nature", 10),
            new ItemData("Warped Stem", Material.WARPED_STEM, "Nature", 5),
            new ItemData("Warped Planks", Material.WARPED_PLANKS, "Nature", 10),
            new ItemData("Crimson Stem", Material.CRIMSON_STEM, "Nature", 5),
            new ItemData("Crimson Planks", Material.CRIMSON_PLANKS, "Nature", 10)
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
