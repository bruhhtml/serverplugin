package me.plugin.ClanningSystem.SubProcesses;

import me.plugin.ClanningSystem.ClanningDatabase;
import me.plugin.OpenWorld.OverheadScoreboard;
import me.plugin.database;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ClanLeave {

    public static boolean playerLeaveClan(Player player) {

        String currentClan = null;
        String playerRank = null;
        ConfigurationSection clanData = null;
        ConfigurationSection playerData = null;
        List<String> adminList = new ArrayList<String>();
        List<String> memberList = new ArrayList<String>();

        if (ClanCreate.isInAnyClan(player.getUniqueId().toString())) {

            currentClan = ClanCreate.getPlayerClan(player.getUniqueId().toString());

        } else {

            player.sendMessage("It appears you are not in a clan");
            return false;

        }

        if (!currentClan.equals(null)) {

          playerRank = ClanCreate.getRankInClan(player.getUniqueId().toString(), currentClan);
          clanData = ClanningDatabase.get().getConfigurationSection(currentClan);
          playerData = database.get().getConfigurationSection(player.getUniqueId().toString());
          adminList = clanData.getStringList("Admins");
          memberList = clanData.getStringList("Members");

        } else {

            player.sendMessage("There was no name found for that clan.");
            return false;

        }

        switch (playerRank) {
            case "Owner":
                if (adminList.size() > 0) {

                    //SET NEW OWNER TO THE FIRST PLAYER IN ## ADMIN ## LIST

                    //REPLACE OWNER WITH ADMIN 1
                    clanData.set("Owner", adminList.get(0));
                    //REMOVE ADMIN 1 FROM ADMINS
                    adminList.remove(0);
                    //SET ADMINS TO NEW ADMIN LIST
                    clanData.set("Admins", adminList);

                    //CLEAR PLAYER CURRENT CLAN
                    playerData.set("CurrentClan", null);

                    //SAVE CHANGES
                    ClanningDatabase.save();
                    database.save();

                    //INFORM PLAYER
                    player.sendMessage("You have left " + currentClan);

                    //UPDATED IN GAME VISUALS
                    OverheadScoreboard.updateOverhead(player);

                    return true;
                }
                if (memberList.size() > 0) {

                    //SET NEW OWNER TO THE FIRST PLAYER IN ## MEMBER ## LIST

                    //REPLACE OWNER WITH MEMBER 1
                    clanData.set("Owner", memberList.get(0));
                    //REMOVE MEMBER 1 FROM MEMBERS
                    memberList.remove(0);
                    //SET MEMBERS TO NEW MEMBER LIST
                    clanData.set("Admins", memberList);

                    //CLEAR PLAYER CURRENT CLAN
                    playerData.set("CurrentClan", null);

                    //SAVE CHANGES
                    ClanningDatabase.save();
                    database.save();

                    //INFORM PLAYER
                    player.sendMessage("You have left " + currentClan);

                    //UPDATED IN GAME VISUALS
                    OverheadScoreboard.removePlayerFromClan(player, currentClan);

                    return true;
                }
                // ## ELSE ## COMPLETELY DELETE CLAN
                ClanningDatabase.get().set(currentClan, null);
                playerData.set(currentClan, null);

                //INFORM PLAYER
                player.sendMessage(currentClan + " has been deleted.");

                //SAVE CHANGES
                ClanningDatabase.save();
                database.save();

                //UPDATED IN GAME VISUALS
                OverheadScoreboard.updateOverhead(player);
                break;
            case "Admin":
                // REMOVE ## ADMIN ## FROM LIST
                break;
            case "Member":
                // REMOVE ## MEMBER ## FROM LIST
                break;
            default:
                player.sendMessage("There was no rank found for you in that clan.");
                break;
        }

        return false;

    }

}
