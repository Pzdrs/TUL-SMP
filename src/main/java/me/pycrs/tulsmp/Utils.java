package me.pycrs.tulsmp;

import org.apache.commons.lang.time.DurationFormatUtils;
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
        long sessionTime = getSessionPlayTime(player) - TulSmp.afkTracking.getOrDefault(player.getUniqueId(), 0L);
        // If the player has been afk longer than they actually played, don't increment the player's total playtime
        if (sessionTime <= 0) return;
        player_data.set("playtime", player_data.getLong("playtime") + TimeUnit.MILLISECONDS.toSeconds(sessionTime));
        if (save) plugin.saveConfig();
    }

    /**
     * Gets the current session playtime for a player
     *
     * @param player Target player
     * @return Session playtime (millis)
     */
    public static long getSessionPlayTime(Player player) {
        return System.currentTimeMillis() - TulSmp.joinLog.get(player.getUniqueId());
    }

    /**
     * Gets the current recorded playtime of a player. Doesn't include current session.
     *
     * @param player Target player
     * @return Playtime, not including current session (millis)
     */
    public static long getPlayTime(TulSmp plugin, Player player, boolean includeSession) {
        return TimeUnit.SECONDS.toMillis(plugin.getPlayerData(player.getUniqueId()).getLong("playtime")) + (includeSession ? getSessionPlayTime(player) : 0);
    }

    /**
     * Add afk time to a player to be then subtracted from session playtime
     *
     * @param player Target player
     * @param l      Afk duration (millis)
     */
    public static void addAfkTime(Player player, Long l) {
        TulSmp.afkTracking.put(player.getUniqueId(),
                TulSmp.afkTracking.getOrDefault(player.getUniqueId(), 0L) + l);
    }

    /**
     * Format time to "H '...' m '...' s '...'"
     *
     * @param l Time (millis)
     * @return Formatted string
     */
    public static String playtimeFormat(Long l) {
        return DurationFormatUtils.formatDuration(l, "H 'hours' m 'minutes' s 'seconds'");
    }
}
