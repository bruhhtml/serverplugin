package me.plugin.OpenWorld.NPCs.ClanScoreboard;

import me.filoghost.holographicdisplays.api.hologram.Hologram;
import me.plugin.ClanningSystem.ClanningDatabase;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UpdateClanScoreboardTask extends BukkitRunnable {

    private final Hologram hologram;

    public UpdateClanScoreboardTask(Hologram hologram) {
        this.hologram = (Hologram) hologram;
    }

    @Override
    public void run() {
        // Fetch clan data
        List<ClanData> clans = new ArrayList<>();
        for (String clan : ClanningDatabase.get().getKeys(false)) {
            int coins = ClanningDatabase.get().getInt(clan + ".Bank", 0);
            String clanName = clan;
            if (clanName == null) {
                clanName = "Unknown";
            }
            clans.add(new ClanData(clanName, coins));
        }

        // Sort clans by total coins
        Collections.sort(clans, Comparator.comparingInt(ClanData::getTotalClanCoins).reversed());

        // Get top 10 clans
        List<ClanData> top10Clans = clans.subList(0, Math.min(10, clans.size()));

        // Update Holographic Displays hologram
        updateHologram(top10Clans);
    }

    private void updateHologram(List<ClanData> top10Clans) {
        // Clear existing lines
        hologram.getLines().clear();

        // Format the total coins
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setGroupingUsed(true);

        // Add empty lines if less than 10 clans
        if (top10Clans.size() < 10) {
            int fillSize = 10 - top10Clans.size();
            for (int i = 0; i < fillSize; i++) {
                hologram.getLines().insertText(0, "");
            }
        }

        // Add top 10 clans in reverse order
        for (int i = top10Clans.size() - 1; i >= 0; i--) {
            ClanData clanData = top10Clans.get(i);
            String formattedCoins = numberFormat.format(clanData.getTotalClanCoins());
            hologram.getLines().insertText(0, ChatColor.YELLOW + String.valueOf(i + 1) + ". " + ChatColor.RESET + clanData.getClanName() + " - " + ChatColor.YELLOW + formattedCoins + " coins");
        }

        // Add title at the end
        hologram.getLines().insertText(0, ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "" + ChatColor.UNDERLINE + "Top 10 Clans:");
    }


    public static class ClanData {
        private final String clanName;
        private final int clanBank;

        public ClanData(String clanName, int coins) {
            this.clanName = clanName;
            this.clanBank = coins;
        }

        public int getTotalClanCoins() {
            return clanBank;
        }

        public String getClanName() {
            return clanName;
        }
    }
}
