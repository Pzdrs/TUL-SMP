package me.pycrs.tulsmp.commands;

import me.pycrs.tulsmp.TulSmp;
import me.pycrs.tulsmp.Utils;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class LastDeathCommand implements CommandExecutor {
    private TulSmp plugin;

    public LastDeathCommand(TulSmp plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(plugin.getCommand("lastDeath")).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command is available to players only.");
            return true;
        }
        Player player = (Player) sender;
        Location location = Utils.getPlayerData(player).getLocation("last_death");
        player.sendMessage(Utils.color(String.format("&7Location of your last death: &a%s", location == null ? "Haven't died yet" : location)));
        return true;
    }
}
