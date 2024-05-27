package me.plugin.OpenWorld;

import org.bukkit.entity.Player;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.spi.CurrencyNameProvider;

public class CombatLogSystem {

    private static HashMap<String, LocalDateTime> CurrentCombat = new HashMap<>();

    private static LocalDateTime getTime(Player player) {

        String playerId = String.valueOf(player.getUniqueId());

        if (!CurrentCombat.isEmpty() && CurrentCombat.containsKey(playerId)) {

            return CurrentCombat.get(playerId);

        } else {

            return LocalDateTime.now().minusMinutes(1);

        }

    }

    public static boolean checkCombat(Player player) {

        LocalDateTime now = LocalDateTime.now();

        Duration difference = Duration.between(getTime(player), now);

        if (difference.getSeconds() >= 20) {

            removePlayer(player);

            return false;

        } else {

            return true;

        }

    }

    public static void addPlayer(Player player) {

        LocalDateTime now = LocalDateTime.now();

        String playerId = String.valueOf(player.getUniqueId());

        if (!CurrentCombat.containsKey(playerId)) {

            CurrentCombat.put(playerId, now);

        } else {

            CurrentCombat.replace(playerId, now);

        }

    }

    private static void removePlayer(Player player) {

        String playerId = String.valueOf(player.getUniqueId());

        if (CurrentCombat.containsKey(playerId)) {

            CurrentCombat.remove(playerId);

        }

    }

}
