package me.plugin.FriendSystem;

import me.plugin.database;

import net.md_5.bungee.api.chat.*;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FriendSystemMain implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("Friend") || command.getName().equalsIgnoreCase("FriendAccept")) {

            Player player = Bukkit.getPlayer(sender.getName().toString());

            player.sendMessage(ChatColor.RED + "[SERVER] " + ChatColor.RESET + "This command is not active yet.");

        }

//        if (command.getName().equalsIgnoreCase("Friend") && sender instanceof Player && !sender.getName().toString().equalsIgnoreCase(args[0])) {
//            // Send Friend Request Command
//            if (command.getName().equalsIgnoreCase("friend") && !database.get().getConfigurationSection(((Player)sender).getUniqueId().toString()).getConfigurationSection("Friends").contains(Bukkit.getPlayer(args[0]).getUniqueId().toString())) {
//                Player receiver = Bukkit.getPlayer(args[0]);
//                TextComponent acceptText = new TextComponent("Click here to accept!");
//                acceptText.setColor(ChatColor.BLUE.asBungee());
//                acceptText.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/friendaccept " + sender.getName().toString()));
//                acceptText.setHoverEvent(new HoverEvent(net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder("Click to accept")).color(ChatColor.DARK_BLUE.asBungee()).italic(true).create()));
//                receiver.spigot().sendMessage(new BaseComponent[]{new TextComponent("New friend request from " + sender.getName().toString() + ". "), acceptText});
//            } else if (command.getName().equalsIgnoreCase("friend") && database.get().getConfigurationSection(((Player)sender).getUniqueId().toString()).getConfigurationSection("Friends").contains(Bukkit.getPlayer(args[0]).getUniqueId().toString())) {
//                sender.sendMessage("Player already friended!");
//            }
//            // Accept Friend Request Command
//            if (command.getName().equalsIgnoreCase("FriendAccept")) {
//                if (database.get().getConfigurationSection(((Player)sender).getUniqueId().toString()).getConfigurationSection("Friends").contains(Bukkit.getPlayer(args[0]).getUniqueId().toString())) {
//                    sender.sendMessage("No request inbound!");
//                } else {
//                    database.get().getConfigurationSection(((Player)sender).getUniqueId().toString()).getConfigurationSection("Friends").set(Bukkit.getPlayer(args[0]).getUniqueId().toString(), 1);
//                    database.get().getConfigurationSection(Bukkit.getPlayer(args[0]).getUniqueId().toString()).getConfigurationSection("Friends").set(((Player)sender).getUniqueId().toString(), 1);
//                    database.save();
//                    sender.sendMessage("You accepted a friend request!");
//                    Bukkit.getPlayer(args[0]).sendMessage("Friend request accepted!");
//                }
//            }
//
//        }

        return true;

    }

}

