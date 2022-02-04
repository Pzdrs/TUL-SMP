package me.pycrs.tulsmp.listeners;

import me.pycrs.tulsmp.CustomConfiguration;
import me.pycrs.tulsmp.TulSmp;
import me.pycrs.tulsmp.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoinQuitListener implements Listener {
    private TulSmp plugin;

    public PlayerJoinQuitListener(TulSmp plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        CustomConfiguration player_data = plugin.getPlayerData();
        TulSmp.joinLog.put(event.getPlayer().getUniqueId(), System.currentTimeMillis());

        if (!player_data.getConfig().isConfigurationSection(event.getPlayer().getUniqueId().toString())) {
            player_data.getConfig().createSection(event.getPlayer().getUniqueId().toString());
            player_data.saveConfig();
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Utils.updatePlayerPlaytime(plugin, event.getPlayer(), true);
    }
}
