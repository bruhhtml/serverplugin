package me.plugin.MainLobby.NPCs.CashScoreboard;

import me.filoghost.holographicdisplays.api.hologram.Hologram;
import me.plugin.database;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UpdateCashScoreboardTask extends BukkitRunnable {

    private final Hologram richestPlayersLeaderboard;

    public UpdateCashScoreboardTask(Hologram hologram) {
        this.richestPlayersLeaderboard = hologram;
    }

    @Override
    public void run() {
        // Fetch clan data
        List<UpdateCashScoreboardTask.PlayerData> players = new ArrayList<>();
        for (String player : database.get().getKeys(false)) {
            String playerName = database.get().getString(player + ".PlayerName");
            int coins = database.get().getInt(player + ".Coins", 0);
            int banked = database.get().getInt(player + ".Bank.Banked", 0);
            if (playerName == null) {
                playerName = "Unknown";
            } else {

                players.add(new UpdateCashScoreboardTask.PlayerData(playerName, coins, banked));

            }
        }

        // Sort clans by total coins
        Collections.sort(players, Comparator.comparingInt(UpdateCashScoreboardTask.PlayerData::getTotalCoins).reversed());

        // Get top 10 clans
        List<UpdateCashScoreboardTask.PlayerData> top10Players = players.subList(0, Math.min(10, players.size()));

        // Update Holographic Displays hologram
        updateHologram(top10Players);
    }

    private void updateHologram(List<UpdateCashScoreboardTask.PlayerData> top10Players) {
        // Clear existing lines
        richestPlayersLeaderboard.getLines().clear();

        // Format the total coins
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setGroupingUsed(true);
//
//        // Add empty lines if less than 10 clans
//        if (top10Players.size() < 10) {
//            int fillSize = 10 - top10Players.size();
//            for (int i = 0; i < fillSize; i++) {
//                richestPlayersLeaderboard.getLines().insertText(0, "");
//            }
//        }

        // Add top 10 clans in reverse order
        for (int i = top10Players.size() - 1; i >= 0; i--) {
            UpdateCashScoreboardTask.PlayerData playerData = top10Players.get(i);
            String formattedCoins = numberFormat.format(playerData.getTotalCoins());
            richestPlayersLeaderboard.getLines().insertText(0, ChatColor.YELLOW + String.valueOf(i + 1) + ". " + ChatColor.RESET + playerData.getPlayerName() + " - " + ChatColor.YELLOW + formattedCoins + " coins");
        }

        // Add title at the end
        richestPlayersLeaderboard.getLines().insertText(0, "{Rainbow}Richest Players");
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
