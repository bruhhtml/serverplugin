package me.plugin.ClanningSystem;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ClanningDatabase {

    private static File file;
    private static FileConfiguration databaseFile;

    public static void setup() {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("MainServerPlugin").getDataFolder(), "clans.yml");
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

    public static void loadpreset(String clanName, Player player) {
        String playerId = player.getUniqueId().toString();
        databaseFile.createSection(clanName);
        databaseFile.getConfigurationSection(clanName).set("Bank", 0);
        databaseFile.getConfigurationSection(clanName).set("Owner", playerId);
        databaseFile.getConfigurationSection(clanName).set("Admins", new ArrayList<String>());
        databaseFile.getConfigurationSection(clanName).set("Members", new ArrayList<String>());
        save();
    }

}
