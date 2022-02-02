package me.pycrs.tulsmp;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class TulSmp extends JavaPlugin implements Listener {
    private List<UUID> frozenPlayers = new ArrayList<>();
    private List<TulPlayer> registeredPlayers = new ArrayList<>();
    public static StagAPI stagAPI = new StagAPI("https://stag-ws.tul.cz/ws/services/rest2");

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(this, this);
        try {
            System.out.println(StagAPI.asText(stagAPI.student("/getStudentInfo?osCislo=M21000096").getEntity().getContent()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        ConfigurationSection player_data = getConfig().getConfigurationSection("player_data." + player.getUniqueId());
        if (player_data == null) player_data = getConfig().createSection("player_data." + player.getUniqueId());
        if (player_data.getString("osobni_cislo") == null) {
            frozenPlayers.add(player.getUniqueId());
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 0));
            player.sendMessage(Utils.color("&7Zadej osobni cislo: "));
        }
        saveConfig();
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        frozenPlayers.remove(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (frozenPlayers.contains(player.getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        ConfigurationSection player_data = getConfig().getConfigurationSection("player_data." + player.getUniqueId());
        if (frozenPlayers.contains(player.getUniqueId())) {
            player_data.set("osobni_cislo", event.getMessage());
            frozenPlayers.remove(player.getUniqueId());
            Bukkit.getScheduler().scheduleSyncDelayedTask(this, () ->
                    player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType())));
        }
        saveConfig();
    }
}
