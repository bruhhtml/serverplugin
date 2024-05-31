package me.plugin;

import me.filoghost.holographicdisplays.api.HolographicDisplaysAPI;
import me.filoghost.holographicdisplays.api.hologram.Hologram;
import me.filoghost.holographicdisplays.api.hologram.HologramLines;
import me.filoghost.holographicdisplays.api.hologram.line.HologramLine;
import me.filoghost.holographicdisplays.api.hologram.line.TextHologramLine;
import me.plugin.ClanningSystem.ClanningCommands;
import me.plugin.ClanningSystem.ClanningDatabase;
import me.plugin.ClanningSystem.SubProcesses.ClanCreate;
import me.plugin.FriendSystem.FriendSystemMain;
import me.plugin.MainLobby.LobbyCommands;
//import me.plugin.OpenWorld.NPCs.Banker.BankSystemClicks;
//import me.plugin.OpenWorld.NPCs.Banker.BankSystemCommands;
//import me.plugin.OpenWorld.NPCs.Banker.Systems.DailyInterestTask;
//import me.plugin.OpenWorld.NPCs.Banker.Systems.DepositSystem;
import me.plugin.MainLobby.NPCs.CashScoreboard.UpdateCashScoreboardTask;
//import me.plugin.OpenWorld.NPCs.ClanScoreboard.UpdateClanScoreboardTask;
import me.plugin.MainLobby.NPCs.ClanScoreboard.UpdateClanScoreboardTask;
import me.plugin.OpenWorld.NPCs.Builder.BuilderNPCMain;
import me.plugin.OpenWorld.NPCs.Decorator.DecoratorNPCMain;
//import me.plugin.OpenWorld.NPCs.Merchant.ShopSystemClicks;
//import me.plugin.OpenWorld.NPCs.Merchant.ShopSystemCommands;
import me.plugin.OpenWorld.NPCs.NPCEventController;
import me.plugin.OpenWorld.NPCs.Wizard.WizardUI;
import me.plugin.OpenWorld.OpenWorldMain;
import me.plugin.OpenWorld.OverheadScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;

import java.util.*;

//We need to import the CommandHandler class

public class Main extends JavaPlugin implements Listener {

    // ANSI color codes
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_BOLD = "\u001B[1m";

    public void onEnable() {
        System.out.println(ANSI_GREEN + "Main plugin started!" + ANSI_RESET);
        this.getServer().getPluginManager().registerEvents(this, this);
//        this.getServer().getPluginManager().registerEvents(new BankSystemClicks(), this);
//        this.getServer().getPluginManager().registerEvents(new DepositSystem(), this);
        this.getServer().getPluginManager().registerEvents(new OpenWorldMain(), this);
//        this.getServer().getPluginManager().registerEvents(new MerchantShop(), this);
        this.getServer().getPluginManager().registerEvents(new BuilderNPCMain(), this);
        this.getServer().getPluginManager().registerEvents(new DecoratorNPCMain(), this);
//        this.getServer().getPluginManager().registerEvents(new ShopSystemClicks(), this);
        this.getServer().getPluginManager().registerEvents(new NPCEventController(), this);
//        this.getCommand("BankGui").setExecutor(new BankSystemCommands());
//        this.getCommand("EnableBanking").setExecutor(new BankSystemCommands());
//        this.getCommand("SetCash").setExecutor(new BankSystemCommands());
        this.getCommand("Lobby").setExecutor(new LobbyCommands());
        this.getCommand("OverWorld").setExecutor(new LobbyCommands());
//        this.getCommand("OpenWorldShopGUI").setExecutor(new ShopSystemCommands());
        this.getCommand("Friend").setExecutor(new FriendSystemMain());
        this.getCommand("FriendAccept").setExecutor(new FriendSystemMain());
//        this.getCommand("UpdateCashLeaderboard").setExecutor(new CashLeaderboardCommand());
//        this.getCommand("UpdateClanLeaderboard").setExecutor(new ClanLeaderboardCommand());
        new ClanningCommands(this);

        database.setup();
        ClanningDatabase.setup();
        database.save();
        ClanningDatabase.save();

        // Schedule the task to run once per day
//        int ticksPerDay = 20 * 60 * 60 * 24; // 20 ticks per second, 60 seconds per minute, 60 minutes per hour, 24 hours per day
        int interestTiming = 20 * 60 * 60 * 2; // 20 ticks per second, 60 seconds per minute, 60 minutes per hour, 2 hours
        int leaderboardTiming = 20 * 60 * 5; //* 5; // 20 ticks per second, 60 seconds per minute, 10 minutes
//        new DailyInterestTask().runTaskTimer(this, interestTiming, interestTiming);

//        new UpdateClanScoreboardTask(holo).runTaskTimer(this, leaderboardTiming, leaderboardTiming);

        Plugin holoPlugin = Bukkit.getServer().getPluginManager().getPlugin("HolographicDisplays");
        if (holoPlugin == null) {
            System.out.println(ANSI_RED + "HolographicDisplays plugin not found!" + ANSI_RESET);
            return;
        }

        Collection<Hologram> holos = HolographicDisplaysAPI.get(holoPlugin).getHolograms();

        System.out.println(ANSI_BOLD + ANSI_YELLOW + "Holograms:" + ANSI_RESET);

        try {

            for (Hologram hologram : holos) {
                HologramLines lines = hologram.getLines();
                if (lines.size() > 0) {
                    HologramLine firstLine = lines.get(0);
                    if (firstLine instanceof TextHologramLine) {
                        String firstLineText = ((TextHologramLine) firstLine).getText();
                        System.out.println(ANSI_CYAN + "First line of hologram: " + ANSI_GREEN + firstLineText + ANSI_RESET);
                        if (firstLineText.equalsIgnoreCase("{Rainbow}Top Clans")) {

                            new UpdateClanScoreboardTask(hologram).runTaskTimer(this, leaderboardTiming, leaderboardTiming);

                            new UpdateClanScoreboardTask(hologram).run();

                            System.out.println(ANSI_GREEN + "Correct hologram found." + ANSI_RESET);

                        } else if (firstLineText.equalsIgnoreCase("{Rainbow}Richest Players")) {

                            new UpdateCashScoreboardTask(hologram).runTaskTimer(this, leaderboardTiming, leaderboardTiming);

                            new UpdateCashScoreboardTask(hologram).run();

                            System.out.println(ANSI_GREEN + "Correct hologram found." + ANSI_RESET);

                        }
                    } else {
                        System.out.println(ANSI_RED + "First line is not a TextLine." + ANSI_RESET);
                    }
                } else {
                    System.out.println(ANSI_RED + "Hologram has no lines." + ANSI_RESET);
                }
            }

        } catch (Exception error) {

            System.out.println(ANSI_RED + "[ERROR] " + error.getMessage());

        }



        // Initialize enchantment costs map
        Map<Enchantment, Integer> enchantmentCosts = new HashMap<>();
        enchantmentCosts.put(Enchantment.PROTECTION_ENVIRONMENTAL, 100);
        enchantmentCosts.put(Enchantment.DURABILITY, 150);
        enchantmentCosts.put(Enchantment.ARROW_INFINITE, 200);

        // Register the WizardUI class as an event listener and pass the enchantment costs map
        getServer().getPluginManager().registerEvents(new WizardUI(enchantmentCosts), this);

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
        } else if (plr.getWorld().getName().equalsIgnoreCase("world") || plr.getWorld().getName().equalsIgnoreCase("world_nether") || plr.getWorld().getName().equalsIgnoreCase("world_the_end")) {
            Objective objective = scoreboard.registerNewObjective("Main", "dummy");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            objective.setDisplayName(ChatColor.GREEN + "Open World");
            Score score = objective.getScore(" ");

            Score score5 = objective.getScore("Wallet: " + ChatColor.YELLOW + String.format("%,d", database.get().getConfigurationSection(plr.getUniqueId().toString()).getInt("Coins")));

            Score score6 = objective.getScore("   ");

            if (ClanCreate.isInAnyClan(plr.getUniqueId().toString())) {
                score.setScore(7);
                score5.setScore(6);
                score6.setScore(5);

                String plrsClan = ClanCreate.getPlayerClan(plr.getUniqueId().toString());
                String plrsRank = ClanCreate.getRankInClan(plr.getUniqueId().toString(), plrsClan);
                int plrsClanBank = ClanningDatabase.get().getConfigurationSection(plrsClan).getInt("Bank");

                Score score7 = objective.getScore(ChatColor.GREEN + "Clan: " + ChatColor.RESET + plrsClan);
                score7.setScore(4);

                Score score8 = objective.getScore(ChatColor.GREEN + "Clan Rank: " + ChatColor.RESET + plrsRank);
                score8.setScore(3);

                Score score9 = objective.getScore(ChatColor.GREEN + "Clan Bank: " + ChatColor.RESET + plrsClanBank);
                score9.setScore(2);
            } else {

                score.setScore(4);
                score5.setScore(3);
                score6.setScore(2);

            }

            Score score10 = objective.getScore("    ");
            score10.setScore(1);

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
