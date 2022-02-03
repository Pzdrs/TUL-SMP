package me.pycrs.tulsmp.listeners;

import me.pycrs.tulsmp.TulSmp;
import me.pycrs.tulsmp.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerListPingListener implements Listener {
    private TulSmp plugin;

    public ServerListPingListener(TulSmp plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPing(ServerListPingEvent event) {
        event.setMotd(Utils.color(Utils.spacing(18) + "&c&lTUL SMP &8- &6&lSEASON 1"));
    }
}
