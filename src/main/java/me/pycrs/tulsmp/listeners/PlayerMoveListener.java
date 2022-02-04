package me.pycrs.tulsmp.listeners;

import me.pycrs.tulsmp.TulSmp;
import me.pycrs.tulsmp.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class PlayerMoveListener implements Listener {
    private TulSmp plugin;
    public static Map<UUID, Long> lastMoves = new HashMap<>();

    public PlayerMoveListener(TulSmp plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        // AFK handler
        if (lastMoves.containsKey(player.getUniqueId())) {
            long timeBetweenLastMove = System.currentTimeMillis() - lastMoves.get(player.getUniqueId());
            if (timeBetweenLastMove > TimeUnit.SECONDS.toMillis(plugin.getConfig().getInt("afkPeriod", 300))) {
                Utils.addAfkTime(player, timeBetweenLastMove);
                player.sendMessage(Utils.color(String.format("&7You have been AFK for the past %s.", Utils.playtimeFormat(timeBetweenLastMove))));
            }
        }
        lastMoves.put(player.getUniqueId(), System.currentTimeMillis());
    }
}
