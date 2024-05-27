package me.plugin.ClanningSystem.SubProcesses;

import me.plugin.ClanningSystem.ClanningDatabase;
import me.plugin.Main;
import me.plugin.OpenWorld.OverheadScoreboard;
import me.plugin.database;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ClanRemove {

    public static boolean removePlayerFromClan(Player owner, OfflinePlayer playerToRemove) {

        String ownerClan = null;
        String playerToRemoveClan = null;
        String ownerRank = null;
        String playerToRemoveRank = null;

        if (ClanCreate.isInAnyClan(owner.getUniqueId().toString())) {

            ownerClan = ClanCreate.getPlayerClan(owner.getUniqueId().toString());

        } else {

            owner.sendMessage("You need to be in a clan to perform this action");
            return false;

        }

        if (ClanCreate.isInAnyClan(playerToRemove.getUniqueId().toString())) {

            playerToRemoveClan = ClanCreate.getPlayerClan(playerToRemove.getUniqueId().toString());

        } else {

            owner.sendMessage("This player is not in a clan");
            return false;

        }

        if (ownerClan.equalsIgnoreCase(playerToRemoveClan.toString())) {

            ownerRank = ClanCreate.getRankInClan(owner.getUniqueId().toString(), ownerClan);
            playerToRemoveRank = ClanCreate.getRankInClan(playerToRemove.getUniqueId().toString(), playerToRemoveClan);

        } else {

            owner.sendMessage("This player is not in your clan");
            return false;

        }

        ConfigurationSection ownerData = database.get().getConfigurationSection(owner.getUniqueId().toString());
        ConfigurationSection removedPlayerData = database.get().getConfigurationSection(playerToRemove.getUniqueId().toString());
        ConfigurationSection clanData = ClanningDatabase.get().getConfigurationSection(ownerClan);

        if (ownerRank.equalsIgnoreCase("Owner") && playerToRemoveRank.equalsIgnoreCase("Member")) {

            //remove the player
            removedPlayerData.set("CurrentClan", null);
            List<String> membersList = clanData.getStringList("Members");

            if (membersList == null) {
                membersList = new ArrayList<>();
            } else {

                membersList.remove(playerToRemove.getUniqueId().toString());

                if (membersList == null) {
                    membersList = new ArrayList<>();
                }

            }

            clanData.set("Members", membersList);

            database.save();
            ClanningDatabase.save();

            Main.updateScoreboard(owner);

            if (playerToRemove.isOnline()) {
                Player onlinePlayer = playerToRemove.getPlayer();
                if (onlinePlayer != null) {
                    Main.updateScoreboard(onlinePlayer);
                    OverheadScoreboard.updateOverhead(onlinePlayer);
                    onlinePlayer.sendMessage("You have been removed from the clan " + ownerClan + ".");
                }
            }

            OverheadScoreboard.updateOverhead(owner);
            owner.sendMessage("Player " + playerToRemove.getName() + " has been removed from your clan.");

            return true;

        }

        if (ownerRank.equalsIgnoreCase("Owner") && playerToRemoveRank.equalsIgnoreCase("Admin")) {

            //remove the player
            removedPlayerData.set("CurrentClan", null);
            List<String> adminsList = clanData.getStringList("Admins");

            if (adminsList == null) {
                adminsList = new ArrayList<>();
            } else {

                adminsList.remove(playerToRemove.getUniqueId().toString());

                if (adminsList == null) {
                    adminsList = new ArrayList<>();
                }

            }

            clanData.set("Admins", adminsList);

            database.save();
            ClanningDatabase.save();

            Main.updateScoreboard(owner);

            if (playerToRemove.isOnline()) {
                Player onlinePlayer = playerToRemove.getPlayer();
                if (onlinePlayer != null) {
                    Main.updateScoreboard(onlinePlayer);
                    OverheadScoreboard.updateOverhead(onlinePlayer);
                    onlinePlayer.sendMessage("You have been removed from the clan " + ownerClan + ".");
                }
            }

            OverheadScoreboard.updateOverhead(owner);
            owner.sendMessage("Player " + playerToRemove.getName() + " has been removed from your clan.");

            return true;

        }

        if (ownerRank.equalsIgnoreCase("Admin") && playerToRemoveRank.equalsIgnoreCase("Member")) {

            //remove the player
            removedPlayerData.set("CurrentClan", null);
            List<String> membersList = clanData.getStringList("Members");

            if (membersList == null) {
                membersList = new ArrayList<>();
            } else {

                membersList.remove(playerToRemove.getUniqueId().toString());

                if (membersList == null) {
                    membersList = new ArrayList<>();
                }

            }

            clanData.set("Members", membersList);

            database.save();
            ClanningDatabase.save();

            Main.updateScoreboard(owner);

            if (playerToRemove.isOnline()) {
                Player onlinePlayer = playerToRemove.getPlayer();
                if (onlinePlayer != null) {
                    Main.updateScoreboard(onlinePlayer);
                    OverheadScoreboard.updateOverhead(onlinePlayer);
                    onlinePlayer.sendMessage("You have been removed from the clan " + ownerClan + ".");
                }
            }

            OverheadScoreboard.updateOverhead(owner);
            owner.sendMessage("Player " + playerToRemove.getName() + " has been removed from " + ownerClan + ".");

            return true;

        }

        owner.sendMessage("You do not have the permission to perform this action");

        return false;

    }

}
