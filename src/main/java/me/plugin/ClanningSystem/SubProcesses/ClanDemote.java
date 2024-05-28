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

public class ClanDemote {

    public static boolean demoteClanMember(Player owner, OfflinePlayer member) {

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

        if (ClanCreate.isInAnyClan(member.getUniqueId().toString())) {

            playerToRemoveClan = ClanCreate.getPlayerClan(member.getUniqueId().toString());

        } else {

            owner.sendMessage("This player is not in a clan");
            return false;

        }

        if (ownerClan.equalsIgnoreCase(playerToRemoveClan.toString())) {

            ownerRank = ClanCreate.getRankInClan(owner.getUniqueId().toString(), ownerClan);
            playerToRemoveRank = ClanCreate.getRankInClan(member.getUniqueId().toString(), playerToRemoveClan);

        } else {

            owner.sendMessage("This player is not in your clan");
            return false;

        }

        ConfigurationSection clanData = ClanningDatabase.get().getConfigurationSection(ownerClan);

        if (ownerRank.equalsIgnoreCase("Owner") && playerToRemoveRank.equalsIgnoreCase("Admin")) {

            //demote the player
            List<String> adminList = clanData.getStringList("Admins");
            List<String> membersList = clanData.getStringList("Members");

            adminList.remove(member.getUniqueId().toString());
            membersList.add(member.getUniqueId().toString());

            clanData.set("Admins", adminList);
            clanData.set("Members", membersList);

            ClanningDatabase.save();

            if (member.isOnline()) {
                Player onlinePlayer = member.getPlayer();
                if (onlinePlayer != null) {
                    onlinePlayer.sendMessage("You have been demoted in " + ownerClan + ".");
                    Main.updateScoreboard(onlinePlayer);
                }
            }

            owner.sendMessage("Player " + member.getName() + " has been demoted.");

            return true;

        }

        if (!ownerRank.equalsIgnoreCase("Owner")) {

            owner.sendMessage("You do not have permission to do this");

        }

        return false;

    }

}
