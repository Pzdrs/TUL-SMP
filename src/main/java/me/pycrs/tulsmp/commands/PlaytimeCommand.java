package me.pycrs.tulsmp.commands;

import me.pycrs.tulsmp.TulSmp;
import me.pycrs.tulsmp.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class PlaytimeCommand implements CommandExecutor {
    private TulSmp plugin;

    public PlaytimeCommand(TulSmp plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(plugin.getCommand("playtime")).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command is available to players only.");
            return true;
        }
        Player player = (Player) sender;


        if (args.length < 1) {
            player.sendMessage(Utils.color("&aYou have " + LocalTime.ofSecondOfDay(Utils.getPlayTime(plugin, player, true))
                    .format(DateTimeFormatter.ofPattern("H 'hours' m 'minutes' s 'seconds'")) + " of playtime."));
        } else {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                player.sendMessage(Utils.color("&cThis player is not online."));
                return true;
            }
            player.sendMessage(Utils.color("&a" + target.getName() + " has " + LocalTime.ofSecondOfDay(Utils.getPlayTime(plugin, player, true))
                    .format(DateTimeFormatter.ofPattern("H 'hours' m 'minutes' s 'seconds'")) + " of playtime."));
        }
        return true;
    }
}
