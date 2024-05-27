package me.plugin.OpenWorld.NPCs.CashScoreboard;

import me.plugin.database;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.HologramTrait;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UpdateCashScoreboardTask extends BukkitRunnable {

    @Override
    public void run() {
        // Fetch player data
        FileConfiguration databaseFile = database.get();
        List<CashLeaderboardCommand.PlayerData> players = new ArrayList<>();

        for (String playerId : databaseFile.getKeys(false)) {
            int coins = databaseFile.getInt(playerId + ".Coins", 0);
            int banked = databaseFile.getInt(playerId + ".Bank.Banked", 0);
            String playerName = databaseFile.getString(playerId + ".PlayerName");

            if (playerName == null) {
                playerName = "Unknown";
            }

            players.add(new CashLeaderboardCommand.PlayerData(playerName, coins, banked));
        }

        // Sort players by total coins
        Collections.sort(players, Comparator.comparingInt(CashLeaderboardCommand.PlayerData::getTotalCoins).reversed());

        // Get top 10 players
        List<CashLeaderboardCommand.PlayerData> top10Players = players.subList(0, Math.min(10, players.size()));

        // Update NPC Hologram
        updateHologram(top10Players);
    }

    private void updateHologram(List<CashLeaderboardCommand.PlayerData> top10Players) {
        NPC npc = CitizensAPI.getNPCRegistry().getById(14); // Ensure this is the correct NPC ID

        if (npc != null) {
            HologramTrait hologram = npc.getOrAddTrait(HologramTrait.class);
            hologram.clear();

            // Assuming getTotalCoins() returns a numerical value
            // Get the NumberFormat instance for formatting
            NumberFormat numberFormat = NumberFormat.getInstance();

            // Set the formatting style to use commas for every thousand
            numberFormat.setGroupingUsed(true);

            for (int i = top10Players.size() - 1; i >= 0; i--) {
                CashLeaderboardCommand.PlayerData playerData = top10Players.get(i); // Get the player data in reverse order
                // Format the total coins
                String formattedCoins = numberFormat.format(playerData.getTotalCoins());
                hologram.addLine(ChatColor.YELLOW + String.valueOf(i + 1) + ". " + ChatColor.RESET + playerData.getPlayerName() + " - " + ChatColor.YELLOW + formattedCoins + " coins");
            }

            hologram.addLine(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "" + ChatColor.UNDERLINE + "Top 10 Richest Players:"); // Add the title first
        }
    }

    public static class PlayerData {
        private final String playerName;
        private final int coins;
        private final int banked;

        public PlayerData(String playerName, int coins, int banked) {
            this.playerName = playerName;
            this.coins = coins;
            this.banked = banked;
        }

        public int getTotalCoins() {
            return coins + banked;
        }

        public String getPlayerName() {
            return playerName;
        }
    }

}
