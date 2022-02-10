package me.pycrs.tulsmp;

import me.pycrs.tulsmp.commands.PlaytimeCommand;
import me.pycrs.tulsmp.commands.WhoisCommand;
import me.pycrs.tulsmp.listeners.*;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class TulSmp extends JavaPlugin implements Listener {
    private static TulSmp instance;
    public static Map<UUID, Long> joinLog = new HashMap<>();
    public static Map<UUID, Long> afkTracking = new HashMap<>();
    public static StagAPI stagAPI = new StagAPI("https://stag-ws.tul.cz/ws/services/rest2");
    private CustomConfiguration player_data;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        this.player_data = new CustomConfiguration(this, "players.yml");
        init();

        // Make sure all players are in the join log after reload
        getServer().getOnlinePlayers().forEach(player -> joinLog.put(player.getUniqueId(), System.currentTimeMillis()));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        // Make sure playtime gets updated if an unexpected server shutdown/reload occurs
        getServer().getOnlinePlayers().forEach(player -> Utils.updatePlayerPlaytime(this, player, false));
        player_data.saveConfig();
    }

    public static TulSmp getInstance() {
        return instance;
    }

    private void init() {
        new PlayerJoinQuitListener(this);
        new PlayerMoveListener(this);
        new ServerListPingListener(this);
        new EntityDeathListener(this);
        new BedEnterLeaveListener(this);

        new PlaytimeCommand(this);
        new WhoisCommand(this);
    }

    public CustomConfiguration getPlayerData() {
        return player_data;
    }
}
