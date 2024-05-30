package me.plugin.ClanningSystem;

import me.plugin.ClanningSystem.SubProcesses.*;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static me.plugin.Main.updateScoreboard;

public class ClanningCommands implements CommandExecutor, TabCompleter {

    private final List<String> subCommands = Arrays.asList("create", "add", "remove", "promote", "demote", "leave");

    public ClanningCommands(JavaPlugin plugin) {
        plugin.getCommand("clan").setExecutor(this);
        plugin.getCommand("clan").setTabCompleter(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage("Please specify a subcommand: create, add, remove, leave");
            return true;
        }

        Player player = (Player) sender;
        String subCommand = args[0].toLowerCase();

        switch (subCommand) {
            case "create":
                if (!ClanCreate.createClan(player, args)) {

                    player.sendMessage(ChatColor.RED + "[SERVER] " + ChatColor.RESET + "Use " + ChatColor.BLUE + "/clan help" + ChatColor.RESET + " for assistance");

                }
                break;
            case "add":
                // Handle add player to clan logic here
                Player invitee = Bukkit.getPlayer(args[1]);
                if (invitee.isOnline()) {
                    if (!ClanAdd.addPlayerToClan(player, invitee)) {

                        player.sendMessage(ChatColor.RED + "[SERVER] " + ChatColor.RESET + "Use " + ChatColor.BLUE + "/clan help" + ChatColor.RESET + " for assistance");

                    }
                }
                break;
            case "remove":

                String targetPlayerName = args[1];
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(targetPlayerName);

                ClanRemove.removePlayerFromClan(player, offlinePlayer);

                // Handle remove player from clan logic here
//                player.sendMessage("Player removed from clan!");
                break;
            case "promote":
                Player member = Bukkit.getPlayer(args[1]);
                if (member.isOnline()) {
                    if (!ClanPromote.promoteClanPlayer(player, member)) {

                        player.sendMessage(ChatColor.RED + "[SERVER] " + ChatColor.RESET + "Use " + ChatColor.BLUE + "/clan help" + ChatColor.RESET + " for assistance");

                    }
                }
                break;
            case "demote":
                if (!ClanDemote.demoteClanMember(player, Bukkit.getOfflinePlayer(args[1]))) {

                    player.sendMessage(ChatColor.RED + "[SERVER] " + ChatColor.RESET + "Use " + ChatColor.BLUE + "/clan help" + ChatColor.RESET + " for assistance");

                }
                break;
            case "leave":
                // Handle leave clan logic here

                ClanLeave.playerLeaveClan(player);

//                player.sendMessage("You left the clan!");
                break;
            default:
                player.sendMessage("Unknown subcommand. Available subcommands: create, add, remove, leave");
                break;
        }
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            List<String> suggestions = new ArrayList<>();
            for (String subCommand : subCommands) {
                if (subCommand.startsWith(args[0].toLowerCase())) {
                    suggestions.add(subCommand);
                }
            }
            return suggestions;
        }
        return null;
    }

}
