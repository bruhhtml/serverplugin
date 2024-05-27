package me.plugin;

import me.plugin.ClanningSystem.ClanningCommands;
import me.plugin.ClanningSystem.ClanningDatabase;
import me.plugin.FriendSystem.FriendSystemMain;
import me.plugin.MainLobby.LobbyCommands;
import me.plugin.MainLobby.NPCs.Banker.BankSystemClicks;
import me.plugin.MainLobby.NPCs.Banker.BankSystemCommands;
import me.plugin.MainLobby.NPCs.Banker.Systems.DailyInterestTask;
import me.plugin.MainLobby.NPCs.Banker.Systems.DepositSystem;
import me.plugin.OpenWorld.NPCs.CashScoreboard.CashLeaderboardCommand;
import me.plugin.OpenWorld.NPCs.CashScoreboard.UpdateCashScoreboardTask;
import me.plugin.OpenWorld.NPCs.ClanScoreboard.ClanLeaderboardCommand;
import me.plugin.OpenWorld.NPCs.ClanScoreboard.UpdateClanScoreboardTask;
import me.plugin.OpenWorld.NPCs.Merchant.MerchantController;
import me.plugin.OpenWorld.NPCs.Merchant.ShopSystemClicks;
import me.plugin.OpenWorld.NPCs.Merchant.ShopSystemCommands;
import me.plugin.OpenWorld.OpenWorldMain;
import me.plugin.OpenWorld.OverheadScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;

//We need to import the CommandHandler class

public class Main extends JavaPlugin implements Listener {

    public void onEnable() {
        String RESET = "\u001B[0m";
        String RED = "\u001B[31m";
        String GREEN = "\u001B[32m";
        String YELLOW = "\u001B[33m";
        System.out.println(GREEN + "Main plugin started!" + RESET);
        this.getServer().getPluginManager().registerEvents(this, this);
        this.getServer().getPluginManager().registerEvents(new BankSystemClicks(), this);
        this.getServer().getPluginManager().registerEvents(new DepositSystem(), this);
        this.getServer().getPluginManager().registerEvents(new OpenWorldMain(), this);
        this.getServer().getPluginManager().registerEvents(new ShopSystemClicks(), this);
        this.getServer().getPluginManager().registerEvents(new MerchantController(), this);
        this.getCommand("BankGui").setExecutor(new BankSystemCommands());
        this.getCommand("EnableBanking").setExecutor(new BankSystemCommands());
        this.getCommand("SetCash").setExecutor(new BankSystemCommands());
        this.getCommand("Lobby").setExecutor(new LobbyCommands());
        this.getCommand("OverWorld").setExecutor(new LobbyCommands());
        this.getCommand("OpenWorldShopGUI").setExecutor(new ShopSystemCommands());
        this.getCommand("Friend").setExecutor(new FriendSystemMain());
        this.getCommand("FriendAccept").setExecutor(new FriendSystemMain());
        this.getCommand("UpdateCashLeaderboard").setExecutor(new CashLeaderboardCommand());
        this.getCommand("UpdateClanLeaderboard").setExecutor(new ClanLeaderboardCommand());
        new ClanningCommands(this);

        database.setup();
        ClanningDatabase.setup();
        database.save();
        ClanningDatabase.save();

        // Schedule the task to run once per day
//        int ticksPerDay = 20 * 60 * 60 * 24; // 20 ticks per second, 60 seconds per minute, 60 minutes per hour, 24 hours per day
        int interestTiming = 20 * 60 * 60 * 2; // 20 ticks per second, 60 seconds per minute, 60 minutes per hour, 2 hours
        int leaderboardTiming = 20 * 60 * 5; // 20 ticks per second, 60 seconds per minute, 10 minutes
        new DailyInterestTask().runTaskTimer(this, interestTiming, interestTiming);
        new UpdateCashScoreboardTask().runTaskTimer(this, leaderboardTiming, leaderboardTiming);
        new UpdateClanScoreboardTask().runTaskTimer(this, leaderboardTiming, leaderboardTiming);
    }

    public static void updateScoreboard(Player plr) {
        ScoreboardManager scoreboardmanager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = scoreboardmanager.getNewScoreboard();
        if (plr.getWorld().getName().equalsIgnoreCase("Lobby")) {
            Objective objective = scoreboard.registerNewObjective("Main", "dummy");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            objective.setDisplayName(ChatColor.BLUE + "City Central");
            Score score = objective.getScore(" ");
            score.setScore(8);
            Score score2 = objective.getScore(database.get().getConfigurationSection(plr.getUniqueId().toString()).getString("Rank"));
            score2.setScore(7);
            Score score3 = objective.getScore(String.valueOf("Level: " + database.get().getConfigurationSection(plr.getUniqueId().toString()).getInt("Level") + "  Xp: " + database.get().getConfigurationSection(plr.getUniqueId().toString()).getInt("Xp")));
            score3.setScore(6);
            Score score4 = objective.getScore("  ");
            score4.setScore(5);
            Score score5 = objective.getScore("Coins: " + String.format("%,d", database.get().getConfigurationSection(plr.getUniqueId().toString()).getInt("Coins")));
            score5.setScore(4);
            Score score6 = objective.getScore("   ");
            score6.setScore(3);
            Score score8 = objective.getScore("    ");
            score8.setScore(1);
            plr.setScoreboard(scoreboard);
        } else if (plr.getWorld().getName().equalsIgnoreCase("world") || plr.getWorld().getName().equalsIgnoreCase("world_nether") || plr.getWorld().getName().equalsIgnoreCase("world_the_nether")) {
            Objective objective = scoreboard.registerNewObjective("Main", "dummy");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            objective.setDisplayName(ChatColor.GREEN + "Open World");
            Score score = objective.getScore(" ");
            score.setScore(4);
            Score score5 = objective.getScore("Coins: " + String.format("%,d", database.get().getConfigurationSection(plr.getUniqueId().toString()).getInt("Coins")));
            score5.setScore(3);
            Score score6 = objective.getScore("   ");
            score6.setScore(2);
            Score score8 = objective.getScore("    ");
            score8.setScore(1);
            plr.setScoreboard(scoreboard);
        }
    }

    @EventHandler
    public void onPlayerWorldChange(PlayerChangedWorldEvent e) {

        updateScoreboard(e.getPlayer());

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        System.out.println("A player has joined the server!");
        e.setJoinMessage(player.getDisplayName() + " has joined the server!");

        if (!database.get().contains(player.getUniqueId().toString())) {
            database.loadpreset(player);
        }

        // Check for OfflineInterest and notify the player
        ConfigurationSection playerData = database.get().getConfigurationSection(player.getUniqueId().toString());
        if (playerData != null) {
            ConfigurationSection playerBankData = playerData.getConfigurationSection("Bank");
            if (playerBankData != null) {
                int offlineInterest = playerBankData.getInt("OfflineInterest", 0);
                if (offlineInterest > 0) {
                    playerBankData.set("OfflineInterest", 0);
                    player.sendMessage(ChatColor.BLUE + "[BANKER] " + ChatColor.RESET + "While you were away, you earned " + offlineInterest + " in interest!");
                }
            }
        }

        updateScoreboard(player);
        if (OverheadScoreboard.updateOverhead(player)) {

            player.sendMessage("Clan header successfully added");
            System.out.println(ChatColor.BLUE + "HEADER SUCCESS " + player.getDisplayName() + " Clan header successfully added");

        }

        playerData.set("PlayerName", (String) player.getPlayer().getName());
        database.save();
    }


    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        System.out.println("A player has left the server!");
        e.setQuitMessage(e.getPlayer().getDisplayName() + " has left the server!");
    }

    public void onDisable() {
        System.out.println("Main plugin stopped!");
        database.save();
        ClanningDatabase.save();
    }
}
