package me.plugin.OpenWorld.NPCs.Banker.Systems;

import me.plugin.Main;
import me.plugin.database;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class WithdrawSystem {

    public static boolean TakeFull(Player player) {
        ConfigurationSection playerData = database.get().getConfigurationSection(player.getUniqueId().toString());
        ConfigurationSection playerBankData = playerData.getConfigurationSection("Bank");
        Integer Coins = (Integer)playerData.get("Coins");
        Integer Banked = (Integer)playerBankData.get("Banked");
        Integer MaxDeposit = (Integer)playerBankData.get("MaxDepo");
        if (Banked > 0) {
            playerData.set("Coins", Coins + Banked);
            playerBankData.set("Banked", 0);
            database.save();
            Main.updateScoreboard(player);
        } else {
            player.sendMessage(ChatColor.LIGHT_PURPLE + "You need cash in your account to do this.");
        }

        return true;
    }

    public static boolean TakeHalf(Player player) {
        ConfigurationSection playerData = database.get().getConfigurationSection(player.getUniqueId().toString());
        ConfigurationSection playerBankData = playerData.getConfigurationSection("Bank");
        Integer Coins = (Integer)playerData.get("Coins");
        Integer Banked = (Integer)playerBankData.get("Banked") / 2;
        Integer MaxDeposit = (Integer)playerBankData.get("MaxDepo");
        if (Banked > 0) {
            playerData.set("Coins", Coins + Banked);
            playerBankData.set("Banked", Banked);
            database.save();
            Main.updateScoreboard(player);
        } else {
            player.sendMessage(ChatColor.LIGHT_PURPLE + "You need cash in your account to do this.");
        }

        return true;
    }

}
