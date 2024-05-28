package me.plugin.OpenWorld;

import me.plugin.ClanningSystem.SubProcesses.ClanCreate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OverheadScoreboard {
    private static final Map<Player, String> overheadDisplayNames = new ConcurrentHashMap<>();

    public static boolean updateOverhead(Player player) {
        if (player == null) {
            System.out.println("Player is null");
            return false;
        }

        String clanName = ClanCreate.getPlayerClan(player.getUniqueId().toString());

        if (clanName != null && !clanName.isEmpty()) {
            ScoreboardManager manager = Bukkit.getScoreboardManager();
            if (manager == null) {
                System.out.println("ScoreboardManager is null");
                return false;
            }

            Scoreboard scoreboard = manager.getMainScoreboard();
            Team clanTeam = scoreboard.getTeam(clanName);
            if (clanTeam == null) {
                clanTeam = scoreboard.registerNewTeam(clanName);
                clanTeam.setDisplayName(clanName);
                clanTeam.setPrefix(ChatColor.GREEN + " [" + ChatColor.RESET + clanName + ChatColor.GREEN + "] " + ChatColor.RESET);
                clanTeam.setCanSeeFriendlyInvisibles(true); // Optional: set team options
                clanTeam.setAllowFriendlyFire(false); // Optional: set team options
            }

            // Ensure player is added to the team
            if (!clanTeam.hasEntry(player.getName())) {
                clanTeam.addEntry(player.getName());
            }

            // Store the overhead display name
            String overheadDisplayName = ChatColor.GREEN + " [" + ChatColor.RESET + clanName + ChatColor.GREEN + "] " + ChatColor.RESET + player.getName();
            overheadDisplayNames.put(player, overheadDisplayName);

            // Update the player's display name and list name
            player.setDisplayName(overheadDisplayName);
            player.setPlayerListName(overheadDisplayName);

            return true;
        } else {
            player.sendMessage(ChatColor.RED + "You are not in any clan.");
            return false;
        }
    }

    public static void removePlayerFromClan(Player player, String clanName) {
        if (player == null) {
            return;
        }

        if (clanName != null && !clanName.isEmpty()) {
            ScoreboardManager manager = Bukkit.getScoreboardManager();
            if (manager != null) {
                Scoreboard scoreboard = manager.getMainScoreboard();
                Team clanTeam = scoreboard.getTeam(clanName);
                if (clanTeam != null) {
                    clanTeam.removeEntry(player.getName());
                }
            }
        }

        overheadDisplayNames.remove(player);
        player.setDisplayName(player.getName());
        player.setPlayerListName(player.getName());
    }
}
