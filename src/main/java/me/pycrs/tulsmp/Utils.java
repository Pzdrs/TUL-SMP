package me.pycrs.tulsmp;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public class Utils {
    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    /**
     * Adds player's current session playtime to the player's total
     *
     * @param plugin JavaPlugin
     * @param player Target player
     * @param save   Save config automatically
     */
    public static void updatePlayerPlaytime(TulSmp plugin, Player player, boolean save) {
        ConfigurationSection player_data = plugin.getPlayerData(player.getUniqueId());
        player_data.set("playtime", player_data.getLong("playtime") + getSessionPlayTime(player));
        if (save) plugin.saveConfig();
    }

    /**
     * Gets the current session playtime for a player
     *
     * @param player Target player
     * @return Session playtime
     */
    public static long getSessionPlayTime(Player player) {
        return TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - TulSmp.joinLog.get(player.getUniqueId()));
    }

    /**
     * Gets the current recorded playtime of a player. Doesn't include current session.
     *
     * @param player Target player
     * @return Playtime in seconds, not including current session
     */
    public static long getPlayTime(TulSmp plugin, Player player, boolean includeSession) {
        return plugin.getPlayerData(player.getUniqueId()).getLong("playtime") + (includeSession ? getSessionPlayTime(player) : 0);
    }
}
