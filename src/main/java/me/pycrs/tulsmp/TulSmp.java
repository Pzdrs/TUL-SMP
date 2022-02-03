package me.pycrs.tulsmp;

import me.pycrs.tulsmp.commands.PlaytimeCommand;
import me.pycrs.tulsmp.listeners.PlayerJoinQuitListener;
import me.pycrs.tulsmp.listeners.PlayerMoveListener;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class TulSmp extends JavaPlugin implements Listener {
    public static Map<UUID, Long> joinLog = new HashMap<>();
    public static Map<UUID, Long> afkTracking = new HashMap<>();
    public static StagAPI stagAPI = new StagAPI("https://stag-ws.tul.cz/ws/services/rest2");

    @Override
    public void onEnable() {
        saveDefaultConfig();
        init();

        // Make sure all players are in the join log after reload
        getServer().getOnlinePlayers().forEach(player -> joinLog.put(player.getUniqueId(), System.currentTimeMillis()));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        // Make sure playtime gets updated if an unexpected server shutdown/reload occurs
        getServer().getOnlinePlayers().forEach(player -> Utils.updatePlayerPlaytime(this, player, false));
        saveConfig();
    }

    private void init() {
        new PlayerJoinQuitListener(this);
        new PlayerMoveListener(this);

        new PlaytimeCommand(this);
    }

    public ConfigurationSection getPlayerData(UUID uuid) {
        return getConfig().getConfigurationSection("player_data." + uuid);
    }
}
