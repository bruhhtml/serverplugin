package me.plugin.ClanningSystem.SubProcesses;

import me.plugin.ClanningSystem.ClanningDatabase;
import me.plugin.Main;
import me.plugin.OpenWorld.OverheadScoreboard;
import me.plugin.database;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ClanAdd {

    private static final int ADD_MEMBER_COST = 150;

    public static boolean addPlayerToClan(Player owner, Player invitee) {

        if (ClanCreate.isInAnyClan(owner.getUniqueId().toString())) {
            String clanName = ClanCreate.getPlayerClan(owner.getUniqueId().toString());
            String rankInClan = ClanCreate.getRankInClan(owner.getUniqueId().toString(), clanName).toString();

            if (rankInClan.equals("Owner") || rankInClan.equals("Admin")) {

                // Can Add Member

                if (ClanCreate.isInAnyClan(invitee.getUniqueId().toString())) {
                    owner.sendMessage("The person you are trying to invite is already in a clan.");
                    return false;
                } else {

                    // Add player to clan

                    ConfigurationSection ownerData = database.get().getConfigurationSection(owner.getUniqueId().toString());
                    ConfigurationSection inviteeData = database.get().getConfigurationSection(invitee.getUniqueId().toString());
                    ConfigurationSection clanData = ClanningDatabase.get().getConfigurationSection(clanName);

                    if (ownerData == null || inviteeData == null || clanData == null) {
                        owner.sendMessage("An error occurred while accessing the clan data.");
                        return false;
                    }

                    int ownerCash = ownerData.getInt("Coins");

                    if (ownerCash >= ADD_MEMBER_COST) {

                        // Handle clan addition logic here
                        ownerData.set("Coins", ownerCash - ADD_MEMBER_COST);
                        List<String> membersList = clanData.getStringList("Members");

                        if (membersList == null) {
                            membersList = new ArrayList<>();
                        }

                        membersList.add(invitee.getUniqueId().toString());
                        clanData.set("Members", membersList);
                        inviteeData.set("CurrentClan", clanName);

                        database.save();
                        ClanningDatabase.save();

                        Main.updateScoreboard(owner);
                        Main.updateScoreboard(invitee);

                        OverheadScoreboard.updateOverhead(owner);
                        OverheadScoreboard.updateOverhead(invitee);

                        owner.sendMessage("Player " + invitee.getName() + " has been added to your clan.");
                        invitee.sendMessage("You have been added to the clan " + clanName + ".");

                        return true;

                    } else {
                        owner.sendMessage("You need " + ADD_MEMBER_COST + " coins to add a member to the clan.");
                        return false;
                    }

                }

            } else {
                owner.sendMessage("You are not a high enough rank to add players to this clan.");
                return false;
            }

        } else {
            owner.sendMessage("You need to be in a clan in order to add players to a clan.");
            return false;
        }

    }
}
