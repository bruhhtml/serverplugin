package me.plugin.MainLobby;

import me.plugin.OpenWorld.CombatLogSystem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LobbyCommands implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("Lobby")) {

            Player player = (Player) sender;

            Location loc = Bukkit.getWorld("Lobby").getSpawnLocation();

            if (CombatLogSystem.checkCombat(player) == false) {

                player.teleport(loc);

            } else {

                player.sendMessage(ChatColor.RED + "[SERVER] " + ChatColor.RESET + "You are currently in combat, please wait and try again!");

            }



        } else if (command.getName().equalsIgnoreCase("OverWorld")) {

            Player player = (Player) sender;

            Location loc = Bukkit.getWorld("World").getSpawnLocation();

            player.teleport(loc);

        }
//        else if (command.getName().equalsIgnoreCase("UpdateLeaderboard")) {
//
//            List playerCoinList = new ArrayList<String[][]>();
//
////            String[][] playerCoinList = {{}};
//
//            for(String s : database.get().getKeys(false)) {
//
//                playerCoinList.add(s);
//
//            }
//
//            Collections.sort(playerCoinList);
//        }

        return true;

    }

}
