package me.plugin.ClanningSystem.SubProcesses;

import me.plugin.ClanningSystem.ClanningDatabase;
import me.plugin.Main;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ClanPromote {

    public static boolean promoteClanPlayer(Player owner, Player member) {

        String ownerClan = ClanCreate.getPlayerClan(owner.getUniqueId().toString());
        String memberClan = ClanCreate.getPlayerClan(member.getUniqueId().toString());

        String ownerRank = null;
        String memberRank = null;

        ConfigurationSection clanData = null;

        List<String> adminList = new ArrayList<String>();
        List<String> memberList = new ArrayList<String>();


        if (ownerClan.equals(null)) {

            owner.sendMessage("You need to be in a clan to perform this action");
            return false;

        }

        if (memberClan.equals(null)) {

            owner.sendMessage("This player is not in a clan");
            return false;

        }

        ownerRank = ClanCreate.getRankInClan(owner.getUniqueId().toString(), ownerClan);

        if (!ownerRank.equals("Owner")) {

            owner.sendMessage("You need to be the clan owner to perform this action");
            return false;

        }

        if (!memberClan.equals(ownerClan)) {

            owner.sendMessage("This player is not in your clan");
            return false;

        }

        memberRank = ClanCreate.getRankInClan(member.getUniqueId().toString(), memberClan);

        if (memberRank.equals("Owner")) {

            owner.sendMessage("You can not promote the owner");
            return false;

        }

        if (memberRank.equals("Admin")) {

            owner.sendMessage("You can not promote an admin");
            return false;

        }

        clanData = ClanningDatabase.get().getConfigurationSection(ownerClan);
        adminList = clanData.getStringList("Admins");
        memberList = clanData.getStringList("Members");

        adminList.add(member.getUniqueId().toString());
        memberList.remove(member.getUniqueId().toString());

        clanData.set("Admins", adminList);
        clanData.set("Members", memberList);

        ClanningDatabase.save();

        Main.updateScoreboard(owner);
        Main.updateScoreboard(member);

        owner.sendMessage(member.getName() + " has been promoted to admin.");
        member.sendMessage("You have been promoted to admin in " + ownerClan);

        return true;

    }

}
