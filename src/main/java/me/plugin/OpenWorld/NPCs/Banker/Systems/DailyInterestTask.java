package me.plugin.OpenWorld.NPCs.Banker.Systems;

import me.plugin.Main;
import me.plugin.database;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class DailyInterestTask extends BukkitRunnable {

    private final double DAILY_INTEREST_RATE = 0.02; // 2% daily interest rate

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            int balance = getAccountBalance(player);
            int interest = calculateDailyInterest(balance);
            updateAccountBalance(player, interest,balance + interest);
        }
    }

    private int calculateDailyInterest(int balance) {
        return (int) Math.round(balance * DAILY_INTEREST_RATE);
    }

    private int getAccountBalance(Player player) {
        ConfigurationSection playerData = database.get().getConfigurationSection(player.getUniqueId().toString());
        if (playerData != null) {
            ConfigurationSection playerBankData = playerData.getConfigurationSection("Bank");
            if (playerBankData != null) {
                return playerBankData.getInt("Banked");
            }
        }
        return 0; // Default balance if data is missing or corrupted
    }

    private void updateAccountBalance(Player player, int interest, int newBalance) {
        ConfigurationSection playerData = database.get().getConfigurationSection(player.getUniqueId().toString());
        if (playerData != null) {
            ConfigurationSection playerBankData = playerData.getConfigurationSection("Bank");
            if (playerBankData != null) {
                if (newBalance > (Integer) playerBankData.get("MaxDepo")) {

                    player.sendMessage(ChatColor.BLUE + "[BANKER] " + ChatColor.RESET + "Upgrade bank account to keep earning interest!");

                } else {

                    if ((Integer) interest == 0) {

                        player.sendMessage(ChatColor.BLUE + "[BANKER] " + ChatColor.RESET + "Deposit money in your account to start earning interest!");

                    } else {

                        playerBankData.set("Banked", newBalance);
                        player.sendMessage(ChatColor.BLUE + "[BANKER] " + ChatColor.RESET + "You received " + interest + " interest!");
                        database.save();
                        Main.updateScoreboard(player);

                    }

                }
            }
        }
    }
}
