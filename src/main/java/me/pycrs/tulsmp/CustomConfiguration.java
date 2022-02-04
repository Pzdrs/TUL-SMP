package me.pycrs.tulsmp;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class CustomConfiguration {
    private final JavaPlugin plugin;
    private final String fileName;
    private File file;
    private FileConfiguration configuration;

    public CustomConfiguration(JavaPlugin plugin, String fileName) {
        this.plugin = plugin;
        this.fileName = fileName;
        this.file = new File(plugin.getDataFolder(), fileName);
        saveDefaultConfig();
    }

    public void reloadConfig() {
        configuration = YamlConfiguration.loadConfiguration(file);

        // Look for defaults in the jar
        InputStream defConfigStream = plugin.getResource(fileName);
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream));
            configuration.setDefaults(defConfig);
        }
    }

    public FileConfiguration getConfig() {
        if (configuration == null) {
            reloadConfig();
        }
        return configuration;
    }

    public void saveConfig() {
        try {
            getConfig().save(file);
        } catch (IOException ex) {
            plugin.getLogger().log(Level.SEVERE, "Could not save config to " + file, ex);
        }
    }

    public void saveDefaultConfig() {
        if (!file.exists()) {
            plugin.saveResource(fileName, false);
        }
    }
}
