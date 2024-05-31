package me.plugin.OpenWorld.NPCs.Banker;

import me.plugin.Main;
import me.plugin.OpenWorld.NPCs.Banker.GUIs.BankGUI;
import me.plugin.database;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BankSystemCommands implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player;
        if (sender instanceof Player && command.getName().equalsIgnoreCase("BankGui")) {
            player = (Player)sender;
            if (database.get().getConfigurationSection(player.getUniqueId().toString()).getConfigurationSection("Bank").get("Active").equals(true)) {
                BankGUI.openGUI(player);
            }
        }

        if (sender instanceof Player && command.getName().equalsIgnoreCase("EnableBanking")) {
            player = (Player)sender;
            if (database.get().getConfigurationSection(player.getUniqueId().toString()).getConfigurationSection("Bank").get("Active").equals(false)) {
                database.get().getConfigurationSection(player.getUniqueId().toString()).getConfigurationSection("Bank").set("Active", true);
                database.save();
            }
        }

        if (sender instanceof Player && command.getName().equalsIgnoreCase("SetCash")) {
            database.get().getConfigurationSection(((Player)sender).getUniqueId().toString()).set("Coins", Integer.parseInt(args[0]));
            database.save();
            Main.updateScoreboard(((Player)sender).getPlayer());
        }

        return true;
    }

}
