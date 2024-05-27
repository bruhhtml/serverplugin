package me.plugin.MainLobby.NPCs.Banker.Systems;

import me.plugin.Main;
import me.plugin.database;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;

public class DepositSystem implements Listener {

    public static ArrayList<Player> bannedRecipients = new ArrayList();

    public static boolean DepoFull(Player player) {
        ConfigurationSection playerData = database.get().getConfigurationSection(player.getUniqueId().toString());
        ConfigurationSection playerBankData = playerData.getConfigurationSection("Bank");
        Integer Coins = (Integer)playerData.get("Coins");
        Integer Banked = (Integer)playerBankData.get("Banked");
        Integer MaxDeposit = (Integer)playerBankData.get("MaxDepo");
        if (Banked + Coins >= MaxDeposit) {
            if (Banked < MaxDeposit) {
                playerBankData.set("Banked", MaxDeposit);
                playerData.set("Coins", Coins - (MaxDeposit - Banked));
                database.save();
                Main.updateScoreboard(player);
            } else {
                player.sendMessage(ChatColor.LIGHT_PURPLE + "You need to upgrade your bank to do this.\nThis can be done by leveling up!");
            }
        } else if (Banked + Coins < MaxDeposit) {
            playerBankData.set("Banked", Banked + Coins);
            playerData.set("Coins", 0);
            database.save();
            Main.updateScoreboard(player);
        }

        return true;
    }

    public static boolean DepoHalf(Player player) {
        ConfigurationSection playerData = database.get().getConfigurationSection(player.getUniqueId().toString());
        ConfigurationSection playerBankData = playerData.getConfigurationSection("Bank");
        Integer Coins = (Integer)playerData.get("Coins") / 2;
        Integer Banked = (Integer)playerBankData.get("Banked");
        Integer MaxDeposit = (Integer)playerBankData.get("MaxDepo");
        if (Banked + Coins >= MaxDeposit) {
            if (Banked < MaxDeposit) {
                playerBankData.set("Banked", MaxDeposit);
                playerData.set("Coins", Coins - (MaxDeposit - Banked));
                database.save();
                Main.updateScoreboard(player);
            } else {
                player.sendMessage(ChatColor.LIGHT_PURPLE + "You need to upgrade your bank to do this.\nThis can be done by leveling up!");
            }
        } else if (Banked + Coins < MaxDeposit) {
            playerBankData.set("Banked", Banked + Coins);
            playerData.set("Coins", Coins);
            database.save();
            Main.updateScoreboard(player);
        }

        return true;
    }

}
