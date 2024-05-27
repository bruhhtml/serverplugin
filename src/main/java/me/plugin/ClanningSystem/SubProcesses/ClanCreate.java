package me.plugin.ClanningSystem.SubProcesses;

import me.plugin.ClanningSystem.ClanningCommands;
import org.bukkit.plugin.Plugin;
import me.plugin.ClanningSystem.ClanningDatabase;
import me.plugin.Main;
import me.plugin.OpenWorld.OverheadScoreboard;
import me.plugin.database;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

public class ClanCreate {

    private static int clanCost = 5000;

    public static String getPlayerClan(String playerUUID) {
        FileConfiguration clansConfig = ClanningDatabase.get();

        for (String clan : clansConfig.getKeys(false)) {
            if (clansConfig.getString(clan + ".Owner").contains(playerUUID)) {
                return clan; // Player is the owner of this clan
            }
            if (clansConfig.getStringList(clan + ".Admins").contains(playerUUID)) {
                return clan; // Player is an admin of this clan
            }
            if (clansConfig.getStringList(clan + ".Members").contains(playerUUID)) {
                return clan; // Player is a member of this clan
            }
        }

        return null; // Player is not in any clan
    }

    public static String getRankInClan(String playerUUID, String clan) {
        FileConfiguration clansConfig = ClanningDatabase.get();

        if (clansConfig.getString(clan + ".Owner").equals(playerUUID)) {
            return "Owner"; // Player is the owner of this clan
        }
        if (clansConfig.getStringList(clan + ".Admins").contains(playerUUID)) {
            return "Admin"; // Player is an admin of this clan
        }
        if (clansConfig.getStringList(clan + ".Members").contains(playerUUID)) {
            return "Member"; // Player is a member of this clan
        }
        return null;
    }

    public static boolean isInAnyClan(String playerUUID) {
        FileConfiguration clansConfig = ClanningDatabase.get();
        for (String clan : clansConfig.getKeys(false)) {
            String owner = clansConfig.getString(clan + ".Owner");
            List<String> admins = clansConfig.getStringList(clan + ".Admins");
            List<String> members = clansConfig.getStringList(clan + ".Members");

            if (playerUUID.equals(owner) || admins.contains(playerUUID) || members.contains(playerUUID)) {
                return true;
            }
        }
        return false;
    }

    private static boolean clanExists(String clanName) {
        for (String existingClan : ClanningDatabase.get().getKeys(false)) {
            if (existingClan.equalsIgnoreCase(clanName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean createClan(Player player, String[] args) {

        if (args.length < 2) {
            player.sendMessage("Please specify a clan name.");
            return false;
        }

        String clanName = String.join(" ", Arrays.copyOfRange(args, 1, args.length));

        if (clanName.length() > 20) {
            player.sendMessage("Clan name cannot be longer than 20 characters.");
            return false;
        }

        if (clanExists(clanName)) {
            player.sendMessage("Clan name already exists.");
            return false;
        }

        if (isInAnyClan(player.getUniqueId().toString())) {
            player.sendMessage("You are already in a clan or own a clan. You cannot create a new one.");
            return false;
        }

        ConfigurationSection playerData = database.get().getConfigurationSection(player.getUniqueId().toString());
        int playerCash = playerData.getInt("Coins");

        if (playerCash >= clanCost) {

            // Handle clan creation logic here
            playerData.set("Coins", playerCash - clanCost);
            ClanningDatabase.loadpreset(clanName, player);
            playerData.set("CurrentClan", clanName);
            database.save();
            Main.updateScoreboard(player);

            if (!OverheadScoreboard.updateOverhead(player)) {

                player.sendMessage("You are not in a clan");

            }
            player.sendMessage("Clan " + clanName + " created!");
            return true;

        } else {

            player.sendMessage("You need 5k coins to make a clan");
            return false;

        }

    }

}
