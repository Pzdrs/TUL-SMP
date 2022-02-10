package me.pycrs.tulsmp.commands;

import me.pycrs.tulsmp.StagAPI;
import me.pycrs.tulsmp.TulSmp;
import me.pycrs.tulsmp.Utils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class WhoisCommand implements TabExecutor {
    private TulSmp plugin;

    public WhoisCommand(TulSmp plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(plugin.getCommand("whois")).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command is available to players only.");
            return true;
        }
        Player player = (Player) sender;
        if (args.length < 1) {
            player.sendMessage(Utils.color("&cMissing arguments: osobni cislo"));
            return true;
        }

        try {
            CloseableHttpResponse response = TulSmp.stagAPI.get("/student/getStudentInfo", new BasicNameValuePair("osCislo", args[0]));
            if (response.getStatusLine().getStatusCode() == 500) {
                player.sendMessage(Utils.color(String.format("&cInvalid osobni cislo: %s", args[0])));
                return true;
            } else if (response.getStatusLine().getStatusCode() == 200) {
                JSONObject person = new JSONObject(StagAPI.asText(response.getEntity().getContent()));
                player.sendMessage("\n");
                player.sendMessage(formatJsonPair("Osobní číslo", person.getString("osCislo")));
                player.sendMessage(formatJsonPair("Jméno", person.getString("jmeno")));
                player.sendMessage(formatJsonPair("Příjmení", person.getString("prijmeni")));
                player.sendMessage(formatJsonPair("Uživatelské jméno", person.getString("userName")));
                if (!person.get("email").toString().equals("null"))
                    player.sendMessage(formatJsonPair("Email", person.getString("email")));
                player.sendMessage(formatJsonPair("Fakulta", person.getString("fakultaSp")));
                player.sendMessage(formatJsonPair("Obor", person.getString("nazevSp")));
                player.sendMessage(formatJsonPair("Forma studia", person.getString("formaSp")));
                player.sendMessage(formatJsonPair("Ročník", person.getString("rocnik")));
                player.sendMessage(formatJsonPair("Rozvrhový kroužek", person.getString("rozvrhovyKrouzek")));
            }
        } catch (IOException | URISyntaxException e) {
            player.sendMessage(Utils.color("&cAn unexpected error occurred while contacting the STAG web services API"));
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return new ArrayList<>();
    }

    private String formatJsonPair(String key, Object value) {
        return Utils.color(String.format("&7%s: &a%s", key, value));
    }
}
