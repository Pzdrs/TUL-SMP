package me.pycrs.tulsmp.listeners;

import me.pycrs.tulsmp.TulSmp;
import me.pycrs.tulsmp.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

public class BedEnterLeaveListener implements Listener {
    private TulSmp plugin;

    public BedEnterLeaveListener(TulSmp plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onBedEnter(PlayerBedEnterEvent event) {
        Player player = event.getPlayer();

        if (event.getBedEnterResult() == PlayerBedEnterEvent.BedEnterResult.OK) {
            Bukkit.getOnlinePlayers().forEach(online -> {
                if (online != player)
                    online.setSleepingIgnored(true);
                online.sendMessage(Utils.color(player.getName() + " &ewent to bed. Sweet Dreams"));
            });
        }
    }
}
