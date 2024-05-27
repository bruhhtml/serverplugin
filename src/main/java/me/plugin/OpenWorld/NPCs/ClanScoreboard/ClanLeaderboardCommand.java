package me.plugin.OpenWorld.NPCs.ClanScoreboard;

import me.plugin.ClanningSystem.ClanningDatabase;
import me.plugin.database;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.HologramTrait;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ClanLeaderboardCommand implements CommandExecutor  {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("UpdateClanLeaderboard")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("This command can only be run by a player.");
                return true;
            }

            Player player = (Player) sender;

            // Fetch player data
            FileConfiguration databaseFile = ClanningDatabase.get();
            List<ClanData> clans = new ArrayList<>();

            for (String clan : databaseFile.getKeys(false)) {
                int coins = databaseFile.getInt(clan + ".Bank", 0);
                String clanName = clan;

                if (clanName == null) {
                    clanName = "Unknown";
                }

                clans.add(new ClanData(clanName, coins));
            }

            // Sort players by total coins
            Collections.sort(clans, Comparator.comparingInt(ClanData::getTotalClanCoins).reversed());

            // Get top 10 players
            List<ClanData> top10Clans = clans.subList(0, Math.min(10, clans.size()));

            // Update NPC Hologram
            updateHologram(top10Clans);

            player.sendMessage("Leaderboard updated!");
            return true;
        }
        return false;
    }

    private void updateHologram(List<ClanData> top10Clans) {
        NPC npc = CitizensAPI.getNPCRegistry().getById(15); // Ensure this is the correct NPC ID

        if (npc != null) {
            HologramTrait hologram = npc.getOrAddTrait(HologramTrait.class);
            hologram.clear();

            // Assuming getTotalCoins() returns a numerical value
            // Get the NumberFormat instance for formatting
            NumberFormat numberFormat = NumberFormat.getInstance();

            // Set the formatting style to use commas for every thousand
            numberFormat.setGroupingUsed(true);

            if (top10Clans.size() < 10) {

                int fillSize = 10 - top10Clans.size();

                for (int i = 0; i < fillSize; i++) {

                    hologram.addLine("");

                }

            }

            for (int i = top10Clans.size() - 1; i >= 0; i--) {
                ClanData clanData = top10Clans.get(i); // Get the player data in reverse order
                // Format the total coins
                String formattedCoins = numberFormat.format(clanData.getTotalClanCoins());
                hologram.addLine(ChatColor.YELLOW + String.valueOf(i + 1) + ". " + ChatColor.RESET + clanData.getClanName() + " - " + ChatColor.YELLOW + formattedCoins + " coins");
            }

            hologram.addLine(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "" + ChatColor.UNDERLINE + "Top 10 Clans:"); // Add the title first
        }
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
