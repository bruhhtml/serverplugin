package me.plugin;

import java.io.File;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class database {
    private static File file;
    private static FileConfiguration databaseFile;

    public static void setup() {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("MainServerPlugin").getDataFolder(), "main_plugin_database.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException var1) {
            }
        }

        databaseFile = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get() {
        return databaseFile;
    }

    public static void save() {
        try {
            databaseFile.save(file);
        } catch (IOException var1) {
        }

    }

    public static void reload() {
        databaseFile = YamlConfiguration.loadConfiguration(file);
    }

    public static void loadpreset(Player player) {
        String playeruid = player.getUniqueId().toString();
        databaseFile.createSection(playeruid);
        databaseFile.getConfigurationSection(playeruid).set("Rank", 0);
        databaseFile.getConfigurationSection(playeruid).set("Level", 0);
        databaseFile.getConfigurationSection(playeruid).set("Xp", 0);
        databaseFile.getConfigurationSection(playeruid).set("Coins", 0);
        databaseFile.getConfigurationSection(playeruid).createSection("Friends");
        databaseFile.getConfigurationSection(playeruid).createSection("Bank");
        databaseFile.getConfigurationSection(playeruid).getConfigurationSection("Bank").set("Active", false);
        databaseFile.getConfigurationSection(playeruid).getConfigurationSection("Bank").set("Banked", 0);
        databaseFile.getConfigurationSection(playeruid).getConfigurationSection("Bank").set("MaxDepo", 10000);
        databaseFile.getConfigurationSection(playeruid).getConfigurationSection("Bank").set("InterestRate", 2);
        databaseFile.getConfigurationSection(playeruid).getConfigurationSection("Bank").set("InterestCap", 5000000);
        save();
    }
}