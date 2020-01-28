package com.mateus.mflib.config;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class CustomConfig {
    private final Plugin plugin;
    private final String name;
    private FileConfiguration config;
    private File configFile;
    public CustomConfig(Plugin plugin, String name) {
        this.plugin = plugin;
        this.name = name;
    }
    public void setup() {
        File configFile = new File(plugin.getDataFolder(), name + ".yml");
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileConfiguration config = new YamlConfiguration();
        try {
            config.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        this.config = config;
        this.configFile = configFile;
        saveConfig();
    }
    public FileConfiguration getConfig() {
        return config;
    }
    public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(configFile);
    }
    public void saveConfig() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
