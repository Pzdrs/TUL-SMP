package me.pycrs.tulsmp.commands;

import me.pycrs.tulsmp.TulSmp;
import me.pycrs.tulsmp.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlaytimeCommand implements TabExecutor {
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
            player.sendMessage(Utils.color(
                    String.format("\n&7Total playtime: &a%s\n&7Current session: &a%s",
                            Utils.playtimeFormat(Utils.getPlayTime(player, false)),
                            Utils.playtimeFormat(Utils.getSessionPlayTime(player))
                    )
            ));
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return new ArrayList<>();
    }
}
