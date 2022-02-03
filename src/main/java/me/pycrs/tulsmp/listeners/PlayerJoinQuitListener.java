package me.pycrs.tulsmp.listeners;

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
        TulSmp.joinLog.put(event.getPlayer().getUniqueId(), System.currentTimeMillis());

        if (!plugin.getConfig().isConfigurationSection("player_data." + event.getPlayer().getUniqueId())) {
            plugin.getConfig().createSection("player_data." + event.getPlayer().getUniqueId());
            plugin.saveConfig();
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Utils.updatePlayerPlaytime(plugin, event.getPlayer(), true);
    }
}
