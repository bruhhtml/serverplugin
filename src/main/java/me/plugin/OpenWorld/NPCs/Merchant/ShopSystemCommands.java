package me.plugin.OpenWorld.NPCs.Merchant;

import me.plugin.OpenWorld.NPCs.Merchant.GUIs.CategoryGUI;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ShopSystemCommands implements CommandExecutor {

//    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
//        Player player;
//        if (sender instanceof Player && command.getName().equalsIgnoreCase("OpenWorldShopGUI")) {
//            player = (Player)sender;
//            CategoryGUI.openGUI(player);
//            return true;
//        } else {
//
//            return false;
//
//        }
//
//    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("OpenWorldShopGUI")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                NPCRegistry registry = CitizensAPI.getNPCRegistry();

                boolean isNPC = false;
                for (NPC npc : registry) {
                    if (npc.isSpawned() && npc.getEntity().equals(player)) {
                        isNPC = true;
                        break;
                    }
                }

                if (isNPC) {
                    if (args.length > 0) {
                        Player targetPlayer = sender.getServer().getPlayer(args[0]);
                        if (targetPlayer != null) {
                            targetPlayer.sendMessage("NPC triggered GUI opening."); // For testing purposes
                            return true;
                        } else {
                            sender.sendMessage("Player not found.");
                            return false;
                        }
                    } else {
                        sender.sendMessage("No player specified.");
                        return false;
                    }
                } else {
                    player.sendMessage("This command can only be run by interacting with an NPC.");
                    return false;
                }
            } else {
                sender.sendMessage("This command can only be run by a player or an NPC.");
                return false;
            }
        }
        return false;
    }

}
