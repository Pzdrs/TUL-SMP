package me.pycrs.tulsmp.listeners;

import me.pycrs.tulsmp.TulSmp;
import me.pycrs.tulsmp.Utils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {
    private TulSmp plugin;

    public PlayerDeathListener(TulSmp plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        ConfigurationSection playerData = Utils.getPlayerData(event.getEntity());
        playerData.set("last_death", event.getEntity().getLocation());
        plugin.getPlayerData().saveConfig();
    }
}
